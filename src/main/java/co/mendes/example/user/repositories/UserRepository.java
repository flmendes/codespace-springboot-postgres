package co.mendes.example.user.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.mendes.example.user.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>  {
    
}