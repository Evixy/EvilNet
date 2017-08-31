package EvilNet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

/**
 * Welcome to the EvilNet engine.
 *
 * The EvilNet engine is a simple network engine for Java. The engine allows a user to send messages over a network connection
 * by simply serializing objects representing messages, send them over the connection and then deserialize them on the
 * receiving end.
 *
 * The engine handles everything between sending objects over a connection and receiving them on the other
 * end as a set of objects with the same data as the ones that were sent. This means that a user will have to create a
 * protocol, setup a connection and create sockets and streams by themselves, but to send and receive data is just
 * question of performing a single function-call.
 *
 * The engine can:
 * Send and receive objects over a DataInputStream from a standard Socket.
 * Send and receive objects over a DatagramSocket.
 * Send and receive objects over a MulticastSocket.
 */

/**
 * EvilNet class.
 *
 * This is the entry point for the user of this library.
 * All the user has to do is to create a Socket and create a DataOutputStream/DataInputStream from the
 * Socket object, then create a DatagramSocket and/or a MulticastSocket to send over UDP.
 *
 * After that, a user just has to call either of the Send functions depending on which protocol is to be used
 * and then call the corresponding Receive function on the other end.
 *
 * The library uses generic types to prevent the need for casting each message to Object prior to sending it.
 */

public class EvilNet
{
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*----------------------------------------------------- TCP ------------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Send messages over the TCP protocol.
	 *
	 * @param outputStream  This is the output stream used to transmit the data. It needs to be a DataOutputStream.
	 *
	 * @param messages      This is a container for the messages to be serialized and sent over the DataOutputStream.
	 *                      It uses a generic type to allow a user to send any type of message they want, the message
	 *                      just needs to implement the Serializable-interface.
	 *
	 * @param <T>           The generic type representing the message. This can be anything as long as it implements the
	 *                      Serializable-interface.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	public static <T> void
	SendTCP(DataOutputStream outputStream, ArrayList<T> messages)
			throws IOException
	{
		Sender.sendTCP(outputStream, messages);
	}
	/**
	 * Overloaded function of the above to allow a user to send a single message without creating a container for it.
	 */
	public static <T> void
	SendTCP(DataOutputStream outputStream, T message)
			throws IOException
	{

		Sender.sendTCP(outputStream, message);
	}

	/**
	 * Receive messages sent over the TCP-protocol.
	 *
	 * @param inputStream   This is the input stream to receive messages. It needs to be a DataInputStream.
	 *
	 * @param MAX_BUF_SIZE  This is the maximum size of the buffer that should be used to receive data.
	 *
	 * @param <T>           The generic type representing the message. This can be anything as long as it implements the
	 *                      Serializable-interface.
	 *
	 * @return              Returns de-serialized messages in an ArrayList of type 'T'.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	public static <T> ArrayList<T>
	ReceiveTCP(DataInputStream inputStream, int MAX_BUF_SIZE)
			throws IOException
	{
		return Receiver.receiveTCP(inputStream, MAX_BUF_SIZE);
	}


	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*----------------------------------------------------- UDP ------------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Send messages over the UDP-protocol.
	 *
	 * @param socket        This is the DatagramSocket used to transmit the data.
	 *
	 * @param messages      This is a container for the messages to be serialized and sent over the DatagramSocket.
	 *                      It uses a generic type to allow a user to send any type of message they want, the message
	 *                      just needs to implement the Serializable-interface.
	 *
	 * @param IP            This is the IP-address that the message will be sent to. It can be either IPv4 or IPv6.
	 *
	 * @param PORT          This is the port-number the message will be directed to. Make sure that the receiving end is
	 *                      listening to the same port.
	 *
	 * @param <T>           The generic type representing the message. This can be anything as long as it implements the
	 *                      Serializable-interface.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	public static <T> void
	SendUDP(DatagramSocket socket, ArrayList<T> messages, InetAddress IP, int PORT)
			throws IOException
	{
		Sender.sendDatagram(socket, messages, IP, PORT);
	}
	/**
	 * Overloaded function of the above to allow a user to send a single message without creating a container for it.
	 */
	public static <T> void
	SendUDP(DatagramSocket socket, T message, InetAddress IP, int PORT)
			throws IOException
	{

		Sender.sendDatagram(socket, message, IP, PORT);
	}

	/**
	 * Receive messages sent over the UDP-protocol.
	 *
	 * @param socket        This is the DatagramSocket that is used to receive the data.
	 *
	 * @param MAX_BUF_SIZE  This is the maximum size of the buffer that should be used to receive data.
	 *
	 * @param <T>           The generic type representing the message. This can be anything as long as it implements the
	 *                      Serializable-interface.
	 *
	 * @return              Returns de-serialized messages in an ArrayList of type 'T'.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	public static <T> ArrayList<T>
	ReceiveUDP(DatagramSocket socket, int MAX_BUF_SIZE)
			throws IOException
	{
		return Receiver.receiveUDP(socket, MAX_BUF_SIZE);
	}


	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*-------------------------------------------------- Multicast ---------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to send data over a multicast socket.
	 *
	 * @param socket        The multicast socket to send data over.
	 *
	 * @param messages      This is a container for the messages to be serialized and sent over the DatagramSocket.
	 *                      It uses a generic type to allow a user to send any type of message they want, the message
	 *                      just needs to implement the Serializable-interface.
	 *
	 * @param IP            This is the IP-address that the message will be sent to. It can be either IPv4 or IPv6.
	 *
	 * @param PORT          This is the port-number the message will be directed to. Make sure that the receiving end is
	 *                      listening to the same port.
	 *
	 * @param <T>           The generic type representing the message. This can be anything as long as it implements the
	 *                      Serializable-interface.
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	public static <T> void
	SendMulticast(MulticastSocket socket, ArrayList<T> messages, InetAddress IP, int PORT)
			throws IOException
	{
		Sender.sendMulticast(socket, messages, IP, PORT);
	}
	/**
	 * Overloaded function of the above to allow a user to send a single message without creating a container for it.
	 */
	public static <T> void
	SendMulticast(MulticastSocket socket, T message, InetAddress IP, int PORT)
			throws IOException
	{
		Sender.sendMulticast(socket, message, IP, PORT);
	}

	/**
	 * Receive messages sent over a multicast socket.
	 *
	 * @param socket        This is the MulticastSocket that is used to receive the data.
	 *
	 * @param MAX_BUF_SIZE  This is the maximum size of the buffer that should be used to receive data.
	 *
	 * @param <T>           The generic type representing the message. This can be anything as long as it implements the
	 *                      Serializable-interface.
	 *
	 * @return              Returns deserialized messages in an ArrayList of type 'T'.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	public static <T> ArrayList<T>
	ReceiveMulti(MulticastSocket socket, int MAX_BUF_SIZE)
			throws IOException
	{
		return Receiver.receiveMulticast(socket, MAX_BUF_SIZE);
	}

}
