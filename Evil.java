package EvilNet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.HashMap;

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

public class Evil
{
	public static int tickRate;
	private static boolean evilNetInitialized = false;
	private static String alreadyInit = "EvilNet has already been initialized.";
	private static String notAlreadyInit = "EvilNet has not been initialized yet. Call InitializeEvilNet before you try to use the functions.";

	static HashMap<DataOutputStream, TCPQueue> tcpQueueMap;
	static HashMap<InetAddress, UDPQueue> udpQueueMap;
	static HashMap<InetAddress, MulticastQueue> multiQueueMap;
	/**
	 * Static function to initialize EvilNet.
	 *
	 * In order to initialize EvilNet, a tick rate needs to be specified. Tick rate means the amount of times per second
	 * messages are sent over the network. A high tick rate can easily clog up the network while a low tick rate can
	 * make an application appear laggy.
	 *
	 * @param tick          The amount of times per second messages are sent over the network.
	 *                      Preferably keep this below 60.
	 */
	public static void
	InitializeEvilNet(int tick)
	{
		e_assert(!evilNetInitialized, alreadyInit);

		tcpQueueMap = new HashMap<>();
		udpQueueMap = new HashMap<>();
		multiQueueMap = new HashMap<>();

		evilNetInitialized = true;
		tickRate = tick;
	}

	/**
	 * Function to update the tick rate of the engine. You should probably not do this but it is supported if you so desire
	 *
	 * @param tick          The new tick rate to be set to EvilNet.
	 */
	public static void
	SetTickRate(int tick)
	{
		tickRate = tick;

		for(TCPQueue queue : tcpQueueMap.values())
		{
			queue.SetTickRate(tick);
		}
		for(UDPQueue queue : udpQueueMap.values())
		{
			queue.SetTickRate(tick);
		}
		for(MulticastQueue queue : multiQueueMap.values())
		{
			queue.SetTickRate(tick);
		}
	}

	/**
	 * Assertion function for EvilNet to throw an assertion error with a string message if the statement is false.
	 *
	 * @param val           Boolean statement to check.
	 *
	 * @param str           The message to be posted if the statement is false.
	 */
	static void
	e_assert(boolean val, String str)
	{
		if(!val)
		{
			throw new AssertionError(str);
		}
	}
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
	 */
	public static <T> void
	SendTCP(DataOutputStream outputStream, ArrayList<T> messages)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			Queuer.QueueTCP(outputStream, messages);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Overloaded function of the above to allow a user to send a single message without creating a container for it.
	 */
	public static <T> void
	SendTCP(DataOutputStream outputStream, T message)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			Queuer.QueueTCP(outputStream, message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
	 */
	public static <T> ArrayList<T>
	ReceiveTCP(DataInputStream inputStream, int MAX_BUF_SIZE)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			return Receiver.receiveTCP(inputStream, MAX_BUF_SIZE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
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
	 */
	public static <T> void
	SendUDP(DatagramSocket socket, ArrayList<T> messages, InetAddress IP, int PORT)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			Queuer.QueueDatagram(socket, messages, IP, PORT);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Overloaded function of the above to allow a user to send a single message without creating a container for it.
	 */
	public static <T> void
	SendUDP(DatagramSocket socket, T message, InetAddress IP, int PORT)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			Queuer.QueueDatagram(socket, message, IP, PORT);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
	 */
	public static <T> ArrayList<T>
	ReceiveUDP(DatagramSocket socket, int MAX_BUF_SIZE)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			return Receiver.receiveUDP(socket, MAX_BUF_SIZE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
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
	 */
	public static <T> void
	SendMulticast(MulticastSocket socket, ArrayList<T> messages, InetAddress IP, int PORT)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			Queuer.QueueMulticast(socket, messages, IP, PORT);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Overloaded function of the above to allow a user to send a single message without creating a container for it.
	 */
	public static <T> void
	SendMulticast(MulticastSocket socket, T message, InetAddress IP, int PORT)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			Queuer.QueueMulticast(socket, message, IP, PORT);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
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
	 */
	public static <T> ArrayList<T>
	ReceiveMulti(MulticastSocket socket, int MAX_BUF_SIZE)
	{
		e_assert(evilNetInitialized, notAlreadyInit);
		try
		{
			return Receiver.receiveMulticast(socket, MAX_BUF_SIZE);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
