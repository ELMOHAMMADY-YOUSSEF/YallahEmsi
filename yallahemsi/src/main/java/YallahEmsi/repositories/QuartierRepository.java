package YallahEmsi.repositories;

import YallahEmsi.entities.Quartier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuartierRepository extends JpaRepository<Quartier, Integer> {
}