package YallahEmsi.repositories;

import YallahEmsi.entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Integer> {
    // Njibou ga3 les trajets li ghadyin l wa7d l'Campus (ex: EMSI Maarif)
    List<Trajet> findByCampusId(Integer campusId);

    // Njibou ga3 les trajets li darhom wa7d l'conducteur
    List<Trajet> findByConducteurId(Integer conducteurId);
}