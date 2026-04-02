package YallahEmsi.controllers;

import YallahEmsi.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // API bach n-reserviw (kan-ssifto l'id dyal passager, trajet, w ch7al mn blassa bgha)
    @PostMapping("/nouvelle")
    public String reserver(@RequestParam Integer passagerId,
                           @RequestParam Integer trajetId,
                           @RequestParam Integer places) {
        return reservationService.reserverTrajet(passagerId, trajetId, places);
    }
}