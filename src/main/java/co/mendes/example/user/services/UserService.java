package co.mendes.example.user.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import co.mendes.example.user.dtos.CreateUserDTO;
import co.mendes.example.user.dtos.CreatedUserDTO;
import co.mendes.example.user.entities.UserEntity;
import co.mendes.example.user.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CreatedUserDTO findByEmail(String email){
        List<UserEntity> users = userRepository.findByEmail(email);

        Optional<CreatedUserDTO> createdOptionalUser =  users.stream()
        .findFirst()
        .flatMap(u -> Optional.of(new CreatedUserDTO(u.getId(), u.getName(), u.getEmail())));

        return createdOptionalUser.orElseThrow(() -> new RuntimeException("Usuario NÃO encontrado."));
    }

    public CreatedUserDTO create(CreateUserDTO createUserDTO){
        var entity = new UserEntity();
        entity.setEmail(createUserDTO.email());
        entity.setName(createUserDTO.name());
        var savedEntity = userRepository.save(entity);
        return new CreatedUserDTO(
            savedEntity.getId(), 
            savedEntity.getName(), 
            savedEntity.getEmail()
        );
    }

	public Page<CreatedUserDTO> findAll(Pageable pageable) {
        
        Page<UserEntity> users = userRepository.findAll(pageable);

        List<CreatedUserDTO> createdUsers = users.getContent()
        .stream()
        .map(a -> new CreatedUserDTO(a.getId(), a.getName(), a.getEmail())).toList();

        return new PageImpl<>(createdUsers, pageable, createdUsers.size());
	}
    

}