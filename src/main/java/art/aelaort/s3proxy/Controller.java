package art.aelaort.s3proxy;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {
	@Value("${redirect.default.url}")
	private String url;
	private final RestTemplate http;
	private AtomicReference<String> urlForRedirect;

	@PostConstruct
	private void init() {
		urlForRedirect = new AtomicReference<>(url);
	}

	@GetMapping("ifconfig")
	public String ddns(HttpServletRequest request) {
		String header = request.getHeader("X-FORWARDED-FOR");
		log.info("request ifconfig, ip: {}", header);
		return header;
	}

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
		response.setStatus(303);
	}

	@CrossOrigin
//	@GetMapping(value = "/**", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public byte[] downloadFromS3(HttpServletRequest request) {
		String s3Path = URI.create(request.getRequestURI()).getPath();
		return http.getForObject(s3Path, byte[].class);
	}
}
