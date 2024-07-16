package alurachallenge.foro_hub.services;

import alurachallenge.foro_hub.exceptions.BadRequestException;
import alurachallenge.foro_hub.exceptions.ResourceNotFoundException;
import alurachallenge.foro_hub.models.Answer;
import alurachallenge.foro_hub.models.Subject;
import alurachallenge.foro_hub.models.User;
import alurachallenge.foro_hub.models.data.answer.SubjectAnswerDTO;
import alurachallenge.foro_hub.models.data.subject.SubjectUpdateDTO;
import alurachallenge.foro_hub.repository.iRepositorySubject;
import alurachallenge.foro_hub.repository.iRepositoryUser;
import alurachallenge.foro_hub.services.iServices.iSubjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements iSubjectService {

    @Autowired
    private iRepositorySubject temaRepository;

    @Autowired
    private iRepositoryUser usuarioRepository;

    @Override
    public List<alurachallenge.foro_hub.models.data.subject.Subject> findAll() {
        List<Subject> subjects = temaRepository.findAll();
        return subjects.stream()
                .map(tema -> manejoRespuestaCliente(tema, true))
                .collect(Collectors.toList());
    }

    @Override
    public Page<alurachallenge.foro_hub.models.data.subject.Subject> paginate(Pageable pageable) {
        Page<Subject> temas = temaRepository.findAll(pageable);
        return temas.map(tema -> manejoRespuestaCliente(tema, true));
    }

    public alurachallenge.foro_hub.models.data.subject.Subject findById(Integer id) {
        Subject subject = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado con ID: " + id));
        return manejoRespuestaCliente(subject, true);
    }

    public alurachallenge.foro_hub.models.data.subject.Subject save(alurachallenge.foro_hub.models.data.subject.Subject subject) {
        boolean tituloExiste = temaRepository.existsByTitulo(subject.getTitulo());
        boolean mensajeExiste = temaRepository.existsByMensaje(subject.getMensaje());

        if (tituloExiste ) {
            throw new BadRequestException("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new BadRequestException("El mensaje ya exisite!");
        }

        User user = usuarioRepository.findById(subject.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));


        Subject tema = new ModelMapper().map(subject, Subject.class);
        try {
            tema.setCreatedAt(LocalDateTime.now());
            tema.setUserId(user);
            tema.setActivo(Boolean.TRUE);
            tema = temaRepository.save(tema);
        }catch (DataAccessException e){
            throw new BadRequestException("ERROR CREACION TEMA: Falla no es posible realizar el proceso!", e);
        }
        return manejoRespuestaCliente(tema, false);
    }

    public alurachallenge.foro_hub.models.data.subject.Subject update(Integer id, SubjectUpdateDTO subjectUpdateDTO) {
        Subject subject = temaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado con ID: " + id));

        boolean tituloExiste = temaRepository.existsByTituloAndIdNot(subjectUpdateDTO.getTitulo(), id);
        boolean mensajeExiste = temaRepository.existsByMensajeAndIdNot(subjectUpdateDTO.getMensaje(), id);

        if (tituloExiste ) {
            throw new BadRequestException("El titulo ya existe!");
        }
        if (mensajeExiste) {
            throw new BadRequestException("El mensaje ya exisite!");
        }

        try{
            if (subject != null){
                subject.setTitulo(subjectUpdateDTO.getTitulo());
                subject.setMensaje(subjectUpdateDTO.getMensaje());
                subject.setGenre(subjectUpdateDTO.getGenre());
                subject.setUpdatedAt(LocalDateTime.now());
                subject = temaRepository.save(subject);
            }else {
                throw new BadRequestException("ERROR ACTUALIZAR: No se pudo actualizar tema!");
            }
        }catch (DataAccessException e){
            throw new BadRequestException("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        return manejoRespuestaCliente(subject, false);
    }

    @Override
    public Boolean delete(Integer id) {
        temaRepository.deleteById(id);
        return true;
    }

    private alurachallenge.foro_hub.models.data.subject.Subject manejoRespuestaCliente(Subject tema, boolean incluirRespuestas) {
        alurachallenge.foro_hub.models.data.subject.Subject subject = new ModelMapper().map(tema, alurachallenge.foro_hub.models.data.subject.Subject.class);

        subject.setFilePerfil(tema.getUserId().getFilePerfil());
//
           if (incluirRespuestas && tema.getAnswers() != null && !tema.getAnswers().isEmpty()) {
            List<SubjectAnswerDTO> respuestasDto = tema.getAnswers().stream()
                    .map(this::manejoRespuesta)
                    .collect(Collectors.toList());
            subject.setRespuestas(respuestasDto);
        }
        return subject;
    }

    private SubjectAnswerDTO manejoRespuesta(Answer answer) {
        SubjectAnswerDTO respuestaDto = new ModelMapper().map(answer, SubjectAnswerDTO.class);
        respuestaDto.setFilePerfilRespuesta(answer.getUserId().getFilePerfil());
        return respuestaDto;
    }

}
