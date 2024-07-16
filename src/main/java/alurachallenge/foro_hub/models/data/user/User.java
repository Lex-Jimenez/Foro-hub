package alurachallenge.foro_hub.models.data.user;

import alurachallenge.foro_hub.models.data.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Role role;
    private String filePerfil;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
