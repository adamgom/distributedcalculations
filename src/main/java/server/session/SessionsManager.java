package server.session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ConcurrentHashMap;

public class SessionsManager {
	private static SessionsManager instance;
	private ConcurrentHashMap<String, Session> sessions; 
	
	private SessionsManager() {
		sessions = new ConcurrentHashMap<>();
	}

	public static SessionsManager getInstance() {
		if (instance == null) {
			instance = new SessionsManager();
			return instance;
		} else {
			return instance;	
		}
	}

	public Session getSession(String sessionId) {
		if (sessionId == null || !isSession(sessionId)) {
			sessionId = md5(String.valueOf(System.currentTimeMillis()));
			Session session = new Session(sessionId);
			sessions.put(session.getSessionId(), session);
		}
		return sessions.get(sessionId);
	}
	
	public String getSessionID(String sessionId) {
		return getSession(sessionId).getSessionId();
	}
	
	public Session getSessionTime(String sessionId) {
		return sessions.get(sessionId);
	}

	public boolean isSession(String sessionId) {
		return sessions.containsKey(sessionId);
	}

	private String md5(String value) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(value.getBytes());
			for (byte b: hash) {
				sb.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void endSession(String sessionId) {
		sessions.remove(sessionId);
	}

	public ConcurrentHashMap<String, Session> getSessions() {
		return sessions;
	}
}	
