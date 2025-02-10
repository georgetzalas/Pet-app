package gr.hua.dit.petapp.repositories;

import gr.hua.dit.petapp.entities.ApprovalStatus;
import gr.hua.dit.petapp.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByType(String type);
    // Finds pets by their shelter's region
    @Query("SELECT p FROM Pet p WHERE p.shelter.Region = :region")
    List<Pet> findByShelterRegion(@Param("region") String region);

    // Finds pets by both shelter's region and pet type
    @Query("SELECT p FROM Pet p WHERE p.shelter.Region = :region AND p.type = :type")
    List<Pet> findByShelterRegionAndType(@Param("region") String region, @Param("type") String type);

}
