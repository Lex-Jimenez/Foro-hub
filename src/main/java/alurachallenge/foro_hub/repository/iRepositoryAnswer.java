package alurachallenge.foro_hub.repository;

import alurachallenge.foro_hub.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iRepositoryAnswer extends JpaRepository<Answer, Integer> {

    boolean existsByMensajeRespuestaAndTemaIdId(String mensajeRespuesta, Integer temaId);

    boolean existsByMensajeRespuestaAndTemaIdIdAndIdNot(String mensajeRespuesta, Integer temaId, Integer id);
}
