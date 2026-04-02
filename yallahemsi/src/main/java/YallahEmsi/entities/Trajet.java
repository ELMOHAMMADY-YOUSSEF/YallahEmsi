package YallahEmsi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Trajet")
@Data @NoArgsConstructor @AllArgsConstructor
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conducteur_id", nullable = false)
    private Utilisateur conducteur;

    @ManyToOne
    @JoinColumn(name = "voiture_id", nullable = false)
    private Voiture voiture;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_trajet", nullable = false)
    private TypeTrajet typeTrajet;

    @ManyToOne
    @JoinColumn(name = "hay_id", nullable = false)
    private Hay hay;

    @ManyToOne
    @JoinColumn(name = "quartier_id")
    private Quartier quartier;

    @ManyToOne
    @JoinColumn(name = "campus_id", nullable = false)
    private Campus campus;

    @Column(name = "date_heure_depart", nullable = false)
    private LocalDateTime dateHeureDepart;

    @Column(name = "prix_par_place", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixParPlace;

    @Column(name = "places_disponibles", nullable = false)
    private Integer placesDisponibles;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('en_attente', 'en_cours', 'termine', 'annule') DEFAULT 'en_attente'")
    private StatutTrajet statut = StatutTrajet.en_attente;

    public enum TypeTrajet { aller, retour }
    public enum StatutTrajet { en_attente, en_cours, termine, annule }
}