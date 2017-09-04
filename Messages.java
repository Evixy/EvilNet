package EvilNet;

import java.io.DataOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 * Messages class for Evil to represent the messages in the send queues.
 */
class Messages
{
	/**
	 * The base type of messages. Need a base class to be able to use it to inherit from.
	 *
	 * @param <T>       The generic class representing actual messages to send.
	 */
	static class IPMessage <T>
	{
		ArrayList<T> msg;

		IPMessage(T msg)
		{
			this.msg = new ArrayList<>();
			this.msg.add(msg);
		}
		IPMessage(ArrayList<T> msg)
		{
			this.msg = msg;
		}
	}

	/**
	 * TCP messages. Inherits from IPMessage.
	 *
	 * @param <T>       The generic class representing actual messages to send.
	 */
	static class TCPMessage <T> extends IPMessage
	{
		DataOutputStream outputStream;
		TCPMessage(DataOutputStream outputStream, T msg)
		{
			super(msg);
			this.outputStream = outputStream;
		}
		TCPMessage(DataOutputStream outputStream, ArrayList<T> msg)
		{
			super(msg);
			this.outputStream = outputStream;
		}
	}

	/**
	 * UDP Message. Inherits from IPMessage.
	 *
	 * @param <T>       The generic class representing actual messages to send.
	 */
	static class UDPMessage <T> extends IPMessage
	{
		DatagramSocket socket;
		InetAddress IP;
		int PORT;

		UDPMessage(DatagramSocket socket, T msg, InetAddress IP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.IP = IP;
			this.PORT = PORT;
		}

		UDPMessage(DatagramSocket socket, ArrayList<T> msg, InetAddress IP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.IP = IP;
			this.PORT = PORT;
		}
	}

	/**
	 * Multicast Message. Inherits from IPMessage
	 *
	 * @param <T>       The generic class representing actual messages to send.
	 */
	public static class MultiMessage <T> extends IPMessage
	{
		MulticastSocket socket;
		InetAddress GROUP;
		int PORT;

		MultiMessage(MulticastSocket socket, T msg, InetAddress GROUP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.GROUP = GROUP;
			this.PORT = PORT;
		}

		MultiMessage(MulticastSocket socket, ArrayList<T> msg, InetAddress GROUP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.GROUP = GROUP;
			this.PORT = PORT;
		}
	}
}
