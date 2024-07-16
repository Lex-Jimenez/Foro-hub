package alurachallenge.foro_hub.services.iServices;

import alurachallenge.foro_hub.models.data.subject.Subject;
import alurachallenge.foro_hub.models.data.subject.SubjectUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iSubjectService {

    List<Subject> findAll();
    Page<Subject> paginate(Pageable pageable);
    Subject findById(Integer id);
    Subject save(Subject subject);
    Subject update(Integer id, SubjectUpdateDTO subjectUpdateDTO);
    Boolean delete(Integer id);

}
