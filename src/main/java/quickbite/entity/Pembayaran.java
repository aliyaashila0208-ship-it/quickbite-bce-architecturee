package quickbite.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pembayaran")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pembayaran {
    @Id
    @Column(name = "id_pembayaran")
    private String idPembayaran;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_pesanan", nullable = false, unique = true)
    private Pesanan pesanan;

    private double jumlahBayar;
    private String statusBayar;
    private String kodeReferensi;
    private LocalDateTime tanggal;
}