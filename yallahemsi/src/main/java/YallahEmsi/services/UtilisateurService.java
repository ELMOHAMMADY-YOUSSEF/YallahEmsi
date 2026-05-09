package YallahEmsi.services;

import YallahEmsi.entities.CNEValide;
import YallahEmsi.entities.Utilisateur;
import YallahEmsi.repositories.CNEValideRepository;
import YallahEmsi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service // Indique à Spring : "Ceci est la couche logique métier"
public class UtilisateurService {

    // Injection des repositories Utilisateur et CNEValide
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private CNEValideRepository cneValideRepository;

    // --- Fonction pour inscrire un nouvel étudiant ---
    public String inscrireEtudiant(Utilisateur nouvelEtudiant) {

        // 1. Vérifier si le CNE existe réellement dans la table CNE_Valide
        Optional<CNEValide> cneExiste = cneValideRepository.findById(nouvelEtudiant.getCne());

        if (cneExiste.isEmpty()) {
            return "Erreur : Ce CNE n'existe pas dans la liste de l'EMSI !";
        }

        // 2. Vérifier si l'email ou le CNE sont déjà utilisés
        if (utilisateurRepository.findByEmail(nouvelEtudiant.getEmail()).isPresent()) {
            return "Erreur : Cet email est déjà utilisé par un autre utilisateur !";
        }
        if (utilisateurRepository.findByCne(nouvelEtudiant.getCne()).isPresent()) {
            return "Erreur : Ce CNE possède déjà un compte !";
        }

        // 3. Si tout est correct, attribuer le rôle 'etudiant' puis enregistrer dans MySQL
        nouvelEtudiant.setRole(Utilisateur.Role.etudiant);
        utilisateurRepository.save(nouvelEtudiant);

        return "Félicitations ! Inscription réussie pour " + nouvelEtudiant.getNom();
    }
}