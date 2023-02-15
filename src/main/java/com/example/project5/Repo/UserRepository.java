package com.example.project5.Repo;



import com.example.project5.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<UserEntity, String> {
}
