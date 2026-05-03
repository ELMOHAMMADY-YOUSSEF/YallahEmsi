package YallahEmsi.repositories;

import YallahEmsi.entities.Message;
import YallahEmsi.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.trajet.id = :trajetId AND " +
            "((m.expediteur.id = :user1 AND m.destinataire.id = :user2) OR " +
            "(m.expediteur.id = :user2 AND m.destinataire.id = :user1)) " +
            "ORDER BY m.dateEnvoi ASC")
    List<Message> findChatHistory(@Param("trajetId") Integer trajetId, // <--- BEDDEL HADI L-INTEGER
                                  @Param("user1") Integer user1,
                                  @Param("user2") Integer user2);

    // Kat-jbed l-liste dyal n-nas li siftou message l-conducteur f wa7d l-trajet
    @Query("SELECT DISTINCT m.expediteur FROM Message m WHERE m.trajet.id = :trajetId AND m.destinataire.id = :conducteurId")
    List<Utilisateur> findContactsPourTrajet(@Param("trajetId") Integer trajetId, @Param("conducteurId") Integer conducteurId);
}