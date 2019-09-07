package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import transportProtocol.Fields;
import transportProtocol.Message;

public class ClientInstance {
	private Controller controller;
	Message question, answer;

	public ClientInstance(Controller controller) {
		this.controller = controller;
		question = new Message(true);
		answer = null;
	}
	
	public void runClient() {
		if (answer == null) {
			question.getHeaders().addHeader(Fields.headerContentType, Fields.headerContentTypeValue);
			question.getHeaders().addHeader(Fields.headerSessionId, "init");
		} else {
			question.passHeaders(answer.getHeaders());
		}
	}
	
	public void computeType(boolean computeType) {
		if (answer != null && computeType) {
			question.getHeaders().addHeader(Fields.headerComputePrev, Fields.headerComputePrevTrue);	
		} else {
			question.getHeaders().addHeader(Fields.headerComputePrev, Fields.headerComputePrevFalse);
		}
	}

	public void operationType(String operationType) {
		question.getHeaders().addHeader(Fields.headerOperator, operationType);
	}
	
	public void setQuestion(String nums) {
		question.setBody(nums);
	}
	
	public void sendAndReceive() {
		controller.setRequest(question);
		answer = sender(question);
		controller.setResponse(answer);
	}

	private Message sender(Message request) {
		try (	Socket socket = new Socket(Fields.host, Fields.port);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));){
			writer.write(request.getFullMessage());
			writer.flush();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Message response = new Message(false);
			response.processHeaders(reader);
			response.processBody(reader);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
			controller.setResponse(Fields.noServerInfo);
		}
		return null;
	}	
}
