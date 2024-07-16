package alurachallenge.foro_hub.models.data.subject;

import alurachallenge.foro_hub.models.data.answer.SubjectAnswerDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Subject {

    private Integer id;

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

    @NotNull
    private Integer usuarioId;

    private String usuarioNombre;

    private String filePerfil;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean activo;

    private List<SubjectAnswerDTO> respuestas;

}
