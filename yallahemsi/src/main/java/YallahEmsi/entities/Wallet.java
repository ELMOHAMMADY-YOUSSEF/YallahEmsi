package YallahEmsi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Wallet")
@Data @NoArgsConstructor @AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", unique = true)
    private Utilisateur utilisateur;

    @Column(precision = 10, scale = 2)
    private BigDecimal solde = BigDecimal.ZERO;

    @Column(length = 10)
    private String devise = "MAD";
}