package alurachallenge.foro_hub.models.data.authentication;

import alurachallenge.foro_hub.models.data.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Role role;
    private String filePerfil;

}