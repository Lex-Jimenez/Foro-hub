package alurachallenge.foro_hub.models.data.answer;

import lombok.Data;

@Data
public class SubjectAnswerDTO {
    private Integer id;
    private String mensajeRespuesta;
    private Integer usuarioId;
    private String usuarioNombre;
    private String filePerfilRespuesta;
}
