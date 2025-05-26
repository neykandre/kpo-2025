package hse.studuing.apigateway.controller;

import hse.studuing.apigateway.service.AnalysisProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
public class AnalysisGatewayController {

    private final AnalysisProxyService proxy;

    public AnalysisGatewayController(AnalysisProxyService p) {this.proxy = p;}

    @Operation(
            summary = "Запустить анализ .txt файла",
            responses = {
                    @ApiResponse(responseCode = "200", description = "JSON с результатами"),
                    @ApiResponse(responseCode = "503", description = "analysis-service offline")
            })
    @PostMapping("/{fileId}")
    public ResponseEntity<String> analyse(
            @PathVariable Long fileId,
            @RequestParam(defaultValue = "false") boolean cloud) {
        return proxy.runAnalysis(fileId, cloud);
    }

    @Operation(summary = "Получить результат анализа по id")
    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable Long id) {
        return proxy.getResult(id);
    }

    @GetMapping(path = "/{id}/svg", produces = "image/svg+xml")
    @Operation(
            summary = "Word-cloud как SVG",
            description = "Возвращает само SVG-изображение облака слов",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(
                            mediaType = "image/svg+xml")),
                    @ApiResponse(responseCode = "204", description = "SVG отсутствует"),
                    @ApiResponse(responseCode = "503", description = "analysis-service offline")
            }
    )
    public ResponseEntity<String> svg(@PathVariable long id) {
        return proxy.getSvg(id);
    }
}