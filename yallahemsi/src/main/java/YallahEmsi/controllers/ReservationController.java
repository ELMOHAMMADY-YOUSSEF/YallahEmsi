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
    // 1. L'Étudiant (Passager) kay-ssift Demande dyal Réservation
    @PostMapping("/nouvelle")
    public String nouvelleReservation(@RequestParam Integer passagerId,
                                      @RequestParam Integer trajetId,
                                      @RequestParam Integer places) {

        Optional<Utilisateur> passagerOpt = utilisateurRepository.findById(passagerId);
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);

        if (passagerOpt.isEmpty() || trajetOpt.isEmpty()) {
            return "Erreur : L'utilisateur ou le trajet n'existe pas !";
        }

        Utilisateur passager = passagerOpt.get();
        Trajet trajet = trajetOpt.get();

        // Kan-t2akdou blli baqin l'blays f tomobil 9bel ma n-ssifto l'demande
        if (trajet.getPlacesDisponibles() < places) {
            return " Erreur : Il n'y a plus assez de places disponibles dans ce trajet !";
        }

        // L'Calcul dyal l'Flouss (Prix par place * Nombre de places)
        BigDecimal montant = trajet.getPrixParPlace().multiply(new BigDecimal(places));

        // 🔥 VERIFICATION DYAL L-WALLET (JDID) 🔥
        if (passager.getWallet() == null || passager.getWallet().getSolde() == null) {
            return "❌ Erreur: Portefeuille introuvable. Veuillez recharger votre compte.";
        }

        // Kan-choufou wach l-flouss li f l-Wallet sgher mn l-Montant dyal l-Trajet
        if (passager.getWallet().getSolde().compareTo(montant) < 0) {
            return "❌ Erreur : Solde insuffisant ! Vous avez " + passager.getWallet().getSolde() + " MAD, mais le trajet coûte " + montant + " MAD.";
        }

        ReservationTrajet res = new ReservationTrajet();
        res.setPassager(passager);
        res.setTrajet(trajet);
        res.setPlacesReservees(places);
        res.setMontantTotal(montant);

        // L'Statut par défaut: EN ATTENTE
        res.setStatutReservation(ReservationTrajet.StatutReservation.en_attente);

        reservationTrajetRepository.save(res);

        return "✅ Félicitations ! La demande a été envoyée au conducteur, veuillez attendre son acceptation.";
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
    // 3. L'Conducteur kay-Accepter l'Demande (Hna fin kan-nqssou l'blays w l-FLOUSS 💸)
    @PostMapping("/accepter/{reservationId}")
    public String accepterReservation(@PathVariable Integer reservationId) {
        Optional<ReservationTrajet> resOpt = reservationTrajetRepository.findById(reservationId);

        if (resOpt.isEmpty()) {
            return " Erreur : La réservation n'existe pas !";
        }

        ReservationTrajet res = resOpt.get();
        Trajet trajet = res.getTrajet();
        Utilisateur passager = res.getPassager();
        Utilisateur conducteur = trajet.getConducteur();

        // 1. Kan-t2akdou mra khra blli l'blays baqin khawyin
        if (trajet.getPlacesDisponibles() < res.getPlacesReservees()) {
            return "❌ Erreur : Il n'y a plus de places disponibles dans ce trajet pour accepter la réservation !";
        }

        // 2. Kan-jibou l-montant total dyal had l-réservation
        BigDecimal montantTotal = res.getMontantTotal();

        // 3. Kan-verifiw wach l-passager 3ndou flouss kafya f l-Wallet dyalo
        // ⚠️ MOLA7ADA: Ila kan Wallet 3ndk Entité (Classe) bo7dha, khassk d-dir: passager.getWallet().getSolde()
        // Hna nfhtardou anaka dayr l-Wallet fl-Utilisateur nishan (awla getSolde())
        if (passager.getWallet().getSolde().compareTo(montantTotal) < 0) {
            return "❌ Erreur : L'étudiant ne dispose pas d'un solde suffisant dans son portefeuille !";
        }

        // 4. Kan-nqssou l-flouss mn l-passager w kan-zidouhom l-conducteur
        passager.getWallet().setSolde(passager.getWallet().getSolde().subtract(montantTotal));
        conducteur.getWallet().setSolde(conducteur.getWallet().getSolde().add(montantTotal));

        // 5. Kan-bdlou l-état dyal réservation w kan-nqssou l-blays
        res.setStatutReservation(ReservationTrajet.StatutReservation.confirmee);
        trajet.setPlacesDisponibles(trajet.getPlacesDisponibles() - res.getPlacesReservees());

        // 6. Kan-sauvegardiw kolchi f MySQL (Darouri n-sauvegardiw Utilisateurs bach y-tbdl l-Wallet f Base de données)
        utilisateurRepository.save(passager);
        utilisateurRepository.save(conducteur);
        trajetRepository.save(trajet);
        reservationTrajetRepository.save(res);

        return "✅ Demande acceptée et le paiement a été effectué avec succès !";
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



        return "❌ Erreur : La réservation n'existe pas !";
    }
}