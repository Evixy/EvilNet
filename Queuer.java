package EvilNet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

class Queuer
{
	private static TCPQueue tcpQueue = null;
	private static UDPQueue udpQueue = null;
	private static MulticastQueue multiQueue = null;

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*----------------------------------------------------- UDP ------------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to queue messages to be sent over a DatagramSocket.
	 *
	 * Adds messages to a queue. If no queue currently exists, one is created.
	 *
	 * @param socket        The DatagramSocket we send the data over.
	 *
	 * @param objects       The message to serialize and send.
	 *
	 * @param address       The InetAddress we want to send the data to.
	 *
	 * @param port          The port number the receiver is listening to.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @throws IOException  Throws an IOException if one is triggered.
	 */
	static <T> void
	QueueDatagram(DatagramSocket socket, ArrayList<T> objects, InetAddress address, int port)
			throws IOException
	{
		if(udpQueue == null)
		{
			udpQueue = new UDPQueue(EvilNet.tickRate);
			udpQueue.start();
		}

		Messages.UDPMessage msg = new Messages.UDPMessage<>(socket, objects, address, port);
		udpQueue.AddToQueue(msg);
	}

	/**
	 * Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	QueueDatagram(DatagramSocket socket, T object, InetAddress address, int port)
			throws IOException
	{
		if(udpQueue == null)
		{
			udpQueue = new UDPQueue(EvilNet.tickRate);
			udpQueue.start();
		}

		Messages.UDPMessage msg = new Messages.UDPMessage<>(socket, object, address, port);
		udpQueue.AddToQueue(msg);
	}

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*-------------------------------------------------- Multicast ---------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to queue messages to be sent over a MulticastSocket.
	 *
	 * Adds messages to a queue. If no queue currently exists, one is created.
	 *
	 * @param socket        The DatagramSocket we send the data over.
	 *
	 * @param objects       The message to serialize and send.
	 *
	 * @param group         The InetAddress we want to send the data to.
	 *
	 * @param port          The port number the receiver is listening to.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @throws IOException  Throws an IOException if one is triggered.
	 */
	static <T> void
	QueueMulticast(MulticastSocket socket, ArrayList<T> objects, InetAddress group, int port)
			throws IOException
	{
		if(multiQueue == null)
		{
			multiQueue = new MulticastQueue(EvilNet.tickRate);
			multiQueue.start();
		}
		Messages.MultiMessage msg = new Messages.MultiMessage<>(socket, objects, group, port);
		multiQueue.AddToQueue(msg);
	}
	/**
	 * Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	QueueMulticast(MulticastSocket socket, T object, InetAddress group, int port)
			throws IOException
	{
		if(multiQueue == null)
		{
			multiQueue = new MulticastQueue(EvilNet.tickRate);
			multiQueue.start();
		}
		Messages.MultiMessage msg = new Messages.MultiMessage<>(socket, object, group, port);
		multiQueue.AddToQueue(msg);
	}

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*----------------------------------------------------- TCP ------------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to queue messages to be sent over a DataOutputStream.
	 *
	 * Adds messages to a queue. If no queue currently exists, one is created.
	 *
	 * @param stream        The DataOutputStream we send the data over.
	 *
	 * @param objects       The message to serialize and send.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @throws IOException  Throws an IOException if one is triggered.
	 */
	static <T> void
	QueueTCP(DataOutputStream stream, ArrayList<T> objects)
			throws IOException
	{
		if(tcpQueue == null)
		{
			tcpQueue = new TCPQueue(EvilNet.tickRate);
			tcpQueue.start();
		}
		Messages.TCPMessage msg = new Messages.TCPMessage<>(stream, objects);
		tcpQueue.AddToQueue(msg);
	}
	/**
	 *  Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	QueueTCP(DataOutputStream stream, T object)
			throws IOException
	{
		if(tcpQueue == null)
		{
			tcpQueue = new TCPQueue(EvilNet.tickRate);
			tcpQueue.start();
		}
		Messages.TCPMessage msg = new Messages.TCPMessage<>(stream, object);
		tcpQueue.AddToQueue(msg);
	}
}