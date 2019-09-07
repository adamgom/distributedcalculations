package client;

import transportProtocol.Fields;
import transportProtocol.Message;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
	@FXML private Button send, getHistory;
	@FXML private TextArea request, response;
	@FXML private TextField num1, num2, operation;
	@FXML private CheckBox cBox;
	private ClientInstance client;
	private boolean[] sendOK;
	
	public Controller() {
		sendOK = new boolean[3];
	}
	
	public void initialize() {
		send.setDisable(true);
		cBox.setDisable(true);
		request.setText("Czekam na zapytanie");
		response.setText("Czekam na odpowiedz serwera");
		send.addEventHandler(ActionEvent.ACTION, e -> 		sendButtonAction());
		getHistory.addEventHandler(ActionEvent.ACTION, e -> getHistoryAction());
		cBox.selectedProperty().addListener((Observable, oldValue, newValue) -> {num2.setDisable(newValue); num2.clear(); send.setDisable(oldValue);} );
		num1.textProperty().addListener((Observable, oldVal, newVal) -> 		{checkNum(newVal, 0); send.setDisable(checkSend());} );
		num2.textProperty().addListener((Observable, oldVal, newVal) -> 		{checkNum(newVal, 1); send.setDisable(checkSend());} );
		operation.textProperty().addListener((Observable, oldVal, newVal) -> 	{checkNum(newVal, 2); send.setDisable(checkSend());} );
	}
	
	private void checkNum(String newVal, int position) {
		sendOK[position] = false;
		switch (position) {
		case 0:
			if (newVal.matches(Fields.regexp01)) sendOK[position] = true; 
			break;
		case 1:
			if (newVal.matches(Fields.regexp01) || cBox.isSelected()) sendOK[position] = true; 
			break;
		case 2:
			if (newVal.matches(Fields.regexp2)) sendOK[position] = true; 
			break;
		}
	}
	
	private boolean checkSend() {
		for (boolean b : sendOK) if (!b) return true;
		return false;
	}

	private void getHistoryAction() {
		client.runClient();
		client.setQuestion(Fields.getHistory);
		client.sendAndReceive();
	}
	
	private void sendButtonAction() {
		cBox.setDisable(false);
		client.runClient();
		if(cBox.isSelected()) {
			client.setQuestion(num1.getText());
		} else {
			client.setQuestion(num1.getText() + "," + num2.getText());
		}
		client.operationType(operation.getText());
		client.computeType(cBox.isSelected());
		client.sendAndReceive();
	}

	public void setRequest(Message request) {
		this.request.setText(request.getFullMessage());
	}

	public void setResponse(Message response) {
		this.response.setText(response.getFullMessage());
	}
	
	public void setResponse(String response) {
		this.response.setText(response);
	}
	
	public void setClient(ClientInstance client) {
		this.client = client;
	}
}
