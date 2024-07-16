package alurachallenge.foro_hub.models.data.subject;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectUpdateDTO {

    @NotNull
    @Size(min = 3, message = "Titulo debe tener almenos 3 caracteres!")
    @Size(max = 150, message = "Titulo puede tener maximo 45 caracteres!")
    private String titulo;

    @NotNull
    @Size(min = 3, message = "Mensaje debe tener almenos 3 caracteres!")
    @Size(max = 1500, message = "Mensaje puede tener maximo 1500 caracteres!")
    private String mensaje;

    @NotNull
    private Genre genre;

}
