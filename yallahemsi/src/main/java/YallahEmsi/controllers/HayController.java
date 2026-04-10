package YallahEmsi.controllers;

import YallahEmsi.entities.Hay;
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

    @GetMapping("/tous")
    public List<Hay> getAllHays() {
        return hayService.getAllHays();
    }
}