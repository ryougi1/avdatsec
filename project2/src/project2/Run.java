package project2;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Run {

	public static void main(String[] args) {
		
		try {
			Server server = new Server();
			server.start();
			Client client = new Client();
			client.start();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
