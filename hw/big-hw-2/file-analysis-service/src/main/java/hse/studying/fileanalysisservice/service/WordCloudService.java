package hse.studying.fileanalysisservice.service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WordCloudService {
    private final RestTemplate rest;
    private final String endpoint;

    public WordCloudService(RestTemplate rest, @Value("${quickchart.endpoint}") String endpoint) {
        this.rest = rest;
        this.endpoint = endpoint;
    }

    /**
     * Возвращает SVG‑строку облака слов (формат text/plain).
     */
    public String generate(String text) {
        log.info("Generating word cloud");
        Map<String, Object> payload = Map.of(
                "format", "svg",
                "width", 600,
                "height", 600,
                "text", text
        );

        HttpHeaders hdr = new HttpHeaders();
        hdr.setContentType(MediaType.APPLICATION_JSON);
        hdr.setAccept(List.of(MediaType.ALL));

        try {
            ResponseEntity<byte[]> resp = rest.postForEntity(
                    endpoint,
                    new HttpEntity<>(payload, hdr),
                    byte[].class);

            log.info("QuickChart status = {}", resp.getStatusCode());

            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                return new String(resp.getBody(), StandardCharsets.UTF_8);
            }

            if (resp.getStatusCode().is3xxRedirection()) {
                String url = Objects.requireNonNull(resp.getHeaders().getLocation()).toString();
                log.info("Redirected SVG url = {}", url);
                return url;
            }
        } catch (Exception e) {
            log.warn(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
