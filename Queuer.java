package EvilNet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

class Queuer
{
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
		UDPQueue queue = Evil.udpQueueMap.get(address);
		if(queue == null)
		{
			queue = new UDPQueue(Evil.tickRate);
			queue.start();
			Evil.udpQueueMap.put(address, queue);
		}
		queue.AddToQueue(new Messages.UDPMessage<>(socket, objects, address, port));
	}

	/**
	 * Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	QueueDatagram(DatagramSocket socket, T object, InetAddress address, int port)
			throws IOException
	{
		UDPQueue queue = Evil.udpQueueMap.get(address);
		if(queue == null)
		{
			queue = new UDPQueue(Evil.tickRate);
			queue.start();
			Evil.udpQueueMap.put(address, queue);
		}
		queue.AddToQueue(new Messages.UDPMessage<>(socket, object, address, port));
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
		MulticastQueue queue = Evil.multiQueueMap.get(group);
		if(queue == null)
		{
			queue = new MulticastQueue(Evil.tickRate);
			queue.start();
			Evil.multiQueueMap.put(group, queue);
		}
		queue.AddToQueue(new Messages.MultiMessage<>(socket, objects, group, port));
	}

	/**
	 * Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	QueueMulticast(MulticastSocket socket, T object, InetAddress group, int port)
			throws IOException
	{
		MulticastQueue queue = Evil.multiQueueMap.get(group);
		if(queue == null)
		{
			queue = new MulticastQueue(Evil.tickRate);
			queue.start();
			Evil.multiQueueMap.put(group, queue);
		}
		queue.AddToQueue(new Messages.MultiMessage<>(socket, object, group, port));
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
		TCPQueue queue = Evil.tcpQueueMap.get(stream);
		if(queue == null)
		{
			queue = new TCPQueue(Evil.tickRate);
			queue.start();
			Evil.tcpQueueMap.put(stream, queue);
		}
		queue.AddToQueue(new Messages.TCPMessage<>(stream, objects));
	}

	/**
	 *  Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	QueueTCP(DataOutputStream stream, T object)
			throws IOException
	{
		TCPQueue queue = Evil.tcpQueueMap.get(stream);
		if(queue == null)
		{
			queue = new TCPQueue(Evil.tickRate);
			queue.start();
			Evil.tcpQueueMap.put(stream, queue);
		}
		queue.AddToQueue(new Messages.TCPMessage<>(stream, object));
	}
}