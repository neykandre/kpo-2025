package hse.studying.fileanalysisservice.controller;

import hse.studying.fileanalysisservice.dto.AnalysisResponseDto;
import hse.studying.fileanalysisservice.service.TextAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
    private final TextAnalysisService svc;

    public AnalysisController(TextAnalysisService s) {this.svc = s;}

    @Operation(summary = "Запустить анализ файла")
    @PostMapping("/{fileId}")
    public ResponseEntity<AnalysisResponseDto> analyse(
            @PathVariable Long fileId,
            @RequestParam(defaultValue = "false") boolean cloud) {
        return ResponseEntity.ok(svc.analyse(fileId, cloud));
    }

    @Operation(summary = "Получить результат анализа")
    @GetMapping("/{id}")
    public ResponseEntity<AnalysisResponseDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(svc.get(id));
    }

    @Operation(summary = "Word-cloud как SVG",
            description = "Возвращает изображение облака слов",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(
                            mediaType = "image/svg+xml",
                            schema = @Schema(type = "string", format = "binary"))),
                    @ApiResponse(responseCode = "204", description = "SVG отсутствует")
            })
    @GetMapping(path = "/{id}/svg", produces = "image/svg+xml")
    public ResponseEntity<String> svg(@PathVariable Long id) {
        var r = svc.getEntity(id);
        return r.getWordCloudSvg() == null
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(r.getWordCloudSvg());
    }
}