package YallahEmsi.controllers;

import YallahEmsi.entities.Voiture;
import YallahEmsi.services.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voitures")
public class VoitureController {

    @Autowired
    private VoitureService voitureService;

    // API bach n-zidou tomobil (@PathVariable hiya dik {id} li f l'URL)
    @PostMapping("/ajouter/{conducteurId}")
    public String ajouterVoiture(@PathVariable Integer conducteurId, @RequestBody Voiture voiture) {
        return voitureService.ajouterVoiture(conducteurId, voiture);
    }
}