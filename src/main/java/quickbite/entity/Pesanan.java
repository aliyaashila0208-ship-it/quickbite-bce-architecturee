package quickbite.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pesanan")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pesanan {
    @Id
    @Column(name = "id_pesanan")
    private String idPesanan;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelanggan", nullable = false)
    private Pelanggan pelanggan;

    private LocalDateTime tanggal;
    private double totalBiaya;

    @Enumerated(EnumType.STRING)
    private StatusPesanan status;

    @OneToMany(mappedBy = "pesanan", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ItemPesanan> items = new ArrayList<>();

    public void tambahItem(Menu menu, int jumlah) {
        double subtotal = menu.getHarga() * jumlah;
        ItemPesanan item = ItemPesanan.builder()
                .idItem("ITEM-" + System.currentTimeMillis() + "-" + (items.size() + 1))
                .menu(menu)
                .jumlah(jumlah)
                .subtotal(subtotal)
                .pesanan(this)
                .build();
        items.add(item);
        hitungTotalBiaya();
    }

    public void hitungTotalBiaya() {
        this.totalBiaya = items.stream().mapToDouble(ItemPesanan::getSubtotal).sum();
    }

    public void setStatus(StatusPesanan status) {
        this.status = status;
    }
}