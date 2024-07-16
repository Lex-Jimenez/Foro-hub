package alurachallenge.foro_hub.services.iServices;

import alurachallenge.foro_hub.models.data.user.User;
import alurachallenge.foro_hub.models.data.user.UserRegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface iUserService {

    List<User> findAll();
    Page<User> paginate(Pageable pageable);
    User findById(Integer id);
    User save(UserRegisterDTO userRegisterDTO);
    User update(Integer id, UserRegisterDTO userRegisterDTO);
    //    Boolean delete(Integer id);
    ResponseEntity eliminarUsuario(Integer id);

}
