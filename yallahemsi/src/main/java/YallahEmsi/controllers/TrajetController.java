package YallahEmsi.controllers;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.http.MediaType;

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
}