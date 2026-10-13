package com.glicocontrol.backend.repositories;


import com.glicocontrol.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    // O JpaRepository já nos dá métodos prontos como .save(), .findById(), .findAll()
}
