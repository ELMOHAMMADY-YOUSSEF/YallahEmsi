package YallahEmsi.repositories;

import YallahEmsi.entities.CNEValide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Mola7ada: Hna drna String f blast Integer, 7it l'Id dyal CNE howa varchar (String)
public interface CNEValideRepository extends JpaRepository<CNEValide, String> {
}