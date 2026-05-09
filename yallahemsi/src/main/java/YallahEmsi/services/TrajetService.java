package YallahEmsi.services;

import YallahEmsi.entities.Trajet;
import YallahEmsi.entities.Utilisateur;
import YallahEmsi.repositories.TrajetRepository;
import YallahEmsi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetService {

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // --- Fonction 1 : Le conducteur publie un nouveau trajet ---
    public String publierTrajet(Trajet trajet) {

        // Vérifier si l'utilisateur qui veut publier le trajet est bien un conducteur
        Optional<Utilisateur> conducteurOpt = utilisateurRepository.findById(trajet.getConducteur().getId());

        if (conducteurOpt.isEmpty() || conducteurOpt.get().getRole() != Utilisateur.Role.conducteur) {
            return "❌ Erreur : Cet utilisateur n'est pas un conducteur ou n'existe pas !";
        }

        // Définir le statut du trajet à "en attente" par défaut
        trajet.setStatut(Trajet.StatutTrajet.en_attente);

        // Sauvegarder le trajet dans la base de données
        trajetRepository.save(trajet);

        return "✔ Félicitations ! Le trajet a été publié avec succès.";
    }

    // --- Fonction 2 : Récupérer tous les trajets pour affichage aux étudiants ---
    public List<Trajet> voirTousLesTrajets() {
        return trajetRepository.findAll();
    }
}