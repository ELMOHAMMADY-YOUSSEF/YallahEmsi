package YallahEmsi.repositories;

import YallahEmsi.entities.Hay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HayRepository extends JpaRepository<Hay, Integer> {
}