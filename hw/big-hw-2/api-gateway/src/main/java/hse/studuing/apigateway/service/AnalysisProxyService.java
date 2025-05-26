package hse.studuing.apigateway.service;

import hse.studuing.apigateway.config.AppProps;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class AnalysisProxyService {

    private final RestTemplate rest = new RestTemplate();
    private final String base;

    public AnalysisProxyService(AppProps props) {
        this.base = props.getAnalysisServiceUrl();
    }

    /**
     * Запустить анализ файла
     */
    public ResponseEntity<String> runAnalysis(Long fileId, boolean cloud) {
        String url = base + "/analysis/" + fileId + "?cloud=" + cloud;
        try {
            return rest.postForEntity(url, null, String.class);
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Analysis-service unavailable");
        }
    }

    /**
     * Получить сохранённый результат
     */
    public ResponseEntity<String> getResult(Long id) {
        String url = base + "/analysis/" + id;
        try {
            return rest.getForEntity(url, String.class);
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Analysis-service unavailable");
        }
    }

    /**
     * Проксируем GET /analysis/{id}/svg → image/svg+xml
     */
    public ResponseEntity<String> getSvg(long id) {
        String url = base + "/analysis/" + id + "/svg";
        try {
            ResponseEntity<String> resp = rest.getForEntity(url, String.class);
            MediaType ct = resp.getHeaders().getContentType();
            return ResponseEntity.status(resp.getStatusCode())
                    .contentType(ct != null ? ct : MediaType.valueOf("image/svg+xml"))
                    .body(resp.getBody());
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Analysis-service unavailable");
        }
    }
}