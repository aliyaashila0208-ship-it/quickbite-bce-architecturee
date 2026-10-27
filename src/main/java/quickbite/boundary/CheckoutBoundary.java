package quickbite.boundary;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import quickbite.boundary.dto.*;
import quickbite.controller.CheckoutController;
import quickbite.controller.dto.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutBoundary {
    private final CheckoutController checkoutController;

    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest req) {
        CheckoutCommand cmd = CheckoutCommand.builder()
                .idPelanggan(req.getIdPelanggan())
                .tokenPembayaran(req.getTokenPembayaran())
                .items(req.getItems().stream()
                        .map(i -> new CheckoutCommand.ItemCommand(i.getIdMenu(), i.getJumlah()))
                        .collect(Collectors.toList()))
                .build();

        CheckoutResult result = checkoutController.prosesCheckout(cmd);

        return CheckoutResponse.builder()
                .idPesanan(result.getIdPesanan())
                .statusPesanan(result.getStatusPesanan())
                .totalBiaya(result.getTotalBiaya())
                .statusPembayaran(result.getStatusPembayaran())
                .kodeReferensi(result.getKodeReferensi())
                .build();
    }
}