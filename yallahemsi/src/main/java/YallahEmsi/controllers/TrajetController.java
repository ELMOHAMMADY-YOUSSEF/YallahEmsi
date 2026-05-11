package YallahEmsi.controllers;
import YallahEmsi.entities.ReservationTrajet;
import YallahEmsi.repositories.ReservationTrajetRepository;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.http.MediaType;

import java.util.Optional;

import YallahEmsi.entities.Trajet;
import YallahEmsi.repositories.TrajetRepository;
import YallahEmsi.services.TrajetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/trajets")
public class TrajetController {

    @Autowired
    private TrajetService trajetService;

    @Autowired
    private TrajetRepository trajetRepository;

    // API bach n-publiw trajet jdid
    @PostMapping("/publier")
    public String publier(@RequestBody Trajet trajet) {
        try {
            // Hna Spring Boot kay-chd l-JSON w kay-7wlou l-Objet Trajet bo7do (Bla ObjectMapper)

            // Ila knti baghi t-valider chi 7aja 9bel ma t-sauvegarder t-qder d-dirha hna

            // Kan-sauvegardiw l'Trajet f MySQL nishan
            return trajetService.publierTrajet(trajet);

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erreur Serveur: " + e.getMessage();
        }
    }

    // API bach n-jibou la liste dyal ga3 les trajets (@GetMapping 7it ghan-9raw data)
    @GetMapping("/tous")
    public List<Trajet> getAllTrajets() {
        // Hna ma-n-b9awch n-diro findAll(), n-diro l'filtre li sawbna
        return trajetRepository.findByDateHeureDepartAfter(LocalDateTime.now());
    }

    @GetMapping("/mes-trajets/{conducteurId}")
    public List<Trajet> getMesTrajets(@PathVariable Integer conducteurId) {
        return trajetRepository.findByConducteurId(conducteurId);
    }


    @Autowired
    private ReservationTrajetRepository reservationTrajetRepository;
    // 1. API bach l-Conducteur y-msse7 l-Trajet dyalo
    @DeleteMapping("/supprimer/{id}")
    public String supprimerTrajet(@PathVariable Integer id) {
        try {
            // 1. N-jbdou ga3 les réservations dyal had l-trajet
            List<ReservationTrajet> reservations = reservationTrajetRepository.findByTrajetId(id);

            // 2. Kan-vérifiw wach kayna chi réservation baqa "Active" (Confirmée awla En attente)
            boolean hasActiveReservations = false;
            for (ReservationTrajet res : reservations) {
                // Ila lqina ghir we7da machi "annulee", ran-7bsou kolchi
                if (res.getStatutReservation() != ReservationTrajet.StatutReservation.annulee) {
                    hasActiveReservations = true;
                    break;
                }
            }

            // 3. Ila lqina passagers baqin active, kan-mn3ouh y-msse7
            if (hasActiveReservations) {
                return "❌ Erreur : Impossible de supprimer ce trajet car vous avez des passagers en attente ou confirmés !";
            }

            // 4. Ila wslna hna, y3ni l-blays khawyin (0 réservation) AWLA ga3 les réservations "annulee".
            // Khassna n-msse7ou hadouk les réservations annulées mn MySQL bach y-khellina n-msse7ou l-Trajet.
            if (!reservations.isEmpty()) {
                reservationTrajetRepository.deleteAll(reservations);
            }

            // 5. Daba n-qdrou n-msse7ou l-Trajet b amane
            trajetRepository.deleteById(id);

            return "✅ Trajet supprimé avec succès !";

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erreur lors de la suppression du trajet.";
        }
    }

    // 2. API bach l-Conducteur y-beddel l-waqt awla l-prix
    @PutMapping("/modifier/{id}")
    public String modifierTrajet(@PathVariable Integer id, @RequestBody Trajet trajetModifie) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(id);

        if (trajetOpt.isPresent()) {
            Trajet trajetExistant = trajetOpt.get();

            // Kan-beddlou ghir dakchi li bgha (L-waqt, Prix, Blayss)
            if(trajetModifie.getDateHeureDepart() != null) {
                trajetExistant.setDateHeureDepart(trajetModifie.getDateHeureDepart());
            }
            if(trajetModifie.getPrixParPlace() != null) {
                trajetExistant.setPrixParPlace(trajetModifie.getPrixParPlace());
            }
            if(trajetModifie.getPlacesDisponibles() != null) {
                trajetExistant.setPlacesDisponibles(trajetModifie.getPlacesDisponibles());
            }

            trajetRepository.save(trajetExistant);
            return "✅ Trajet modifié avec succès !";
        }
        return "❌ Erreur: Trajet introuvable.";
    }
}