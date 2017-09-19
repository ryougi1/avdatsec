package project2;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client{
	private DatagramSocket socket;
	private InetAddress address;
	private BigInteger a, p, g;
	private DatagramPacket inPacket, outPacket;

	public Client() throws SocketException, UnknownHostException {
		socket = new DatagramSocket();
		address = InetAddress.getByName("localhost");
		g = new BigInteger("5");
		p = new BigInteger("23");
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
		String[] DHresult = DH.getRandomNumbers(g, p);
		System.out.println("Client randomizes c = " + DHresult[0] + " and calculates from this C = " + DHresult[1]);
		byte[] msg = ("connect;" + DHresult[1]).getBytes();
		outPacket = new DatagramPacket(msg, msg.length);
		outPacket.setAddress(address);
		outPacket.setPort(4455);
		socket.send(outPacket);
		System.out.println("#1 Client sent: " + DHresult[1] + " to server.");
		
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
			System.out.println("#4 Client receives: " + received);
			String secret = DH.computeSecret(received, DHresult[0], p);
			System.out.println("-----------------Client shared secret result = " + secret + "-----------------");
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
