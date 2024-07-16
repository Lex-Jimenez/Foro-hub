package alurachallenge.foro_hub.security.jwt.ijwt;

import org.springframework.security.core.Authentication;

public interface iTokenProvider {

    String crearToken(Authentication authentication);
    Authentication obtenerAuthenticacion(String token);

    boolean validacionToken(String token);

}
