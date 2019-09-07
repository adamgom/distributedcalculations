package transportProtocol;

import java.io.BufferedReader;
import java.io.IOException;

public class Message {
	private Headres headres;
	private String body;
	
	public Message(boolean request) {
		this.headres = new Headres();
		if (request) {
			headres.setFirstLine(Fields.headerFirstLine1);
		} else {
			headres.setFirstLine(Fields.headerFirstLine2);
		}
	}

	public void processHeaders(BufferedReader reader) {
		String line = "";
		try {
			line = reader.readLine();
			while (true) {
				line = reader.readLine();
				if (line.equals("")) break;
				headres.addCompleteHeader(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void passHeaders(Headres headres) {
		String fLine = this.headres.getFirstLine();
		this.headres = headres;
		this.headres.setFirstLine(fLine);
	}

	public void processBody(BufferedReader reader) {
		try {
			this.body = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setBody(String body) {
		this.body = body + System.lineSeparator();
	}
	
	public String getBody() {
		return body;
	}

	public String getFullMessage() {
		StringBuilder message = new StringBuilder();
		message.append(headres.getCompleteHeadersSet());
		message.append(body);
		return message.toString();
	}

	public Headres getHeaders() {
		return headres;
	}
	
	public String getSessionID() {
		if (headres.getHeaderKeyPosition(Fields.headerSessionId) > 0) {
			return headres.getHeaderValueByKey(Fields.headerSessionId);
		}
		return null;
	}
}
