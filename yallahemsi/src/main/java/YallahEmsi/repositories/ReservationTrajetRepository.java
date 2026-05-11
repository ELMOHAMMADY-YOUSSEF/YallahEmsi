package YallahEmsi.repositories;

import YallahEmsi.entities.ReservationTrajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationTrajetRepository extends JpaRepository<ReservationTrajet, Integer> {

    // N-jibou les demandes dyal conducteur li baqin "en_attente"
    List<ReservationTrajet> findByTrajetConducteurIdAndStatutReservation(
            Integer conducteurId,
            ReservationTrajet.StatutReservation statut
    );

    List<ReservationTrajet> findByPassagerId(Integer passagerId);
    List<ReservationTrajet> findByTrajetId(Integer trajetId);
}