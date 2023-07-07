package co.mendes.example.user.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	public Object findAll(int page, int size) {
        var pagging = PageRequest.of(page, size, Sort.by("name"));        
		return userRepository.findAll(pagging).stream().map(a -> new CreatedUserDTO(a.getId(), a.getName(), a.getEmail()));
	}

	public Page<CreatedUserDTO> findAll(Pageable pageable) {
        
        Page<UserEntity> users = userRepository.findAll(pageable);

        List<CreatedUserDTO> createdUsers = users.getContent().stream().map(a -> new CreatedUserDTO(a.getId(), a.getName(), a.getEmail())).toList();

        return new PageImpl<>(createdUsers, pageable, createdUsers.size());
	}
    

}