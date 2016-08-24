package server;
import java.net.*;

/**
 * @author Ankur
 *
 */
public class UDPSocket {
	DatagramSocket socket =null;
	private int udpPort = 0;
	String data;
	/**
	 * @return
	 */
	public int getUdpPort() {
		return udpPort;
	}
	
	/**
	 * @param buf
	 * @param length
	 * @param address
	 * @param port
	 */
	public void sendResponse(byte[] buf, int length, InetAddress address, int port){
		try {
		socket = new DatagramSocket(udpPort);
		DatagramPacket reply = new DatagramPacket(buf,buf.length,address,port);
		socket.send(reply);
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		finally{
			socket.close();
		}
	}
	
	/**
	 * @param buf
	 * @param udpPort
	 * @return
	 */
	public String recieveRequest(byte buf[],int udpPort){
		try {
			socket = new DatagramSocket(udpPort);
			DatagramPacket recievedPacket = new DatagramPacket(buf,buf.length);
			socket.receive(recievedPacket);
			data = new String(recievedPacket.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			socket.close();
		}
		return data;
	}
}
