package atlassports.repository;

import atlassports.model.VenueImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueImagesRepository extends JpaRepository<VenueImages, Long> {
}
