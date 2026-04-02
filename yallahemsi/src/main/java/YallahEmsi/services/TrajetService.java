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

    // --- Fonction 1 : Conducteur kay-publier trajet jdid ---
    public String publierTrajet(Trajet trajet) {

        // N-verifiw wach l'utilisateur li bgha y-publier had trajet d'bsa7 "conducteur"
        Optional<Utilisateur> conducteurOpt = utilisateurRepository.findById(trajet.getConducteur().getId());

        if (conducteurOpt.isEmpty() || conducteurOpt.get().getRole() != Utilisateur.Role.conducteur) {
            return "Erreur: Had l'utilisateur machi conducteur awla makaynch!";
        }

        // N-3tiw l'trajet statut "en_attente" par defaut
        trajet.setStatut(Trajet.StatutTrajet.en_attente);

        // N-sauvegardiw l'trajet f MySQL
        trajetRepository.save(trajet);

        return "Mabrouk! L'Trajet t-publia b naja7.";
    }

    // --- Fonction 2 : Njibou ga3 les trajets bach les etudiants y-choufouhom ---
    public List<Trajet> voirTousLesTrajets() {
        return trajetRepository.findAll();
    }
}