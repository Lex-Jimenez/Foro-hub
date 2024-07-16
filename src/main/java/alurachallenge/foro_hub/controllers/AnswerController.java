package alurachallenge.foro_hub.controllers;

import alurachallenge.foro_hub.models.data.answer.AnswerDTO;
import alurachallenge.foro_hub.services.AnswerService;
import alurachallenge.foro_hub.services.iServices.iRespuestaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/respuesta")
public class AnswerController {

    @Autowired
    private iRespuestaService respuestaService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.respuestaService = answerService;
    }

    @GetMapping(value = "/list")
    List<AnswerDTO> findAll(){
        return respuestaService.findAll();
    }

    @GetMapping("/{id}")
    public AnswerDTO findById(@PathVariable(value = "id") Integer id){
        return respuestaService.findById(id);
    }

    @GetMapping
    Page<AnswerDTO> paginate(@PageableDefault(sort ="createdAt",direction = Sort.Direction.DESC, size = 10) Pageable pageable){
        return respuestaService.paginate(pageable);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AnswerDTO save(@RequestBody @Valid AnswerDTO answerDTO){
        return respuestaService.save(answerDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/{id}")
    AnswerDTO update(@PathVariable(value = "id") Integer id, @RequestBody @Valid AnswerDTO answerDTO){
        return respuestaService.update(id, answerDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    Boolean delete(@PathVariable(value = "id") Integer id){
        return respuestaService.delete(id);
    }


}
