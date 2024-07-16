package alurachallenge.foro_hub.config;
import alurachallenge.foro_hub.security.jwt.JwtConfig;
import alurachallenge.foro_hub.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @Value("${api.security.secret}")
    private String jwtSecreto;
    @Value("${api.security.validity-in-seconds}")
    private Long jwtValidacionEnSegundos;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        JwtConfig jwtConfig = new JwtConfig(proveedorDeToken());

        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        a->a
                                .requestMatchers("/api/admin/**")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .permitAll()
                )
                .sessionManagement(h->h.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .apply(jwtConfig);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenProvider proveedorDeToken() {
        return new TokenProvider(jwtSecreto, jwtValidacionEnSegundos);
    }

}
