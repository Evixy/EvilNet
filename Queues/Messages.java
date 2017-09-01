package EvilNet.Queues;

import java.io.DataOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class Messages
{
	public static class IPMessage <T>
	{
		ArrayList<T> msg;

		public IPMessage(T msg)
		{
			this.msg = new ArrayList<>();
			this.msg.add(msg);
		}
		public IPMessage(ArrayList<T> msg)
		{
			this.msg = msg;
		}
	}

	public static class TCPMessage <T> extends IPMessage
	{
		public DataOutputStream outputStream;
		public TCPMessage(DataOutputStream outputStream, T msg)
		{
			super(msg);
			this.outputStream = outputStream;
		}
	}

	public static class UDPMessage <T> extends IPMessage
	{
		public DatagramSocket socket;
		public InetAddress IP;
		public int PORT;

		public UDPMessage(DatagramSocket socket, T msg, InetAddress IP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.IP = IP;
			this.PORT = PORT;
		}

		public UDPMessage(DatagramSocket socket, ArrayList<T> msg, InetAddress IP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.IP = IP;
			this.PORT = PORT;
		}
	}

	public static class MultiMessage <T> extends IPMessage
	{
		public MulticastSocket socket;
		public InetAddress GROUP;
		public int PORT;

		public MultiMessage(MulticastSocket socket, T msg, InetAddress GROUP, int PORT)
		{
			super(msg);
			this.socket = socket;
			this.GROUP = GROUP;
			this.PORT = PORT;
		}
	}
}
