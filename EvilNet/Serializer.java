package EvilNet;

import java.io.*;
import java.util.ArrayList;

/**
 * Serializer-class. Is package-private because this should not be accessed outside the library.
 * Handles serialization and deserialization of messages.
 */
class Serializer
{
	/**
	 * Function to serialize an ArrayList of message-objects into a byte-array.
	 *
	 * @param m             This is the message to be serialized and is an ArrayList of T-objects.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @return              Returns the serialized byte-array.
	 *
	 * @throws IOException  Throws IOException if one is encountered.
	 */
	static <T> byte[]
	Serialize(ArrayList<T> m)
			throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bos);

		for(int i = 0; i < m.size();i++)
		{
			output.writeObject(m.get(i));
			output.flush();
		}
		output.close();

		byte[] byteArray = bos.toByteArray();
		bos.close();
		return byteArray;
	}
	static <T> byte[]
	Serialize(T m)
			throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(bos);

		output.writeObject(m);
		output.flush();
		output.close();

		byte[] byteArray = bos.toByteArray();
		bos.close();
		return byteArray;
	}

	/**
	 * Function to deserialize a byte-array into an ArrayList of message-objects.
	 *
	 * @param b             The byte-array to be deserialized.
	 *
	 * @param <T>           The generic type representing the message.
	 *
	 * @return              Returns an ArrayList containing T-objects.
	 *
	 * @throws IOException  Throws an IOException if one is encountered.
	 */
	static <T> ArrayList<T>
	Deserialize(byte[] b)
			throws IOException
	{
		ArrayList<T> objects = new ArrayList<>();
		ObjectInputStream iStream;
		try
		{
			iStream = new ObjectInputStream(new ByteArrayInputStream(b));
			Object obj;
			while(true)
			{
				try
				{
					obj = iStream.readObject();
					if(obj != null)
					{
						objects.add((T) obj);
					}
					else
					{
						break;
					}
				}
				catch (EOFException e)
				{
					iStream.close();
					return objects;
				}
			}

			iStream.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
