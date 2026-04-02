package YallahEmsi.services;

import YallahEmsi.entities.Utilisateur;
import YallahEmsi.entities.Voiture;
import YallahEmsi.repositories.UtilisateurRepository;
import YallahEmsi.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoitureService {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // --- Fonction bach n-zidou tomobil jdida ---
    public String ajouterVoiture(Integer utilisateurId, Voiture nouvelleVoiture) {

        // 1. N9elbo 3la l'étudiant f l'Magaza
        Optional<Utilisateur> userOpt = utilisateurRepository.findById(utilisateurId);
        if (userOpt.isEmpty()) {
            return "Erreur: Had l'utilisateur makaynch!";
        }

        Utilisateur etudiant = userOpt.get();

        // 2. N-verifiw wach l'matricule dyal tomobil machi m3awd (Déjà m-ssjel)
        if (voitureRepository.findByMatricule(nouvelleVoiture.getMatricule()).isPresent()) {
            return "Erreur: Had l'matricule deja kayn f systeme!";
        }

        // 3. N-rbtou tomobil b l'étudiant w n-bdelo lih l'Role
        nouvelleVoiture.setConducteur(etudiant);
        etudiant.setRole(Utilisateur.Role.conducteur); // Daba wla Chauffeur!

        // 4. N-sauvegardiw kolchi f MySQL
        voitureRepository.save(nouvelleVoiture);
        utilisateurRepository.save(etudiant);

        return "Mabrouk! Tomobil " + nouvelleVoiture.getMarque() + " t-zadate w nta daba Conducteur a " + etudiant.getNom();
    }
}