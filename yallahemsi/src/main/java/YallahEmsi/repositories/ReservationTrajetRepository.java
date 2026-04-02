package YallahEmsi.repositories;

import YallahEmsi.entities.ReservationTrajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationTrajetRepository extends JpaRepository<ReservationTrajet, Integer> {
    // Njibou ga3 les réservations dyal wa7d l'étudiant
    List<ReservationTrajet> findByPassagerId(Integer passagerId);
}