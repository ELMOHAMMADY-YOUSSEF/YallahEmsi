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

    // --- Fonction pour ajouter une nouvelle voiture ---
    public String ajouterVoiture(Integer utilisateurId, Voiture nouvelleVoiture) {

        // 1. Recherche de l'utilisateur dans la base de données
        Optional<Utilisateur> userOpt = utilisateurRepository.findById(utilisateurId);
        if (userOpt.isEmpty()) {
            return "❌ Erreur : Cet utilisateur n'existe pas !";
        }

        Utilisateur etudiant = userOpt.get();

        // 2. Vérifier si le matricule de la voiture existe déjà
        if (voitureRepository.findByMatricule(nouvelleVoiture.getMatricule()).isPresent()) {
            return "❌ Erreur : Ce matricule existe déjà dans le système !";
        }

        // 3. Lier la voiture à l'utilisateur et changer son rôle
        nouvelleVoiture.setConducteur(etudiant);
        etudiant.setRole(Utilisateur.Role.conducteur); // Devient conducteur

        // 4. Sauvegarde dans la base de données
        voitureRepository.save(nouvelleVoiture);
        utilisateurRepository.save(etudiant);

        return "✔ Félicitations ! La voiture " + nouvelleVoiture.getMarque() + " a été ajoutée et vous êtes maintenant conducteur " + etudiant.getNom();
    }
}