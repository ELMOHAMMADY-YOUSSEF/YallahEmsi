package YallahEmsi.controllers;
import YallahEmsi.entities.Ville;
import YallahEmsi.repositories.VilleRepository; // (Khassk t-creeyi had repo fabor)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/villes")
public class VilleController {
    @Autowired private VilleRepository villeRepository;
    @GetMapping("/tous") public List<Ville> getAllVilles() { return villeRepository.findAll(); }
}