package alurachallenge.foro_hub.models;

import alurachallenge.foro_hub.models.data.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String nombre;

    @Column(length = 100)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "file_perfil")
    private String filePerfil;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private Boolean activo;
    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<Subject> subjects;

    @OneToMany(mappedBy = "usuarioId", fetch = FetchType.LAZY)
    private List<Answer> answers;

    public void desactivarUsuario(){
        this.activo = false;
    }

}
