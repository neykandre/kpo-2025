package hse.studuing.apigateway.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProps {
    @NotBlank private String fileServiceUrl;
    @NotBlank private String analysisServiceUrl;

    public String getFileServiceUrl() {return fileServiceUrl;}

    public void setFileServiceUrl(String url) {this.fileServiceUrl = url;}

    public String getAnalysisServiceUrl() {return analysisServiceUrl;}

    public void setAnalysisServiceUrl(String url) {this.analysisServiceUrl = url;}
}