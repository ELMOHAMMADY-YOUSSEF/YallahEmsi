package YallahEmsi.controllers;

import YallahEmsi.entities.Campus;
import YallahEmsi.repositories.CampusRepository;
import YallahEmsi.services.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/campus")
public class CampusController {

    @Autowired
    private CampusService campusService;

    @GetMapping("/tous")
    public List<Campus> getAllCampus() {
        return campusService.getAllCampus();
    }

    @Autowired private CampusRepository campusRepository;
    @GetMapping("/ville/{villeId}")
    public List<Campus> getCampusByVille(@PathVariable Integer villeId) {
        return campusRepository.findByVilleId(villeId);
    }
}