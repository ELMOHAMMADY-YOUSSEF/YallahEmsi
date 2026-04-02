package YallahEmsi.repositories;

import YallahEmsi.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
    // N9elbo 3la tomobil b l'matricule dyalha
    Optional<Voiture> findByMatricule(String matricule);
}