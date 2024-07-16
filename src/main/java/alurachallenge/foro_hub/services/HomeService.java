package alurachallenge.foro_hub.services;

import alurachallenge.foro_hub.models.data.subject.Genre;
import alurachallenge.foro_hub.models.data.subject.Subject;
import alurachallenge.foro_hub.services.iServices.iHomeService;

import java.time.LocalDate;
import java.util.List;

public class HomeService implements iHomeService {

    @Override
    public List<Subject> findByGenero(Genre genre) {
        return null;
    }

    @Override
    public List<Subject> getTemasByDate(LocalDate localDate) {
        return null;
    }
}
