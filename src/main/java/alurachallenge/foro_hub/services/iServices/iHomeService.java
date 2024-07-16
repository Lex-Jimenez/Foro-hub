package alurachallenge.foro_hub.services.iServices;

import alurachallenge.foro_hub.models.data.subject.Genre;
import alurachallenge.foro_hub.models.data.subject.Subject;

import java.time.LocalDate;
import java.util.List;

public interface iHomeService {

    List<Subject> findByGenero(Genre genre);
    List<Subject> getTemasByDate(LocalDate localDate);


}
