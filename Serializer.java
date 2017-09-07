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
		ObjectWriter writer = new ObjectWriter(bos);

		for(int i = 0; i < m.size(); i++)
		{
			writer.writeObject(m.get(i));
			writer.flush();
		}
		writer.close();

		byte[] byteArray = bos.toByteArray();
		bos.close();
		return byteArray;
	}

	/**
	 * Overloaded function of the above to serialize a single message.
	 */
	static <T> byte[]
	Serialize(T m)
			throws IOException
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectWriter writer = new ObjectWriter(bos);

		writer.writeObject(m);
		writer.flush();
		writer.close();

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
		try
		{
			ArrayList<T> objects = new ArrayList<>();
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			ObjectReader reader = new ObjectReader(bais);
			Object obj;
			while(true)
			{
				try
				{
					obj = reader.readObject();
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
					reader.close();
					bais.close();
					return objects;
				}
				catch (StreamCorruptedException s)
				{
					s.printStackTrace();
					reader.close();
					bais.close();
					return objects;
				}
			}

			reader.close();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
