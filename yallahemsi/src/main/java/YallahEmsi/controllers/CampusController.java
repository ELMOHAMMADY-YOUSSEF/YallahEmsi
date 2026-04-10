package YallahEmsi.controllers;

import YallahEmsi.entities.Campus;
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
}