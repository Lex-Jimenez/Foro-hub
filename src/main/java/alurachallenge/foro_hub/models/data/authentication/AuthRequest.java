package alurachallenge.foro_hub.models.data.authentication;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AuthRequest {

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotEmpty
    @NotBlank
    @Pattern(regexp = "[a-z0-9-]+")
    @Size(min = 5, message = "El password debe tener al menos 5 caracteres!")
    private String password;
}
