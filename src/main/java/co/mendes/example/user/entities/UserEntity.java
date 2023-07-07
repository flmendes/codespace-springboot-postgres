package co.mendes.example.user.entities;

import java.io.Serializable;
import java.util.UUID;

public class UserEntity implements Serializable {

    public UUID id;

    public String name;

    public String email;

}