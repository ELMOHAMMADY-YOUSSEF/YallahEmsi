package YallahEmsi.controllers;

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
        return trajetService.publierTrajet(trajet);
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