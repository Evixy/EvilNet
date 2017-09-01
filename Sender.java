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
		byte[] b = Serializer.Serialize(objects);
		DatagramPacket dp = new DatagramPacket(b, b.length, address, port);
		socket.send(dp);
	}

	/**
	 * Overloaded function of the above in order to send just 1 object.
	 */
	static <T> void
	sendDatagram(DatagramSocket socket, T object, InetAddress address, int port)
			throws IOException
	{
		byte[] b = Serializer.Serialize(object);
		DatagramPacket dp = new DatagramPacket(b, b.length, address, port);
		socket.send(dp);
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
		byte[] b = Serializer.Serialize(objects);
		DatagramPacket dp = new DatagramPacket(b, b.length, group, port);
		socket.send(dp);
	}
	/**
	 *
	 */
	static <T> void
	sendMulticast(MulticastSocket socket, T object, InetAddress group, int port)
			throws IOException
	{
		byte[] b = Serializer.Serialize(object);
		DatagramPacket dp = new DatagramPacket(b, b.length, group, port);
		socket.send(dp);
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
		byte[] b = Serializer.Serialize(objects);
		stream.write(b);
		stream.flush();
	}
	/**
	 *
	 */
	static <T> void
	sendTCP(DataOutputStream stream, T object)
			throws IOException
	{

		byte[] b = Serializer.Serialize(object);
		stream.write(b);
		stream.flush();
	}
}