package com.example.SpringBootTestUnit.repositories;

import com.example.SpringBootTestUnit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
}
