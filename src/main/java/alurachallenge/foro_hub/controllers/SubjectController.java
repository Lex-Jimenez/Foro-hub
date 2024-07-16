package alurachallenge.foro_hub.controllers;

import alurachallenge.foro_hub.models.data.subject.Subject;
import alurachallenge.foro_hub.models.data.subject.SubjectUpdateDTO;
import alurachallenge.foro_hub.services.iServices.iSubjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SubjectController {

    @Autowired
    private iSubjectService temaService;

    @GetMapping(value = "/admin/topicos/list")
    private List<Subject> findAll(){
        return temaService.findAll();
    }

    @GetMapping(value = "/admin/topicos")
    private Page<Subject> paginate(@PageableDefault(sort = "createdAt",direction = Sort.Direction.ASC ,size = 10 ) Pageable pageable){
        return temaService.paginate(pageable);
    }

    @GetMapping(value = "/topicos/{id}")
    private Subject findById(@PathVariable(value = "id") Integer id){
        return temaService.findById(id);
    }

    @PostMapping(value = "/topicos")
    private Subject save(@RequestBody @Valid Subject subject){
        return temaService.save(subject);
    }

    @PutMapping(value = "/topicos/{id}")
    private Subject update(@PathVariable(value = "id") Integer id, @RequestBody @Valid SubjectUpdateDTO subjectUpdateDTO){
        return temaService.update(id, subjectUpdateDTO);
    }

    @DeleteMapping(value = "/topicos/{id}")
    private Boolean delete(@PathVariable(value = "id") Integer id){
        return temaService.delete(id);
    }

}
