package alurachallenge.foro_hub.repository;

import alurachallenge.foro_hub.models.Subject;
import alurachallenge.foro_hub.models.data.subject.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface iRepositorySubject extends JpaRepository<Subject,Integer> {
    boolean existsByTitulo(String titulo);
    boolean existsByTituloAndIdNot(String titulo ,Integer id);
    boolean existsByMensaje(String mensaje);
    boolean existsByMensajeAndIdNot(String mensaje, Integer id);
    List<Subject> findByGenero(Genre genre);
    @Query("SELECT t FROM Tema t WHERE DATE(t.createdAt) = :date")
    List<Subject> findTemasByDate(@Param("date") LocalDate date);


    List<Subject> findTop9ByOrderByCreatedAtDesc();

    Page<Subject> findAll(Pageable pageable);
}
