package EvilNet;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectReader extends ObjectInputStream
{
	public ObjectReader(InputStream in) throws IOException
	{
		super(in);
	}

	@Override
	protected void readStreamHeader() throws IOException
	{

	}
}
