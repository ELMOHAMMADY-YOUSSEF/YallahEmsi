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

    // --- Fonction pour permettre à l'étudiant de réserver une place ---
    public String reserverTrajet(Integer passagerId, Integer trajetId, Integer nombrePlaces) {

        // 1. Recherche du trajet et du passager
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);
        Optional<Utilisateur> passagerOpt = utilisateurRepository.findById(passagerId);

        if (trajetOpt.isEmpty() || passagerOpt.isEmpty()) {
            return "❌ Erreur : Le trajet ou le passager n'existe pas !";
        }

        Trajet trajet = trajetOpt.get();
        Utilisateur passager = passagerOpt.get();

        // 2. Vérifier s'il reste des places disponibles
        if (trajet.getPlacesDisponibles() < nombrePlaces) {
            return "❌ Erreur : Désolé, il ne reste que " + trajet.getPlacesDisponibles() + " places dans ce trajet.";
        }

        // 3. Calcul du prix total (nombrePlaces * prixParPlace)
        BigDecimal total = trajet.getPrixParPlace().multiply(new BigDecimal(nombrePlaces));

        // 4. Création de la réservation
        ReservationTrajet reservation = new ReservationTrajet();
        reservation.setPassager(passager);
        reservation.setTrajet(trajet);
        reservation.setPlacesReservees(nombrePlaces);
        reservation.setMontantTotal(total);
        reservation.setStatutReservation(ReservationTrajet.StatutReservation.confirmee);

        // 5. Mise à jour des places disponibles
        trajet.setPlacesDisponibles(trajet.getPlacesDisponibles() - nombrePlaces);

        // 6. Sauvegarde
        reservationRepository.save(reservation);
        trajetRepository.save(trajet);

        return "✔ Félicitations " + passager.getNom() + " ! Vous avez réservé " + nombrePlaces + " place(s). Montant total : " + total + " MAD.";
    }
}