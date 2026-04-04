package YallahEmsi.controllers;

import YallahEmsi.services.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    // API bach n-khelssou (ex: ?reservationId=1)
    @PostMapping("/payer")
    public String payer(@RequestParam Integer reservationId) {
        return paiementService.payerReservation(reservationId);
    }
}