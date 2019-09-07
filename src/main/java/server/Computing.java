package server;

import server.session.SessionsManager;
import transportProtocol.Fields;

public class Computing {
	private static Computing instance;
	private double num1;
	private double num2;
	
	private Computing() {
	}
	
	public static Computing getInstance() {
		if (instance == null) instance = new Computing();
		return instance;
	}

	public double compute(int operator, String body, String sessionId, boolean previous) {
		double result = 0.0;
		if (previous) {
			num1 = Double.parseDouble(body.trim());
			num2 = SessionsManager.getInstance().getSession(sessionId).get(Fields.sessionLastResult, Double.class);
		} else {
			num1 = Double.parseDouble(body.substring(0, body.indexOf(",")).trim());	
			num2 = Double.parseDouble(body.substring(body.indexOf(",") + 1).trim());
		}
		
		switch (operator) {
		case 1:
			result = num2 + num1;
			break;
		case 2:
			result = num2 - num1;
			break;
		case 3:
			result = num2 * num1;
			break;
		case 4:
			result = num2 / num1;
			break;
		}
		
		return result;
	}
}
