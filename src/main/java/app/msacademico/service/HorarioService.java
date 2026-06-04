package app.msacademico.service;

import app.msacademico.dto.HorarioDTO;
import app.msacademico.entity.Horario;
import app.msacademico.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

// Camada de serviço responsável pela lógica de negócio do Horário
@Service
public class HorarioService {

    // Injeção do repositório para acesso ao banco de dados
    @Autowired
    private HorarioRepository horarioRepository;

    // Salva um novo horário convertendo o DTO para entidade
    public String save(HorarioDTO dto) {
        // Cria uma nova entidade e copia os dados do DTO para ela
        Horario horario = new Horario();
        horario.setDia(dto.getDia());
        horario.setTurno(dto.getTurno());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFim(dto.getHoraFim());

        this.horarioRepository.save(horario);
        return "Horário salvo com sucesso.";
    }

    // Atualiza um horário existente buscando pelo ID
    public String update(HorarioDTO dto, long id) {
        // Busca o horário no banco, lança exceção se não encontrar
        Horario horarioExistente = this.horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado."));

        // Atualiza apenas os campos recebidos no DTO
        horarioExistente.setDia(dto.getDia());
        horarioExistente.setTurno(dto.getTurno());
        horarioExistente.setHoraInicio(dto.getHoraInicio());
        horarioExistente.setHoraFim(dto.getHoraFim());

        this.horarioRepository.save(horarioExistente);
        return "Horário atualizado com sucesso.";
    }

    // Deleta um horário permanentemente pelo ID
    public String delete(long id) {
        this.horarioRepository.deleteById(id);
        return "Horário deletado.";
    }

    // Retorna todos os horários cadastrados
    public List<Horario> findAll() {
        return this.horarioRepository.findAll();
    }

    // Busca um horário pelo ID, lança exceção se não encontrar
    public Horario findById(long id) {
        return this.horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado."));
    }
}