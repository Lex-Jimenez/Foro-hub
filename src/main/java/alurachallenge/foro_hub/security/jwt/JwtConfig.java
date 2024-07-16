package alurachallenge.foro_hub.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider proveedorDeToken;

    public JwtConfig(TokenProvider proveedorDeToken){
        this.proveedorDeToken = proveedorDeToken;
    }

    public void configure(HttpSecurity http){
        JwtFilter filtroPersonalizado = new JwtFilter(proveedorDeToken);
        http.addFilterBefore(filtroPersonalizado, UsernamePasswordAuthenticationFilter.class);
    }
}
