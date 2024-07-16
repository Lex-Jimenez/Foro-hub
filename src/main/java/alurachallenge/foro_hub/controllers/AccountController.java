package alurachallenge.foro_hub.controllers;

import alurachallenge.foro_hub.exceptions.BadRequestException;
import alurachallenge.foro_hub.models.User;
import alurachallenge.foro_hub.models.data.Role;
import alurachallenge.foro_hub.models.data.authentication.UserProfileDTO;
import alurachallenge.foro_hub.models.data.user.UserRegisterDTO;
import alurachallenge.foro_hub.repository.iRepositoryUser;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class AccountController {

    private final iRepositoryUser usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/registro")
    public UserProfileDTO registro(@RequestBody @Validated UserRegisterDTO userRegisterDTO){
        boolean emailExists = usuarioRepository.existsByEmail(userRegisterDTO.getEmail());
        if (emailExists){
            throw new BadRequestException("ERROR EMAIL DUPLICADO: El email ya existe!");
        }
        //Usuario usuario = new Usuario();
        User user = new ModelMapper().map(userRegisterDTO, User.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        user.setActivo(Boolean.TRUE);
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        usuarioRepository.save(user);

        return new ModelMapper().map(user, UserProfileDTO.class);
    }

}

