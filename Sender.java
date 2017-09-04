package EvilNet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 * Sender class for Evil.
 */
class Sender
{
	/**
	 * Sends a UDP message.
	 */
	static <T> void
	SendDatagram(DatagramSocket socket, ArrayList<T> msg, InetAddress ip, int port) throws IOException
	{
		byte[] b = Serializer.Serialize(msg);
		socket.send(new DatagramPacket(b, b.length, ip, port));
	}

	/**
	 * Sends a Multicast message.
	 */
	static <T> void
	SendMulticast(MulticastSocket socket, ArrayList<T> msg, InetAddress group, int port) throws IOException
	{
		byte[] b = Serializer.Serialize(msg);
		socket.send(new DatagramPacket(b, b.length, group, port));
	}

	/**
	 * Sends a TCP message.
	 */
	static <T> void
	SendTCP(DataOutputStream stream, ArrayList<T> msg) throws IOException
	{
		byte[] b = Serializer.Serialize(msg);
		stream.write(b);
		stream.flush();
	}
}
