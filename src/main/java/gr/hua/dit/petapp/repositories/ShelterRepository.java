package gr.hua.dit.petapp.repositories;

import gr.hua.dit.petapp.entities.Shelter;
import gr.hua.dit.petapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Integer> {
    Optional<Shelter> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}

