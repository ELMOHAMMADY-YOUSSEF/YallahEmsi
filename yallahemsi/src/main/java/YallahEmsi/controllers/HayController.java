package YallahEmsi.controllers;

import YallahEmsi.entities.Hay;
import YallahEmsi.repositories.HayRepository;
import YallahEmsi.services.HayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/hays")
public class HayController {

    @Autowired
    private HayService hayService;

    // 1. Zidna l'Injection dyal Repository hna:
    @Autowired
    private HayRepository hayRepository;

    @GetMapping("/tous")
    public List<Hay> getAllHays() {
        return hayService.getAllHays();
    }

    @GetMapping("/ville/{villeId}")
    public List<Hay> getHaysByVille(@PathVariable Integer villeId) {
        // 2. Rdinaha b 'h' sghira
        return hayRepository.findByVilleId(villeId);
    }
}