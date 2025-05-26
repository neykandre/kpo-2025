package hse.studuing.apigateway.controller;

import hse.studuing.apigateway.service.FileProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileGatewayController {
    private final FileProxyService proxy;
    public FileGatewayController(FileProxyService p) { this.proxy = p; }

    @Operation(
            summary = "Upload .txt report",
            description = "Принимает отчёт студента и пересылает во внутренний File‑Storing‑Service.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Файл сохранён, возвращён JSON‑meta"),
                    @ApiResponse(responseCode = "503", description = "File‑service недоступен")
            })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file){
        return proxy.upload(file);
    }

    @Operation(
            summary = "Download report by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "TXT файл",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "404", description = "Не найдено"),
                    @ApiResponse(responseCode = "503", description = "File‑service недоступен")
            })
    @GetMapping(value="/{id}")
    public ResponseEntity<Resource> download(
            @Parameter(description="ID файла", example="1")
            @PathVariable Long id){
        return proxy.download(id);
    }

    @Operation(summary = "File metadata by id")
    @GetMapping("/{id}/meta")
    public ResponseEntity<String> meta(@PathVariable Long id){
        return proxy.meta(id);
    }

    @Operation(summary = "File extended metadata by id")
    @GetMapping("/{id}/extended")
    public ResponseEntity<String> extended(@PathVariable Long id){
        return proxy.extendedMeta(id);
    }
}