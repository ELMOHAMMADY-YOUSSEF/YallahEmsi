package YallahEmsi.repositories;

import YallahEmsi.entities.Campus;
import YallahEmsi.entities.Hay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HayRepository extends JpaRepository<Hay, Integer> {
    List<Hay> findByVilleId(Integer villeId);

}