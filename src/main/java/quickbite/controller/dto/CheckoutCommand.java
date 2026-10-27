package quickbite.controller.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckoutCommand {
    private String idPelanggan;
    private List<ItemCommand> items;
    private String tokenPembayaran;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ItemCommand {
        private String idMenu;
        private int jumlah;
    }
}