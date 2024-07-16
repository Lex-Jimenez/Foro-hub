package alurachallenge.foro_hub.models.data.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthAnswer {

    private String token;

    @JsonProperty("usuario")
    private UserProfileDTO userProfileDTO;

}
