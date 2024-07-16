package alurachallenge.foro_hub.services;

import alurachallenge.foro_hub.exceptions.BadRequestException;
import alurachallenge.foro_hub.exceptions.ResourceNotFoundException;
import alurachallenge.foro_hub.models.User;
import alurachallenge.foro_hub.models.data.user.UserRegisterDTO;
import alurachallenge.foro_hub.repository.iRepositoryUser;
import alurachallenge.foro_hub.services.iServices.iUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements iUserService {

    private final iRepositoryUser usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<alurachallenge.foro_hub.models.data.user.User> findAll() {
        List<User> users = usuarioRepository.findAll();
        return users.stream()
                .map(this::manejoRespuestaUsuario)
                .collect(Collectors.toList());
    }

    @Override
    public Page<alurachallenge.foro_hub.models.data.user.User> paginate(Pageable pageable) {
        Page<User> usuarios = usuarioRepository.findAll(pageable);
        return usuarios.map(this::manejoRespuestaUsuario);
    }

    @Override
    public alurachallenge.foro_hub.models.data.user.User findById(Integer id) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERROR ID: id no encontrado en la base de datos!"));
        return manejoRespuestaUsuario(user);
    }

    public alurachallenge.foro_hub.models.data.user.User save(UserRegisterDTO userRegisterDTO) {
        boolean usuarioExiste =usuarioRepository.existsByEmail(userRegisterDTO.getEmail());

        if (usuarioExiste){
            throw new BadRequestException("El email ya existe!");
        }

        User user = null;
        try{
            user = new ModelMapper().map(userRegisterDTO, User.class);

            user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
            user.setActivo(Boolean.TRUE);
            user.setCreatedAt(LocalDateTime.now());
            user = usuarioRepository.save(user);
        }catch (DataAccessException e){
            throw new BadRequestException("ERROR CREACION: Falla no es posible realizar el proceso!", e);
        }
        return manejoRespuestaUsuario(user);
    }

    @Override
    public alurachallenge.foro_hub.models.data.user.User update(Integer id, UserRegisterDTO userRegisterDTO) {
        User user = usuarioRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ERROR ID: usuario id no encontrado!"));

        boolean usuarioExiste =usuarioRepository.existsByEmailAndIdNot(userRegisterDTO.getEmail(), id);

        if (usuarioExiste){
            throw new BadRequestException("El email ya existe!");
        }

        try {
            if (user != null){
                user.setNombre(userRegisterDTO.getNombre());
                user.setEmail(userRegisterDTO.getEmail());
                user.setRole(userRegisterDTO.getRole());
                user.setFilePerfil(userRegisterDTO.getFilePerfil());
                user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
                user.setUpdatedAt(LocalDateTime.now());
            }else {
                throw new BadRequestException("ERROR ACTUALIZAR: Usuario no se pudo actualizar!");
            }
        }catch (DataAccessException e){
            throw new BadRequestException("ERROR ACTUALIZACION: Falla no es posible realizar el proceso!" , e);
        }
        user = usuarioRepository.save(user);
        return manejoRespuestaUsuario(user);
    }


    public ResponseEntity<?> eliminarUsuario(Integer id) {
        User user = usuarioRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.desactivarUsuario();
        usuarioRepository.save(user);
        return ResponseEntity.noContent().build();
    }


    private alurachallenge.foro_hub.models.data.user.User manejoRespuestaUsuario(User usuario) {

        alurachallenge.foro_hub.models.data.user.User user = new ModelMapper().map(usuario, alurachallenge.foro_hub.models.data.user.User.class);
        return user;
    }
}

