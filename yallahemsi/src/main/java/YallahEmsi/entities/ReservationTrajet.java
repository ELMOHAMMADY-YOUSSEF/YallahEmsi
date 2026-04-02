package YallahEmsi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "Reservation_Trajet")
@Data @NoArgsConstructor @AllArgsConstructor
public class ReservationTrajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "passager_id", nullable = false)
    private Utilisateur passager;

    @ManyToOne
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;

    @Column(name = "places_reservees")
    private Integer placesReservees = 1;

    @Column(name = "montant_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_reservation", columnDefinition = "ENUM('en_attente', 'confirmee', 'annulee') DEFAULT 'en_attente'")
    private StatutReservation statutReservation = StatutReservation.en_attente;

    public enum StatutReservation { en_attente, confirmee, annulee }
}