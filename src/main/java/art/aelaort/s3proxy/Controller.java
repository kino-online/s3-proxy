package art.aelaort.s3proxy;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class Controller {
	@Value("${s3.url}")
	private String s3Url;
	private final RestTemplate http;

	@GetMapping
	public void redirectToS3(HttpServletResponse response, @RequestParam("s3") String s3FilePath) {
		String finalPath = s3Url + s3FilePath;
		response.setHeader("Location", finalPath);
		response.setHeader("x-minio-extract", "true");
		response.setStatus(301);
	}

	@GetMapping(value = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] downloadFromS3(@RequestParam("s3") String s3FilePath) {
		return http.getForObject("/" + s3FilePath, byte[].class);
	}
}
