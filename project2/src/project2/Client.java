package project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

public class Client extends Thread {
	private DatagramSocket socket;
	private InetAddress address;
	private int p, g, secret;
	private double a;
	private DatagramPacket inPacket, outPacket;

	public Client() throws SocketException, UnknownHostException {
		socket = new DatagramSocket();
		address = InetAddress.getByName("localhost");
		p = 23;
		g = 5;
	}

	public void run() {
		try {
			execHandshake();
			
			endConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	private void execHandshake() throws IOException {
		// Send hello
		// TODO append hash
		String[] DHres = DH.generateSecret(g, p);
		byte[] msg = ("connect;" + DHres[0]).getBytes();
		outPacket = new DatagramPacket(msg, msg.length);
		outPacket.setAddress(address);
		outPacket.setPort(4455);
		socket.send(outPacket);
		// Receive response
		inPacket = new DatagramPacket(new byte[1000], 1000);

		try {
			socket.receive(inPacket);
		} catch (SocketTimeoutException e) {
			System.out.println("Trying to connect...");
		}

		if (inPacket.getPort() != -1) {
			byte[] data = inPacket.getData();
			String received = new String(data, 0, data.length);
			System.out.println("Client received:" + received);
			secret = DH.computeSecret(received.substring(received.indexOf(';')+1), DHres[1], p);
			System.out.println("Client secret result:" + secret);
		}
	}


	private void endConnection() throws IOException {
		byte[] msg = ("end;".getBytes());
		DatagramPacket packet = new DatagramPacket(msg, msg.length);
		packet.setAddress(address);
		packet.setPort(4455);
		socket.send(packet);
		socket.close();
	}

}
