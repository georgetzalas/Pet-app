package gr.hua.dit.petapp.repositories;

import gr.hua.dit.petapp.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Boolean existsByUsername(String username);
}
