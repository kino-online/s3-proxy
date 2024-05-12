package art.aelaort.s3proxy;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class Controller {
	private final RestTemplate http;

	@CrossOrigin
	@GetMapping(value = "/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] downloadFromS3(HttpServletRequest request) {
		String s3Path = URI.create(request.getRequestURI()).getPath();
		return http.getForObject(s3Path, byte[].class);
	}
}
