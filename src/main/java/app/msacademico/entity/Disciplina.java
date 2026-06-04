package app.msacademico.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String sigla;
    private Integer cargaHoraria;
    private LocalDate data;
    private boolean statusAtivo = true;

    @Column(name = "escola_id")
    private Long escolaId;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public boolean isStatusAtivo() { return statusAtivo; }
    public void setStatusAtivo(boolean statusAtivo) { this.statusAtivo = statusAtivo; }
    public Long getEscolaId() { return escolaId; }
    public void setEscolaId(Long escolaId) { this.escolaId = escolaId; }
}