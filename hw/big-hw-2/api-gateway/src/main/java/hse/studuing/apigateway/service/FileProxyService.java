package hse.studuing.apigateway.service;

import hse.studuing.apigateway.config.AppProps;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * Делает REST‑тоннель к приватному file‑service.
 */
@Service
public class FileProxyService {
    private final RestTemplate rest = new RestTemplate();
    private final String base;

    public FileProxyService(AppProps props) {
        this.base = props.getFileServiceUrl();
    }

    /**
     * Загрузка .txt файла в file‑service.
     * Используем ByteArrayResource, чтобы RestTemplate корректно выставил filename.
     */
    public ResponseEntity<String> upload(MultipartFile file) {
        String url = base + "/files";
        try {
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override public String getFilename() { return file.getOriginalFilename(); }
            };
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", resource);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            HttpEntity<MultiValueMap<String, Object>> req = new HttpEntity<>(body, headers);

            return rest.postForEntity(url, req, String.class);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("File service unavailable");
        }
    }

    public ResponseEntity<Resource> download(Long id) {
        String url = base + "/files/" + id;
        try {
            ResponseEntity<Resource> resp = rest.getForEntity(url, Resource.class);
            return ResponseEntity.status(resp.getStatusCode())
                    .headers(resp.getHeaders())
                    .body(resp.getBody());
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    public ResponseEntity<String> meta(Long id) {
        String url = base + "/files/" + id + "/meta";
        try {
            return rest.getForEntity(url, String.class);
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("File service unavailable");
        }
    }

    public ResponseEntity<String> extendedMeta(Long id) {
        String url = base + "/files/" + id + "/extended";
        try {
            return rest.getForEntity(url, String.class);
        } catch (RestClientException ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("File service unavailable");
        }
    }
}