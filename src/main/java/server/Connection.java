package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import transportProtocol.Fields;
import transportProtocol.Message;
import server.archive.CalculationArchive;
import server.session.SessionsManager;

public class Connection implements Runnable {
	Socket socket;
	Message question;
	Message answer;
	
	public Connection(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		question = new Message(true);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			question.processHeaders(reader);
			question.processBody(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}

		answer = new Message(false);
		answer.passHeaders(question.getHeaders());
		answer.getHeaders().addHeader(Fields.headerSessionId, SessionsManager.getInstance().getSessionID(answer.getSessionID()));

		if (question.getBody().equals(Fields.getHistory)) {
			answer.setBody(CalculationArchive.getInstance().getHistory().toString());
		} else {
			Double result = Computing.getInstance().compute(
					Integer.parseInt(question.getHeaders().getHeaderValueByKey(Fields.headerOperator)), 
					question.getBody(),
					answer.getSessionID(),
					Boolean.parseBoolean(question.getHeaders().getHeaderValueByKey(Fields.headerComputePrev)));
			
			SessionsManager.getInstance().getSession(answer.getHeaders().getHeaderValueByKey(Fields.headerSessionId))
				.add(Fields.sessionLastResult, result);

			CalculationArchive.getInstance().addOperation(
					result, 
					question.getHeaders().getHeaderValueByKey(Fields.headerOperator), 
					question.getHeaders().getHeaderValueByKey(Fields.headerComputePrev));
	
			answer.setBody(String.valueOf(result));
		}

		send(answer);
	}
	
	private void send(Message message) {
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		){
			writer.write(answer.getFullMessage());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
