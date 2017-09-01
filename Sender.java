package EvilNet;

import EvilNet.Queues.Messages;
import EvilNet.Queues.MulticastQueue;
import EvilNet.Queues.TCPQueue;
import EvilNet.Queues.UDPQueue;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

class Sender
{
	private static TCPQueue tcpQueue = null;
	private static UDPQueue udpQueue = null;
	private static MulticastQueue multiQueue = null;

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*----------------------------------------------------- UDP ------------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to send over a DatagramSocket.
	 *
	 * Serializes the array of messages and places the byte-array into a DatagramPacket.
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
	sendDatagram(DatagramSocket socket, ArrayList<T> objects, InetAddress address, int port)
			throws IOException
	{
		if(udpQueue == null)
		{
			udpQueue = new UDPQueue(EvilNet.tickRate);
		}

		Messages.UDPMessage msg = new Messages.UDPMessage<>(socket, objects, address, port);
		udpQueue.AddToQueue(msg);
	}

	/**
	 * Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	sendDatagram(DatagramSocket socket, T object, InetAddress address, int port)
			throws IOException
	{
		if(udpQueue == null)
		{
			udpQueue = new UDPQueue(EvilNet.tickRate);
		}

		Messages.UDPMessage msg = new Messages.UDPMessage<>(socket, object, address, port);
		udpQueue.AddToQueue(msg);
	}

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*-------------------------------------------------- Multicast ---------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to send over a MulticastSocket.
	 *
	 * Serializes the array of messages and places the byte-array into a DatagramPacket.
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
	sendMulticast(MulticastSocket socket, ArrayList<T> objects, InetAddress group, int port)
			throws IOException
	{
		if(multiQueue == null)
		{
			multiQueue = new MulticastQueue(EvilNet.tickRate);
		}
		Messages.MultiMessage msg = new Messages.MultiMessage<>(socket, objects, group, port);
		multiQueue.AddToQueue(msg);
	}
	/**
	 * Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	sendMulticast(MulticastSocket socket, T object, InetAddress group, int port)
			throws IOException
	{
		if(multiQueue == null)
		{
			multiQueue = new MulticastQueue(EvilNet.tickRate);
		}
		Messages.MultiMessage msg = new Messages.MultiMessage<>(socket, object, group, port);
		multiQueue.AddToQueue(msg);
	}

	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/*----------------------------------------------------- TCP ------------------------------------------------------*/
	//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
	/**
	 * Function to send over a DataOutputStream.
	 *
	 * Serializes the array of messages and sends the byte-array over the DataOutputStream.
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
	sendTCP(DataOutputStream stream, ArrayList<T> objects)
			throws IOException
	{
		if(tcpQueue == null)
		{
			tcpQueue = new TCPQueue(EvilNet.tickRate);
		}
		Messages.TCPMessage msg = new Messages.TCPMessage<>(stream, objects);
		tcpQueue.AddToQueue(msg);
	}
	/**
	 *  Overloaded function of the above to add one message to the queue.
	 */
	static <T> void
	sendTCP(DataOutputStream stream, T object)
			throws IOException
	{
		if(tcpQueue == null)
		{
			tcpQueue = new TCPQueue(EvilNet.tickRate);
		}
		Messages.TCPMessage msg = new Messages.TCPMessage<>(stream, object);
		tcpQueue.AddToQueue(msg);
	}
}