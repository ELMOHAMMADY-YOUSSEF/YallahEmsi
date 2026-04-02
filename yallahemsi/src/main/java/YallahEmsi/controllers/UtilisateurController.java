package YallahEmsi.controllers;

import YallahEmsi.entities.Utilisateur;
import YallahEmsi.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Katgoul l'Spring: "Hada howa Sserbay li ghadi y-jawb 3la les requêtes"
@RequestMapping("/api/utilisateurs") // L'adresse dyal had Sserbay
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // L'API bach n-ssjlou etudiant jdid (@PostMapping 7it ghadi n-ssifto données jdad)
    @PostMapping("/inscription")
    public String inscrire(@RequestBody Utilisateur etudiant) {
        // Kan-chedou l'etudiant li ja mn Postman, w kan-3tiwh l'Service y-tkelaf bih
        return utilisateurService.inscrireEtudiant(etudiant);
    }
}