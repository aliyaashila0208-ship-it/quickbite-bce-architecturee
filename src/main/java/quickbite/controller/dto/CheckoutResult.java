package quickbite.controller.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckoutResult {
    private String idPesanan;
    private String statusPesanan;
    private double totalBiaya;
    private String statusPembayaran;
    private String kodeReferensi;
}