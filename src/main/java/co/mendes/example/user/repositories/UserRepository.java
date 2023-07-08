package co.mendes.example.user.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.mendes.example.user.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID>  {

    @Query("select u from UserEntity u where u.email  = :email")
    List<UserEntity> findByEmail(@Param("email") String email);
   
}