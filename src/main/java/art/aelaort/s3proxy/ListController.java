package art.aelaort.s3proxy;

import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ListController {
	private final Set<NamedURL> urls = ConcurrentHashMap.newKeySet();

	@PostMapping("/add")
	public void add(@RequestParam String url, @RequestParam String name) {
		NamedURL namedURL = new NamedURL(url, name);
		if (!urls.add(namedURL)) {
			urls.remove(namedURL);
			urls.add(namedURL);
		}
	}

	@CrossOrigin("${cors.origin}")
	@GetMapping("/list")
	public Set<NamedURL> list() {
		return urls;
	}
}
