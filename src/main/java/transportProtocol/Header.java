package transportProtocol;

public class Header {
	private String headerKey;
	private String headerValue;
	
	public Header(String headerKey, String headerValue) {
		this.headerKey = headerKey;
		this.headerValue = headerValue;
	}
	
	public String getKey() {
		return headerKey;
	}
	
	public String getVal() {
		return headerValue;
	}
	
	public String getHeaderSet() {
		if (headerKey == null) return headerValue;
		return headerKey + ":" + headerValue;
	}
}
