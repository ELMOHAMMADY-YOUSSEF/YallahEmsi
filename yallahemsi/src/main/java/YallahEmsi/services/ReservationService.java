package YallahEmsi.services;

import YallahEmsi.entities.ReservationTrajet;
import YallahEmsi.entities.Trajet;
import YallahEmsi.entities.Utilisateur;
import YallahEmsi.repositories.ReservationTrajetRepository;
import YallahEmsi.repositories.TrajetRepository;
import YallahEmsi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationTrajetRepository reservationRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // --- Fonction bach l'etudiant y-reservi blassa ---
    public String reserverTrajet(Integer passagerId, Integer trajetId, Integer nombrePlaces) {

        // 1. N-9elbo 3la l'Trajet w l'Passager
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);
        Optional<Utilisateur> passagerOpt = utilisateurRepository.findById(passagerId);

        if (trajetOpt.isEmpty() || passagerOpt.isEmpty()) {
            return "Erreur: L'Trajet awla L'Passager makaynch!";
        }

        Trajet trajet = trajetOpt.get();
        Utilisateur passager = passagerOpt.get();

        // 2. N-verifiw wach baqin l'blays khawyin f tomobil
        if (trajet.getPlacesDisponibles() < nombrePlaces) {
            return "Erreur: Sme7 lina, ba9in ghir " + trajet.getPlacesDisponibles() + " blays f had l'trajet.";
        }

        // 3. N-7esbou l'prix total (nombrePlaces * prixParPlace)
        BigDecimal total = trajet.getPrixParPlace().multiply(new BigDecimal(nombrePlaces));

        // 4. N-sawbou l'Reservation jdida
        ReservationTrajet reservation = new ReservationTrajet();
        reservation.setPassager(passager);
        reservation.setTrajet(trajet);
        reservation.setPlacesReservees(nombrePlaces);
        reservation.setMontantTotal(total);
        reservation.setStatutReservation(ReservationTrajet.StatutReservation.confirmee);

        // 5. N-nqssou l'blays mn tomobil
        trajet.setPlacesDisponibles(trajet.getPlacesDisponibles() - nombrePlaces);

        // 6. N-sauvegardiw kolchi
        reservationRepository.save(reservation);
        trajetRepository.save(trajet);

        return "Mabrouk a " + passager.getNom() + "! Réserviti " + nombrePlaces + " blassa. L'Prix total howa: " + total + " MAD.";
    }
}