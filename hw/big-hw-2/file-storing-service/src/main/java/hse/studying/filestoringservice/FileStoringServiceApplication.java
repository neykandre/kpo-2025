package hse.studying.filestoringservice;

import hse.studying.filestoringservice.config.FileStoringProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStoringProperties.class)
public class FileStoringServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(FileStoringServiceApplication.class, args);
	}
}