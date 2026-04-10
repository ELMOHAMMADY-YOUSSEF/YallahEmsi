package YallahEmsi.controllers;

import YallahEmsi.entities.ReservationTrajet;
import YallahEmsi.entities.Trajet;
import YallahEmsi.entities.Utilisateur;
import YallahEmsi.repositories.ReservationTrajetRepository;
import YallahEmsi.repositories.TrajetRepository;
import YallahEmsi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationTrajetRepository reservationTrajetRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // 1. L'Étudiant (Passager) kay-ssift Demande dyal Réservation
    @PostMapping("/nouvelle")
    public String nouvelleReservation(@RequestParam Integer passagerId,
                                      @RequestParam Integer trajetId,
                                      @RequestParam Integer places) {

        Optional<Utilisateur> passagerOpt = utilisateurRepository.findById(passagerId);
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);

        if (passagerOpt.isEmpty() || trajetOpt.isEmpty()) {
            return "❌ Erreur: Utilisateur awla Trajet ma-kaynch!";
        }

        Utilisateur passager = passagerOpt.get();
        Trajet trajet = trajetOpt.get();

        // Kan-t2akdou blli baqin l'blays f tomobil 9bel ma n-ssifto l'demande
        if (trajet.getPlacesDisponibles() < places) {
            return "❌ Erreur: Ma-bqawch blays kafyin f had l'trajet!";
        }

        ReservationTrajet res = new ReservationTrajet();
        res.setPassager(passager);
        res.setTrajet(trajet);
        res.setPlacesReservees(places);

        // L'Calcul dyal l'Flouss (Prix par place * Nombre de places)
        BigDecimal montant = trajet.getPrixParPlace().multiply(new BigDecimal(places));
        res.setMontantTotal(montant);

        // L'Statut par défaut: EN ATTENTE
        res.setStatutReservation(ReservationTrajet.StatutReservation.en_attente);

        reservationTrajetRepository.save(res);

        return "✅ Mabrouk! L'Demande tsiftat l'conducteur, tsna y-accepter.";
    }

    // 2. L'Conducteur kay-chouf ga3 les demandes li baqin "EN ATTENTE"
    @GetMapping("/demandes/{conducteurId}")
    public List<ReservationTrajet> getDemandesEnAttente(@PathVariable Integer conducteurId) {
        return reservationTrajetRepository.findByTrajetConducteurIdAndStatutReservation(
                conducteurId,
                ReservationTrajet.StatutReservation.en_attente
        );
    }

    // 3. L'Conducteur kay-Accepter l'Demande (Hna fin kan-nqssou l'blays)
    @PostMapping("/accepter/{reservationId}")
    public String accepterReservation(@PathVariable Integer reservationId) {
        Optional<ReservationTrajet> resOpt = reservationTrajetRepository.findById(reservationId);

        if (resOpt.isEmpty()) {
            return "❌ Erreur: Réservation ma-kaynach!";
        }

        ReservationTrajet res = resOpt.get();
        Trajet trajet = res.getTrajet();

        // Kan-t2akdou mra khra blli l'blays baqin khawyin (7it yqder y-accepter chi wa7d akhor qbel mno)
        if (trajet.getPlacesDisponibles() >= res.getPlacesReservees()) {
            res.setStatutReservation(ReservationTrajet.StatutReservation.confirmee);
            trajet.setPlacesDisponibles(trajet.getPlacesDisponibles() - res.getPlacesReservees());

            // Kan-sauvegardiw t-tghyirat bjouj
            trajetRepository.save(trajet);
            reservationTrajetRepository.save(res);

            return "✅ Demande Acceptée!";
        }

        return "❌ Erreur: Ma-bqawch blays f had l'trajet bach t-accepter!";
    }

    @GetMapping("/mes-reservations/{passagerId}")
    public List<ReservationTrajet> getMesReservations(@PathVariable Integer passagerId) {
        return reservationTrajetRepository.findByPassagerId(passagerId);
    }

    // 4. L'Conducteur kay-Refuser l'Demande
    @PostMapping("/refuser/{reservationId}")
    public String refuserReservation(@PathVariable Integer reservationId) {
        Optional<ReservationTrajet> resOpt = reservationTrajetRepository.findById(reservationId);

        if (resOpt.isPresent()) {
            ReservationTrajet res = resOpt.get();
            res.setStatutReservation(ReservationTrajet.StatutReservation.annulee);
            reservationTrajetRepository.save(res);
            return "❌ Demande Refusée.";
        }



        return "❌ Erreur: Réservation ma-kaynach!";
    }
}