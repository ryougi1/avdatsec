package project2;

import java.io.IOException;
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
	private int g, p, secret;

	public Server() throws SocketException, UnknownHostException {
		socket = new DatagramSocket(4455);
		address = InetAddress.getByName("localhost");
		p = 23;
		g = 5;
	}

	public void run() {
		try {
			running = true;

			while (running) {
				inPacket = new DatagramPacket(buf, buf.length);
				try {
					socket.receive(inPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				InetAddress address = inPacket.getAddress();
				int port = inPacket.getPort();

				String received = new String(inPacket.getData(), 0, inPacket.getLength());
				System.out.println("Server recieved:" + received);
				if (received.equals("end;")) {
					running = false;
					continue;
				} else if(received.contains("connect;")) {
					//Create response
					String[] DHres = DH.generateSecret(g, p);
					byte[] msg = (";"+DHres[0]).getBytes();
					System.out.println("Server sending:" + (";"+DHres[0]));
					outPacket = new DatagramPacket(msg, msg.length, address, port);
					socket.send(outPacket);
					
					//Compute secret
					secret = DH.computeSecret(received.substring(received.indexOf(';')+1), DHres[1], p);
					System.out.println("Server secret result:" + secret);
				} else {
					//Regular encrypted session
				}
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
