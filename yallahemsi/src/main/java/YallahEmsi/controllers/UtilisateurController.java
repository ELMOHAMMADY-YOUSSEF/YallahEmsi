package YallahEmsi.controllers;

import YallahEmsi.entities.Utilisateur;
import YallahEmsi.entities.Voiture;
import YallahEmsi.repositories.UtilisateurRepository;
import YallahEmsi.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @Autowired
    private VoitureRepository voitureRepo;

    // 1. INSCRIPTION
    @PostMapping("/inscription")
    public Utilisateur inscrire(@RequestBody Utilisateur user) {
        // Qadina l'Enum w 7yedna sauvegarde dyal voiture bo7dha
        if (user.getRole() == Utilisateur.Role.conducteur && user.getVoiture() != null) {

            // 🔥 HADA HOWA S-STER LI ZEDNA BACH N-FOKKOU L-MOCHKIL 🔥
            // Kan-goulou l-Tomobil: "Hada howa moulak li ghadi y-sogek!"
            user.getVoiture().setUtilisateur(user);

        } else {
            user.setVoiture(null); // Ila kan etudiant ma-khassouch tomobil
        }

        return utilisateurRepo.save(user);
    }

    // 2. LOGIN (KHASS Y-RDD L'OBJET KAMEL)
    @PostMapping("/login")
    public Utilisateur login(@RequestBody Utilisateur loginRequest) {
        // L'Méthode findByEmailAndMotDePasse khassha t-koun 3ndk f UtilisateurRepository
        Utilisateur user = utilisateurRepo.findByEmailAndMotDePasse(loginRequest.getEmail(), loginRequest.getMotDePasse()).orElse(null);

        if (user != null) {
            return user; // Kan-rddou user kamel (bach React y-khbiyh f localStorage w y-3qel 3la smito)
        } else {
            throw new RuntimeException("Email awla mot de passe ghalet");
        }
    }
}