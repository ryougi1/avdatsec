package project2;

import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Server extends Thread {
	private DatagramSocket socket;
	private InetAddress address;
	private boolean running;
	private byte[] buf = new byte[256];
	private DatagramPacket inPacket, outPacket;
	private BigInteger b, p, g;

	public Server() throws SocketException, UnknownHostException {
		socket = new DatagramSocket(4455);
		address = InetAddress.getByName("localhost");
		g = new BigInteger("5");
		p = new BigInteger("23");
	}

	public void run() {
		try {
			running = true;
			while (running) {
				//Receive packets
				inPacket = new DatagramPacket(buf, buf.length);
				socket.receive(inPacket);
				InetAddress address = inPacket.getAddress();
				int port = inPacket.getPort();
				String received = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println("#2 Server recieved: " + received);
				
				//Check message type
				if (received.equals("end;")) {
					running = false;
					break;
				} else if (received.contains("connect;")) {
					// Calculate DH shared secret and respond to client
					String[] DHresult = DH.getRandomNumbers(g, p);
					System.out.println("Server randomizes s = " + DHresult[0] + " and calculates from this S = " + DHresult[1]);
					byte[] msg = (DHresult[1]).getBytes();
					System.out.println("#3 Server sent: " + DHresult[1] + " to client.");
					outPacket = new DatagramPacket(msg, msg.length, address, port);
					socket.send(outPacket);
					String secret = DH.computeSecret(received.substring(received.indexOf(';') + 1), DHresult[0], p);
					System.out.println("-----------------Server shared secret result = " + secret + "-----------------");
				} else {
					// Regular encrypted session
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
