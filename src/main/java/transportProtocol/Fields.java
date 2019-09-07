package transportProtocol;

public class Fields {
	// teksty komunikacji
	public static final String getHistory = "getHistory";
	public static final String noServerInfo = "Client: No server connection";
	public static final String initRequest = "Awaiting for request / client's activity";
	public static final String initResponse = "Awaiting for response from server";

	// pola nag³ówka
	public static final String headerContentType = "Content-type";
	public static final String headerSessionId = "SessionId";
	public static final String headerComputePrev = "Compute-previous";
	public static final String headerComputePrevTrue = "True";
	public static final String headerComputePrevFalse = "False";
	public static final String headerOperator = "Operator";

	// pola nag³ówka - wartoœci
	public static final String headerContentTypeValue = "text/html";
	public static final String headerFirstLine1 = "GET / HTTP/1.1";
	public static final String headerFirstLine2 = "HTTP/1.1 OK 200";
	public static final String regexp01 = "[\\d]+\\.{0,1}[\\d]{0,}"; 
	public static final String regexp2 = "[1-4]{1}";
	
	// pola sesji
	public static final String sessionLastResult = "lastResult";
	public static final String host = "localhost";
	public static final int port = 8080;
	
}
