package quickbite.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quickbite.boundary.gateway.PaymentGatewayAPI;
import quickbite.controller.dto.*;
import quickbite.controller.exception.*;
import quickbite.entity.*;
import quickbite.repository.*;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CheckoutController {
    private final PelangganRepository pelangganRepository;
    private final MenuRepository menuRepository;
    private final PesananRepository pesananRepository;
    private final PaymentGatewayAPI paymentGateway;

    @Transactional
    public CheckoutResult prosesCheckout(CheckoutCommand cmd) {
        Pelanggan pelanggan = pelangganRepository.findById(cmd.getIdPelanggan())
                .orElseThrow(() -> new ResourceNotFoundException("Pelanggan tidak ditemukan"));

        Pesanan pesanan = Pesanan.builder()
                .idPesanan("ORD-" + System.currentTimeMillis())
                .pelanggan(pelanggan)
                .tanggal(LocalDateTime.now())
                .status(StatusPesanan.MENUNGGU_BAYAR)
                .build();

        for (CheckoutCommand.ItemCommand itemCmd : cmd.getItems()) {
            Menu menu = menuRepository.findByIdForUpdate(itemCmd.getIdMenu())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu tidak ditemukan: " + itemCmd.getIdMenu()));
            
            if (!menu.validasiStok(itemCmd.getJumlah())) {
                throw new StokTidakCukupException("Stok tidak mencukupi untuk menu: " + menu.getNamaMenu());
            }
            pesanan.tambahItem(menu, itemCmd.getJumlah());
        }

        pesananRepository.save(pesanan);

        String refCode;
        try {
            refCode = paymentGateway.prosesPembayaran(pesanan.getIdPesanan(), pesanan.getTotalBiaya(), cmd.getTokenPembayaran());
        } catch (Exception e) {
            throw new PaymentFailedException("Pembayaran gagal: " + e.getMessage());
        }

        for (ItemPesanan item : pesanan.getItems()) {
            item.getMenu().kurangiStok(item.getJumlah());
        }

        pesanan.setStatus(StatusPesanan.DIPROSES);
        pesananRepository.save(pesanan);

        return CheckoutResult.builder()
                .idPesanan(pesanan.getIdPesanan())
                .statusPesanan(pesanan.getStatus().name())
                .totalBiaya(pesanan.getTotalBiaya())
                .statusPembayaran("SUKSES")
                .kodeReferensi(refCode)
                .build();
    }
}