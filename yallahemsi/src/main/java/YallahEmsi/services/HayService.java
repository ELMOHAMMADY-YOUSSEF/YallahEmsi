package YallahEmsi.services;

import YallahEmsi.entities.Hay;
import YallahEmsi.repositories.HayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HayService {
    @Autowired
    private HayRepository hayRepository;

    public List<Hay> getAllHays() {
        return hayRepository.findAll();
    }
}