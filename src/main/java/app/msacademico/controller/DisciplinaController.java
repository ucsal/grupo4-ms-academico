package app.msacademico.controller;

import app.msacademico.dto.DisciplinaDTO;
import app.msacademico.entity.Disciplina;
import app.msacademico.service.DisciplinaService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Controller responsável por receber e responder as requisições HTTP da Disciplina
@RestController
@RequestMapping("api/disciplina")
@CrossOrigin("*") // Permite requisições de qualquer origem (necessário para o front-end e API Gateway)
public class DisciplinaController {

    // Injeção do serviço de Disciplina
    @Autowired
    private DisciplinaService disciplinaService;

    // Cria uma nova disciplina — recebe um DisciplinaDTO no corpo da requisição
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@RequestBody DisciplinaDTO dto) {
        String mensagem = this.disciplinaService.save(dto);
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", mensagem);
        return new ResponseEntity<>(resposta, HttpStatus.CREATED);
    }

    // Atualiza uma disciplina existente pelo ID passado na URL
    @PostMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> update(@RequestBody DisciplinaDTO dto, @PathVariable long id) {
        String mensagem = this.disciplinaService.update(dto, id);
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", mensagem);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    // Inativa uma disciplina pelo ID sem deletá-la do banco (soft delete)
    @PatchMapping("/inativar/{id}")
    public ResponseEntity<Map<String, String>> inativar(@PathVariable long id) {
        String mensagem = this.disciplinaService.inativar(id);
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", mensagem);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    // Reativa uma disciplina que estava inativa pelo ID
    @PatchMapping("/reativar/{id}")
    public ResponseEntity<Map<String, String>> reativar(@PathVariable long id) {
        String mensagem = this.disciplinaService.reativar(id);
        Map<String, String> resposta = new HashMap<>();
        resposta.put("mensagem", mensagem);
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    // Retorna a lista de disciplinas inativas
    @GetMapping("/inativos")
    public ResponseEntity<List<Disciplina>> findAllInativos() {
        return new ResponseEntity<>(this.disciplinaService.findAllInativos(), HttpStatus.OK);
    }

    // Retorna a lista de todas as disciplinas ativas
    @GetMapping("/findAll")
    public ResponseEntity<List<Disciplina>> findAll() {
        return new ResponseEntity<>(this.disciplinaService.findAll(), HttpStatus.OK);
    }

    // Retorna as disciplinas de uma escola específica pelo ID da escola
    @GetMapping("/findByEscola/{escolaId}")
    public ResponseEntity<List<Disciplina>> findByEscola(@PathVariable long escolaId) {
        return new ResponseEntity<>(disciplinaService.findByEscola(escolaId), HttpStatus.OK);
    }

    // Busca uma disciplina específica pelo ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Disciplina> findById(@PathVariable long id) {
        return new ResponseEntity<>(this.disciplinaService.findById(id), HttpStatus.OK);
    }

    // Exporta todas as disciplinas ativas em formato Excel (.xlsx)
    @GetMapping("/exportar")
    public ResponseEntity<byte[]> exportarDisciplinasExcel() throws IOException {
        List<Disciplina> disciplinas = disciplinaService.findAll();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Disciplinas");

            // Define a largura de cada coluna
            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 40 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 40 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            sheet.setColumnWidth(5, 15 * 256);
            sheet.setColumnWidth(6, 15 * 256);
            sheet.setColumnWidth(7, 15 * 256);

            // Cria a linha de cabeçalho
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nome");
            headerRow.createCell(2).setCellValue("Sigla");
            headerRow.createCell(3).setCellValue("Descrição");
            headerRow.createCell(4).setCellValue("ID Escola");
            headerRow.createCell(5).setCellValue("Carga horária(h)");
            headerRow.createCell(6).setCellValue("Data de cadastro");
            headerRow.createCell(7).setCellValue("Status ativo");

            // Preenche as linhas com os dados de cada disciplina
            int rowIdx = 1;
            for (Disciplina d : disciplinas) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(d.getId() != null ? d.getId().toString() : "");
                row.createCell(1).setCellValue(d.getNome() != null ? d.getNome() : "");
                row.createCell(2).setCellValue(d.getSigla() != null ? d.getSigla() : "");
                row.createCell(3).setCellValue(d.getDescricao() != null ? d.getDescricao() : "");
                row.createCell(4).setCellValue(d.getEscolaId() != null ? d.getEscolaId().toString() : "Sem Escola");
                row.createCell(5).setCellValue(d.getCargaHoraria() != null ? d.getCargaHoraria().toString() : "");
                row.createCell(6).setCellValue(d.getData() != null ? d.getData().toString() : "");
                row.createCell(7).setCellValue(d.isStatusAtivo());
            }

            workbook.write(out);
            byte[] arquivo = out.toByteArray();

            // Define os headers da resposta para download do arquivo
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Disciplinas_Relatorio.xlsx");
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(arquivo);
        }
    }
}