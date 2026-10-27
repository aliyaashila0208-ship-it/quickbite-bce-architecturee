package quickbite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @Column(name = "id_menu")
    private String idMenu;

    private String namaMenu;
    private double harga;
    private int stok;

    public boolean validasiStok(int qty) {
        return this.stok >= qty;
    }

    public boolean kurangiStok(int qty) {
        if (!validasiStok(qty)) return false;
        this.stok -= qty;
        return true;
    }

    public void tambahStok(int qty) {
        this.stok += qty;
    }
}