package YallahEmsi.services;

import YallahEmsi.entities.CNEValide;
import YallahEmsi.entities.Utilisateur;
import YallahEmsi.repositories.CNEValideRepository;
import YallahEmsi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service // Katgoul l'Spring: "Hadi hiya l'Kouzina (La logique)"
public class UtilisateurService {

    // Kan-jibou l'Magaza dyal Utilisateur w dyal CNE bach n-9elbo fihom
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CNEValideRepository cneValideRepository;

    // --- Fonction bach n-ssjlou Etudiant jdid ---
    public String inscrireEtudiant(Utilisateur nouvelEtudiant) {

        // 1. N-verifiw wach had l'CNE d'bsa7 kayn f l'EMSI (table CNE_Valide)
        Optional<CNEValide> cneExiste = cneValideRepository.findById(nouvelEtudiant.getCne());

        if (cneExiste.isEmpty()) {
            return "Erreur: Had l'CNE makaynch f la liste dyal l'EMSI!";
        }

        // 2. N-verifiw wach had l'Email awla l'CNE deja tsjjel bihom chi wa7d f l'appli
        if (utilisateurRepository.findByEmail(nouvelEtudiant.getEmail()).isPresent()) {
            return "Erreur: Had l'Email deja m-ssjel biha chi wa7d!";
        }
        if (utilisateurRepository.findByCne(nouvelEtudiant.getCne()).isPresent()) {
            return "Erreur: Had l'CNE deja 3ndou compte!";
        }

        // 3. Ila kan kolchi n9i, n-3tiwh l'Role 'etudiant' w n-khebiwh f MySQL
        nouvelEtudiant.setRole(Utilisateur.Role.etudiant);
        utilisateurRepository.save(nouvelEtudiant);

        return "Mabrouk! T-ssjelti b naja7 a " + nouvelEtudiant.getNom();
    }
}