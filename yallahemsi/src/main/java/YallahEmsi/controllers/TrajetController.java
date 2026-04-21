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
    @PostMapping(value = "/publier", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String publier(@RequestParam("trajet") String trajetJson,
                          @RequestParam(value = "audio", required = false) MultipartFile audio) {
        try {
            // 1. Kan-trjmou l'JSON l'Objet Trajet
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // Bach ma-y-tferge3ch f l-Weqt w t-Tarikh
            Trajet trajet = mapper.readValue(trajetJson, Trajet.class);

            // 2. Kan-sauvegardiw l'Fichier Audio (Ila ssifto l-Conducteur)
            if (audio != null && !audio.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + ".webm"; // Smiya m-khrbqa bach ma-y-t3awdouch
                Path path = Paths.get("uploads/audios/" + fileName);
                Files.createDirectories(path.getParent()); // Creeyi d-dossier ila makaynch
                Files.write(path, audio.getBytes()); // 7et l'fichier
                trajet.setAudioUrl(fileName); // Ssjjel ghir s-smiya f MySQL
            }

            // 3. Kan-sauvegardiw l'Trajet f MySQL
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