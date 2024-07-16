package alurachallenge.foro_hub.controllers;

import alurachallenge.foro_hub.exceptions.ResourceNotFoundException;
import alurachallenge.foro_hub.models.Subject;
import alurachallenge.foro_hub.models.data.subject.Genre;
import alurachallenge.foro_hub.repository.iRepositorySubject;
import alurachallenge.foro_hub.services.iServices.iHomeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/home")
@AllArgsConstructor
public class HomeController implements iHomeService {

    private final iRepositorySubject temaRepository;

    @Override
    @GetMapping("/categoria/{genero}")
    public List<alurachallenge.foro_hub.models.data.subject.Subject> findByGenero(@PathVariable(value = "genero") Genre genre) {

        List<Subject> subjects = temaRepository.findByGenero(genre);

        if (subjects == null || subjects.isEmpty()){
            throw new ResourceNotFoundException("Genero no encontrado!");
        }else {
            return subjects.stream()
                    .map(this::manejorRespuestaClienteCorta)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/fecha/{date}")
    public List<alurachallenge.foro_hub.models.data.subject.Subject> getTemasByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Subject> temasPorFecha = temaRepository.findTemasByDate(date);

        if (temasPorFecha == null || temasPorFecha.isEmpty()){
            throw new ResourceNotFoundException("No hay Temas en la fecha ingresada!");
        }else {
            return temasPorFecha.stream()
                    .map(this::manejorRespuestaClienteCorta)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/last-temas")
    List<alurachallenge.foro_hub.models.data.subject.Subject> getLastTemas(){
        List<Subject> subjects =temaRepository.findTop9ByOrderByCreatedAtDesc();

        if (subjects == null || subjects.isEmpty()){
            throw new ResourceNotFoundException("Temas no encontrados!");
        }else {
            return subjects.stream()
                    .map(this::manejorRespuestaClienteCorta)
                    .collect(Collectors.toList());
        }
    }

    @GetMapping("/topicos")
    private Page<alurachallenge.foro_hub.models.data.subject.Subject> paginate(@PageableDefault(sort = "createdAt",direction = Sort.Direction.ASC ,size = 10 ) Pageable pageable){
        Page<Subject> temas =temaRepository.findAll(pageable);
        return temas.map(this::manejorRespuestaClienteCorta);

    }

    private alurachallenge.foro_hub.models.data.subject.Subject manejorRespuestaClienteCorta(Subject tema) {
        alurachallenge.foro_hub.models.data.subject.Subject subject = new ModelMapper().map(tema, alurachallenge.foro_hub.models.data.subject.Subject.class);
        subject.setFilePerfil(tema.getUserId().getFilePerfil());
        return subject;
    }
}