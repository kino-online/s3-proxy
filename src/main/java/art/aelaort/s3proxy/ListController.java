package art.aelaort.s3proxy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/list")
	public Set<NamedURL> list() {
		return urls;
	}
}
