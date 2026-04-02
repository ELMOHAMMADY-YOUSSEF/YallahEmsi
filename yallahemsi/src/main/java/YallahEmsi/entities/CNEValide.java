package YallahEmsi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CNE_Valide")
@Data @NoArgsConstructor @AllArgsConstructor
public class CNEValide {
    @Id
    @Column(length = 20)
    private String cne;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(nullable = false, length = 50)
    private String filiere;
}