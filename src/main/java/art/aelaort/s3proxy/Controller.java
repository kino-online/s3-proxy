package art.aelaort.s3proxy;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	@Value("${s3.url}")
	private String s3Url;

	@GetMapping("/{s3-file-path}")
	public void getFile(HttpServletResponse response, @PathVariable("s3-file-path") String s3FilePath) {
		String finalPath = s3Url + s3FilePath;
		response.setHeader("Location", finalPath);
		response.setHeader("x-minio-extract", "true");
		response.setStatus(302);
	}
}
