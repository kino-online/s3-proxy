package art.aelaort.s3proxy;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
	private final RestTemplate http;
	private final AtomicReference<String> urlForRedirect = new AtomicReference<>();

	@PostMapping("set-redirect")
	public void setUrlForRedirect(@RequestParam String url) {
		urlForRedirect.set(url);
		log.debug("set redirect url - {}", url);
	}

	@CrossOrigin
	@GetMapping("redirect")
	public void redirect(HttpServletResponse response) {
		log.debug("redirect to {}", urlForRedirect);
		response.setHeader("location", urlForRedirect.get());
		response.setStatus(301);
	}

	@CrossOrigin
//	@GetMapping(value = "/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] downloadFromS3(HttpServletRequest request) {
		String s3Path = URI.create(request.getRequestURI()).getPath();
		return http.getForObject(s3Path, byte[].class);
	}
}
