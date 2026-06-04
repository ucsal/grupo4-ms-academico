package app.msacademico.dto;

import app.msacademico.enums.Dia;
import app.msacademico.enums.Turno;
import java.time.LocalTime;

public class HorarioDTO {

    private Dia dia;

    private Turno turno;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    public Dia getDia() { return dia; }
    public void setDia(Dia dia) { this.dia = dia; }

    public Turno getTurno() { return turno; }
    public void setTurno(Turno turno) { this.turno = turno; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }
}

// Essa classe define exatamente quais dados o cliente deve enviar na requisição
// Ela evita expor diretamente a entidade Horario para o mundo externo
