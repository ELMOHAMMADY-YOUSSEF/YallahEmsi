package YallahEmsi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Paiement")
@Data @NoArgsConstructor @AllArgsConstructor
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationTrajet reservation;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @Column(name = "montant_paye", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantPaye;

    @Column(name = "date_transaction", insertable = false, updatable = false)
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_paiement", columnDefinition = "ENUM('accepte', 'refuse', 'en_attente') DEFAULT 'en_attente'")
    private StatutPaiement statutPaiement = StatutPaiement.en_attente;

    public enum StatutPaiement { accepte, refuse, en_attente }
}