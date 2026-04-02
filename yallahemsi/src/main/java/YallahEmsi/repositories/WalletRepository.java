package YallahEmsi.repositories;

import YallahEmsi.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    // Njibou l'wallet dyal wa7d l'utilisateur b l'id dyalo
    Optional<Wallet> findByUtilisateurId(Integer utilisateurId);
}