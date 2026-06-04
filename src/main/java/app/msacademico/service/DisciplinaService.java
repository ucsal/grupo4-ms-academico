package app.msacademico.service;

import app.msacademico.dto.DisciplinaDTO;
import app.msacademico.entity.Disciplina;
import app.msacademico.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Camada de serviço responsável pela lógica de negócio da Disciplina
@Service
public class DisciplinaService {

    // Injeção do repositório para acesso ao banco de dados
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Salva uma nova disciplina convertendo o DTO para entidade
    public String save(DisciplinaDTO dto) {
        // Cria uma nova entidade e copia os dados do DTO para ela
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(dto.getNome());
        disciplina.setDescricao(dto.getDescricao());
        disciplina.setSigla(dto.getSigla());
        disciplina.setCargaHoraria(dto.getCargaHoraria());
        disciplina.setData(dto.getData());
        disciplina.setEscolaId(dto.getEscolaId());

        this.disciplinaRepository.save(disciplina);
        return disciplina.getNome() + " salva com sucesso.";
    }

    // Atualiza uma disciplina existente buscando pelo ID
    public String update(DisciplinaDTO dto, long id) {
        // Busca a disciplina no banco, lança exceção se não encontrar
        Disciplina disciplinaExistente = this.disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada no sistema."));

        // Atualiza apenas os campos recebidos no DTO
        disciplinaExistente.setNome(dto.getNome());
        disciplinaExistente.setDescricao(dto.getDescricao());
        disciplinaExistente.setSigla(dto.getSigla());
        disciplinaExistente.setCargaHoraria(dto.getCargaHoraria());
        disciplinaExistente.setData(dto.getData());
        disciplinaExistente.setEscolaId(dto.getEscolaId());

        this.disciplinaRepository.save(disciplinaExistente);
        return "Disciplina atualizada com sucesso.";
    }

    // Inativa uma disciplina sem deletá-la do banco (soft delete)
    public String inativar(long id) {
        Disciplina disciplina = this.disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        disciplina.setStatusAtivo(false);
        this.disciplinaRepository.save(disciplina);
        return "Disciplina " + disciplina.getNome() + " inativada com sucesso.";
    }

    // Reativa uma disciplina que estava inativa
    public String reativar(long id) {
        Disciplina disciplina = this.disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
        disciplina.setStatusAtivo(true);
        this.disciplinaRepository.save(disciplina);
        return "Disciplina " + disciplina.getNome() + " reativada com sucesso.";
    }

    // Retorna apenas as disciplinas ativas
    public List<Disciplina> findAll() {
        return this.disciplinaRepository.findByStatusAtivo(true);
    }

    // Retorna apenas as disciplinas inativas
    public List<Disciplina> findAllInativos() {
        return this.disciplinaRepository.findByStatusAtivo(false);
    }

    // Retorna as disciplinas de uma escola específica pelo ID da escola
    public List<Disciplina> findByEscola(Long escolaId) {
        return this.disciplinaRepository.findByEscolaId(escolaId);
    }

    // Busca uma disciplina pelo ID, lança exceção se não encontrar
    public Disciplina findById(long id) {
        return this.disciplinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disciplina não encontrada."));
    }
}