package app.msacademico.entity;

import java.time.LocalTime;
import app.msacademico.enums.Dia;
import app.msacademico.enums.Turno;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Dia dia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Turno turno;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Dia getDia() { return dia; }
    public void setDia(Dia dia) { this.dia = dia; }
    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
}