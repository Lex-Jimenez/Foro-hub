package alurachallenge.foro_hub.controllers;

import alurachallenge.foro_hub.exceptions.ResourceNotFoundException;
import alurachallenge.foro_hub.models.User;
import alurachallenge.foro_hub.models.data.authentication.AuthRequest;
import alurachallenge.foro_hub.models.data.authentication.UserProfileDTO;
import alurachallenge.foro_hub.models.data.authentication.AuthAnswer;
import alurachallenge.foro_hub.repository.iRepositoryUser;
import alurachallenge.foro_hub.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class JwtController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider proveedorDeToken;
    private final iRepositoryUser usuarioRepository;

    @PostMapping(value = "/autenticacion")
    public ResponseEntity<?> autenticacion(@RequestBody @Valid AuthRequest authRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = proveedorDeToken.crearToken(authentication);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+ token);

        User user = usuarioRepository
                .findByEmail(authRequest.getEmail())
                .orElseThrow(ResourceNotFoundException::new);

        AuthAnswer authAnswer = new AuthAnswer(token, new UserProfileDTO(
                user.getId(),
                user.getNombre(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getFilePerfil()
        ));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(authAnswer);

    }

}