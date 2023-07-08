package co.mendes.example.user.controllers;

import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.domain.Sort;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.mendes.example.user.dtos.CreateUserDTO;
import co.mendes.example.user.dtos.CreatedUserDTO;
import co.mendes.example.user.services.UserService;

@RestController
// @RequestMapping("/api")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<CreatedUserDTO> createUser(@RequestBody CreateUserDTO createUserDTO){
        CreatedUserDTO create = userService.create(createUserDTO);
        return new ResponseEntity<>(create, HttpStatus.CREATED);
    }    

    @GetMapping("/users")
    public Page<CreatedUserDTO> listUser(
        @PageableDefault(page=0, size = 10) 
        @SortDefault.SortDefaults({
            @SortDefault(sort = "name", direction = Sort.Direction.DESC),
            @SortDefault(sort = "id", direction = Sort.Direction.ASC)
    }) Pageable pageable)
    {
        return userService.findAll(pageable);
    }

    @PostMapping("/by-email")
    public ResponseEntity<CreatedUserDTO> findByEmail(@RequestBody Map<String, String> body){
        return new ResponseEntity<>(userService.findByEmail(body.get("email")), HttpStatus.OK);
    }

}
