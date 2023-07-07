package co.mendes.example.user.dtos;

import java.util.UUID;

public record CreatedUserDTO(UUID id, String name, String email) {    
}
