package app.msacademico.dto;

import java.time.LocalDate;

public class DisciplinaDTO {

    private String nome;

    private String descricao;

    private String sigla;

    private Integer cargaHoraria;

    private LocalDate data;

    // ID da escola à qual a disciplina pertence
    // Usando apenas o ID pois a entidade Escola pertence ao microsserviço Institucional
    private Long escolaId;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public Integer getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(Integer cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public Long getEscolaId() { return escolaId; }
    public void setEscolaId(Long escolaId) { this.escolaId = escolaId; }
}

// Essa classe define exatamente quais dados o cliente deve enviar na requisição
// Ela evita expor diretamente a entidade Disciplina para o mundo externo