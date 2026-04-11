package YallahEmsi.repositories;

import YallahEmsi.entities.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Integer> {
    List<Campus> findByVilleId(Integer villeId);
}