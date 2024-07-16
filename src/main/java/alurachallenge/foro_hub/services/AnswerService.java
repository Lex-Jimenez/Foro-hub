package alurachallenge.foro_hub.services;

import alurachallenge.foro_hub.exceptions.BadRequestException;
import alurachallenge.foro_hub.exceptions.ResourceNotFoundException;
import alurachallenge.foro_hub.models.Answer;
import alurachallenge.foro_hub.models.Subject;
import alurachallenge.foro_hub.models.User;
import alurachallenge.foro_hub.models.data.answer.AnswerDTO;
import alurachallenge.foro_hub.repository.iRepositoryAnswer;
import alurachallenge.foro_hub.repository.iRepositorySubject;
import alurachallenge.foro_hub.repository.iRepositoryUser;
import alurachallenge.foro_hub.services.iServices.iRespuestaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerService implements iRespuestaService {

    private final iRepositoryAnswer respuestaRepository;
    private final iRepositoryUser usuarioRepository;
    private final iRepositorySubject temaRepository;

    @Override
    public List<AnswerDTO> findAll() {
        List<Answer> answer = respuestaRepository.findAll();
        return answer.stream()
                .map(this::manejoRespuesta)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AnswerDTO> paginate(Pageable pageable) {
        Page<Answer> respuesta = respuestaRepository.findAll(pageable);
        return respuesta.map(this::manejoRespuesta);
    }

    @Override
    public AnswerDTO findById(Integer id) {
        Answer answer = respuestaRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ERROR ID: Respuesta no encontrada!"));
        return manejoRespuesta(answer);
    }

    @Override
    public AnswerDTO save(AnswerDTO answerDTO) {


        if (answerDTO.getUsuarioId() == null || answerDTO.getTemaId() == null) {
            throw new IllegalArgumentException("Usuario ID and Tema ID must not be null");
        }

        // Verificar si ya existe una respuesta con el mismo mensaje para el mismo tema
        boolean existeRespuesta = respuestaRepository.existsByMensajeRespuestaAndTemaIdId(answerDTO.getMensajeRespuesta(), answerDTO.getTemaId());
        if (existeRespuesta) {
            throw new BadRequestException("La respuesta ya existe para este tema");
        }

        User user =  usuarioRepository.findById(answerDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));

        Subject subject = temaRepository.findById(answerDTO.getTemaId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: tema id no encontrado!"));

        Answer answer = new Answer();
        answer.setMensajeRespuesta(answerDTO.getMensajeRespuesta());
        answer.setSubjectId(subject);
        answer.setUserId(user);
        answer.setActivo(Boolean.TRUE);
        answer.setCreatedAt(LocalDateTime.now());

        Answer savedAnswer = respuestaRepository.save(answer);
        return manejoRespuesta(savedAnswer);
    }


    @Override
    public AnswerDTO update(Integer id, AnswerDTO answerDTO) {
        Answer answer = respuestaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Respuesta no encontrado con ID: " + id));

        boolean existeRespuesta = respuestaRepository.existsByMensajeRespuestaAndTemaIdIdAndIdNot(answerDTO.getMensajeRespuesta(), answerDTO.getTemaId(), id);

        if (existeRespuesta) {
            throw new BadRequestException("La respuesta ya existe para este tema");
        }

        User user =  usuarioRepository.findById(answerDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));

        Subject subject = temaRepository.findById(answerDTO.getTemaId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: tema id no encontrado!"));

        answer.setMensajeRespuesta(answerDTO.getMensajeRespuesta());
        answer.setUpdatedAt(LocalDateTime.now());

        Answer savedAnswer = respuestaRepository.save(answer);
        return manejoRespuesta(savedAnswer);
    }

    @Override
    public Boolean delete(Integer id) {
        respuestaRepository.deleteById(id);
        return true;
    }

    public AnswerDTO manejoRespuesta(Answer answer){
        AnswerDTO answerDTO = new ModelMapper().map(answer, AnswerDTO.class);
        return answerDTO;
    }

}
