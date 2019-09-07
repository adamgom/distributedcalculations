package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import server.session.SessionsManager;

public class Server {

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		SessionsManager.getInstance();
		
		System.out.println("Server start");
		
		try (ServerSocket serverSocket = new ServerSocket(8080)){
			Executor executor = Executors.newFixedThreadPool(5);
			while(true) {
				Socket socket = serverSocket.accept();
				executor.execute(new Connection(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
