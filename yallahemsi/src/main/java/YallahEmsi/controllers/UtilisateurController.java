package YallahEmsi.controllers;

import YallahEmsi.entities.Utilisateur;
import YallahEmsi.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
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

    @Autowired
    private YallahEmsi.repositories.UtilisateurRepository utilisateurRepository;

    // API dyal l'Login bstiha
    @PostMapping("/login")
    public Object login(@RequestParam String email, @RequestParam String motDePasse) {
        var user = utilisateurRepository.findByEmailAndMotDePasse(email, motDePasse);

        if (user.isPresent()) {
            return user.get(); // Ila l9ah, kay-rjje3 l'utilisateur kamel l'React
        } else {
            return "Erreur: Email awla mot de passe ghaltin!";
        }
    }
}