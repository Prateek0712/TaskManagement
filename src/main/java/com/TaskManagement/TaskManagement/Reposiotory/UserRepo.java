package com.TaskManagement.TaskManagement.Reposiotory;

import com.TaskManagement.TaskManagement.Enity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

}
