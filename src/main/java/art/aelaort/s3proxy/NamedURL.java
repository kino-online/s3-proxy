package art.aelaort.s3proxy;

public record NamedURL(String url, String name) {
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof NamedURL namedURL)) {
			return false;
		}

		return name.equals(namedURL.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
