package alurachallenge.foro_hub.controllers;

import alurachallenge.foro_hub.models.data.user.User;
import alurachallenge.foro_hub.models.data.user.UserRegisterDTO;
import alurachallenge.foro_hub.services.iServices.iUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/usuario")
public class UserController {

    @Autowired
    private iUserService usuarioServices;

    @GetMapping(value = "/list")
    private List<User> findAll(){
        return usuarioServices.findAll();
    }

    @GetMapping
    public Page<User> paginate(@PageableDefault(sort = "nombre", direction = Sort.Direction.ASC, size = 10) Pageable pageable) {
        return usuarioServices.paginate(pageable);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    private User findById(@PathVariable(value = "id") Integer id){
        return usuarioServices.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private User save(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        return usuarioServices.save(userRegisterDTO);
    }

    @PutMapping(value = "/{id}")
    private User update(@PathVariable(value = "id") Integer id, @RequestBody @Valid UserRegisterDTO userRegisterDTO){
        return usuarioServices.update(id, userRegisterDTO);
    }

    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> eliminarUsuario(@PathVariable(value = "id") Integer id){
        return usuarioServices.eliminarUsuario(id);
    }

}
