package app.msacademico.controller;

import app.msacademico.dto.HorarioDTO;
import app.msacademico.entity.Horario;
import app.msacademico.service.HorarioService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

// Controller responsável por receber e responder as requisições HTTP do Horário
@RestController
@RequestMapping("api/horario")
@CrossOrigin("*") // Permite requisições de qualquer origem (necessário para o front-end e API Gateway)
public class HorarioController {

    // Injeção do serviço de Horário
    @Autowired
    private HorarioService horarioService;

    // Cria um novo horário — recebe um HorarioDTO no corpo da requisição
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody HorarioDTO dto) {
        return new ResponseEntity<>(horarioService.save(dto), HttpStatus.CREATED);
    }

    // Atualiza um horário existente pelo ID passado na URL
    @PostMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody HorarioDTO dto, @PathVariable long id) {
        return new ResponseEntity<>(horarioService.update(dto, id), HttpStatus.OK);
    }

    // Deleta um horário permanentemente pelo ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        return new ResponseEntity<>(horarioService.delete(id), HttpStatus.OK);
    }

    // Retorna todos os horários cadastrados
    @GetMapping("/findAll")
    public ResponseEntity<List<Horario>> findAll() {
        return new ResponseEntity<>(horarioService.findAll(), HttpStatus.OK);
    }

    // Busca um horário específico pelo ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Horario> findById(@PathVariable long id) {
        return new ResponseEntity<>(horarioService.findById(id), HttpStatus.OK);
    }

    // Exporta todos os horários em formato Excel (.xlsx)
    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarHorariosExcel() throws IOException {
        List<Horario> horarios = horarioService.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Horarios");

            // Define a largura de cada coluna
            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 15 * 256);
            sheet.setColumnWidth(2, 15 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 20 * 256);

            // Cria a linha de cabeçalho
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Dia");
            headerRow.createCell(2).setCellValue("Turno");
            headerRow.createCell(3).setCellValue("Hora Início");
            headerRow.createCell(4).setCellValue("Hora Fim");

            // Preenche as linhas com os dados de cada horário
            int rowIdx = 1;
            for (Horario h : horarios) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(h.getId() != null ? h.getId().toString() : "");
                row.createCell(1).setCellValue(h.getDia() != null ? h.getDia().name() : "");
                row.createCell(2).setCellValue(h.getTurno() != null ? h.getTurno().name() : "");
                row.createCell(3).setCellValue(h.getHoraInicio() != null ? h.getHoraInicio().toString() : "");
                row.createCell(4).setCellValue(h.getHoraFim() != null ? h.getHoraFim().toString() : "");
            }

            workbook.write(out);
            byte[] arquivo = out.toByteArray();

            // Define os headers da resposta para download do arquivo
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Horarios_Relatorio.xlsx");
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(arquivo);
        }
    }
}