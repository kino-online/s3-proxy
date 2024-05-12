package art.aelaort.s3proxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, @Value("${s3.url}") String s3Url) {
		return restTemplateBuilder
				.rootUri(s3Url)
				.defaultHeader("x-minio-extract", "true")
				.build();
	}
}
