package quickbite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_pesanan")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPesanan {
    @Id
    @Column(name = "id_item")
    private String idItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;

    private int jumlah;
    private double subtotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pesanan", nullable = false)
    private Pesanan pesanan;

    public void setPesanan(Pesanan pesanan) {
        this.pesanan = pesanan;
    }
}