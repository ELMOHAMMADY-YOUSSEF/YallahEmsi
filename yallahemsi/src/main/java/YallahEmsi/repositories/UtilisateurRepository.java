package YallahEmsi.repositories;

import YallahEmsi.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    // N9elbo 3la utilisateur b l'email
    Optional<Utilisateur> findByEmail(String email);

    // N9elbo 3la etudiant b l'CNE
    Optional<Utilisateur> findByCne(String cne);
}