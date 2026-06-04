package app.msacademico.repository;

import app.msacademico.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    List<Disciplina> findByEscolaId(Long escolaId);

    List<Disciplina> findByStatusAtivo(boolean statusAtivo);
}