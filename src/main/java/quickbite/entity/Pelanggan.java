package quickbite.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pelanggan")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pelanggan {
    @Id
    @Column(name = "id_pelanggan")
    private String idPelanggan;

    private String nama;
    private String email;
    private String nomorTelepon;
    private String alamat;
}