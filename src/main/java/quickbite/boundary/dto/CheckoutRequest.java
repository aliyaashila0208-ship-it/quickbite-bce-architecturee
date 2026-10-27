package quickbite.boundary.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CheckoutRequest {
    @NotBlank(message = "ID Pelanggan wajib diisi")
    private String idPelanggan;

    @NotEmpty(message = "Item pesanan tidak boleh kosong")
    private List<ItemRequest> items;

    @NotBlank(message = "Token pembayaran wajib diisi")
    private String tokenPembayaran;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ItemRequest {
        @NotBlank private String idMenu;
        @Min(1) private int jumlah;
    }
}