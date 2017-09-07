package EvilNet;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Receiver-class. This class and it's functions are Package-private because the Evil-class is the users way to interact
 * with this library.
 */
class Receiver
{
	/**
	 * Function to receive data sent over UDP.
	 *
	 * @param socket        The datagram-datagramSocket to receive data on.
	 *
	 * @param MAX_BUF_SIZE  The maximum size of the buffer for data.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @return              Returns an ArrayList of T-objects.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	static <T> ArrayList<T>
	receiveUDP(DatagramSocket socket, int MAX_BUF_SIZE)
			throws IOException
	{
		byte[] data = new byte[MAX_BUF_SIZE];
		DatagramPacket dp = new DatagramPacket(data, MAX_BUF_SIZE);
		socket.receive(dp);
		int len = dp.getLength();
		byte[] b = Truncate(dp.getData(), len);
		return Serializer.Deserialize(b);
	}

	/**
	 * Function to receive data sent over UDP.
	 *
	 * @param socket        The multicast-datagramSocket to receive data on.
	 *
	 * @param MAX_BUF_SIZE  The maximum size of the buffer for data.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @return              Returns an ArrayList of T-objects.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	static <T> ArrayList<T>
	receiveMulticast(MulticastSocket socket, int MAX_BUF_SIZE)
			throws IOException
	{
		byte[] t = new byte[MAX_BUF_SIZE];
		DatagramPacket dp = new DatagramPacket(t, MAX_BUF_SIZE);
		socket.receive(dp);
		int lengthOfData = dp.getLength();
		byte[] b = Truncate(t, lengthOfData);
		return Serializer.Deserialize(b);
	}


	/**
	 * Function to receive data sent over TCP.
	 *
	 * @param stream        The input stream to read data from.
	 *
	 * @param MAX_BUF_SIZE  The maximum size of the buffer for data.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @return              Returns an ArrayList of T-objects.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	static <T> ArrayList<T>
	receiveTCP(DataInputStream stream, int MAX_BUF_SIZE)
			throws IOException
	{
		byte[] data = new byte[MAX_BUF_SIZE];
		int len = stream.read(data);
		if(len == -1)
		{
			throw new SocketException();
		}
		byte[] b = Truncate(data, len);

		return Serializer.Deserialize(b);
	}

	/**
	 * Function which truncates a byte-array down to length so that the byte-array can be fully read.
	 *
	 * @param byteArray     A byte-array containing the data received.
	 *
	 * @param length        The length of the message received.
	 *
	 * @return              Returns a truncated byte-array containing only the relevant data.
	 */
	private static byte[]
	Truncate(byte[] byteArray, int length)
	{
		byte[] b = new byte[length];
		for(int i = 0; i < length; i++)
		{
			b[i] = byteArray[i];
		}
		return b;
	}
}
