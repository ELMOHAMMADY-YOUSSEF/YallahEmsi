package YallahEmsi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Utilisateur")
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(length = 20)
    private String telephone;

    @Column(name = "cne", unique = true, length = 20)
    private String cne;

    @Column(length = 50)
    private String filiere;

    @Column(length = 50)
    private String niveau;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('etudiant', 'conducteur', 'admin') DEFAULT 'etudiant'")
    private Role role;

    public enum Role {
        etudiant, conducteur, admin
    }
}