package YallahEmsi.controllers;

import YallahEmsi.entities.Message;
import YallahEmsi.entities.Trajet;
import YallahEmsi.entities.Utilisateur;
import YallahEmsi.repositories.MessageRepository;
import YallahEmsi.repositories.TrajetRepository;
import YallahEmsi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private UtilisateurRepository userRepo;
    @Autowired
    private TrajetRepository trajetRepo;

    // 1. API bach n-siftou message
    @PostMapping("/envoyer")
    public Message envoyerMessage(@RequestParam Integer expediteurId,
                                  @RequestParam Integer destinataireId,
                                  @RequestParam Integer trajetId, // <--- BEDDEL HADI L-INTEGER
                                  @RequestParam String contenu) {

        Utilisateur expediteur = userRepo.findById(expediteurId).orElse(null);
        Utilisateur destinataire = userRepo.findById(destinataireId).orElse(null);
        Trajet trajet = trajetRepo.findById(trajetId).orElse(null);

        if (expediteur != null && destinataire != null && trajet != null) {
            Message msg = new Message();
            msg.setExpediteur(expediteur);
            msg.setDestinataire(destinataire);
            msg.setTrajet(trajet);
            msg.setContenu(contenu);
            msg.setDateEnvoi(LocalDateTime.now());
            return messageRepo.save(msg);
        }
        return null;
    }

    // 2. API bach n-jibou l-historique dyal l-chat
    @GetMapping("/historique/{trajetId}/{user1}/{user2}")
    public List<Message> getHistorique(@PathVariable Integer trajetId, // <--- BEDDEL HADI L-INTEGER
                                       @PathVariable Integer user1,
                                       @PathVariable Integer user2) {
        return messageRepo.findChatHistory(trajetId, user1, user2);
    }

    // 3. API bach n-jibou n-nas li m-contactyin l-conducteur f chi trajet
    @GetMapping("/contacts/{trajetId}/{conducteurId}")
    public List<Utilisateur> getContactsPourTrajet(@PathVariable Integer trajetId,
                                                   @PathVariable Integer conducteurId) {
        return messageRepo.findContactsPourTrajet(trajetId, conducteurId);
    }
}