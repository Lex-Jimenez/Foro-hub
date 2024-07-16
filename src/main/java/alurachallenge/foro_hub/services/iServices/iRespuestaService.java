package alurachallenge.foro_hub.services.iServices;

import alurachallenge.foro_hub.models.data.answer.AnswerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iRespuestaService {

    List<AnswerDTO> findAll();
    Page<AnswerDTO> paginate(Pageable pageable);
    AnswerDTO findById(Integer id);
    AnswerDTO save(AnswerDTO answerDTO);
    AnswerDTO update(Integer id, AnswerDTO answerDTO);
    Boolean delete(Integer id);

}
