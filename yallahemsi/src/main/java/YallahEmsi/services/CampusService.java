package YallahEmsi.services;

import YallahEmsi.entities.Campus;
import YallahEmsi.repositories.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CampusService {
    @Autowired
    private CampusRepository campusRepository;

    public List<Campus> getAllCampus() {
        return campusRepository.findAll();
    }
}