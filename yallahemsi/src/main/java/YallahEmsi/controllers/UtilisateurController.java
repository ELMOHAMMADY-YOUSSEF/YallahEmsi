package YallahEmsi.controllers;

import YallahEmsi.entities.Utilisateur;
import YallahEmsi.entities.Voiture;
import YallahEmsi.entities.Wallet; // 👈 ZIDNA L-IMPORT DYAL WALLET
import YallahEmsi.repositories.UtilisateurRepository;
import YallahEmsi.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal; // 👈 ZIDNA L-IMPORT DYAL BIGDECIMAL

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

        // --- 1. L-KHEDMA DYAL VOITURE ---
        if (user.getRole() == Utilisateur.Role.conducteur && user.getVoiture() != null) {
            user.getVoiture().setUtilisateur(user);
        } else {
            user.setVoiture(null); // Ila kan etudiant ma-khassouch tomobil
        }

        // --- 2. L-KHEDMA DYAL L-WALLET (JDIDA 🔥) ---
        // Kan-creeyiw wallet jdid fih 0 MAD
        Wallet newWallet = new Wallet();
        newWallet.setSolde(BigDecimal.ZERO);
        newWallet.setDevise("MAD");

        // Kan-rbtou l-Utilisateur b l-Wallet dyalo
        user.setWallet(newWallet);
        newWallet.setUtilisateur(user); // Bach l-Wallet hta howa y-3ref moulah

        // Mli kan-sauvegardiw l-Utilisateur, Spring ghadi y-sauvegarder m3ah l-Voiture w l-Wallet (b sbab CascadeType.ALL)
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
            throw new RuntimeException("Email ou mot de passe incorrect");
        }
    }
}