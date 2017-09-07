package EvilNet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectWriter extends ObjectOutputStream
{
	public ObjectWriter(OutputStream out) throws IOException
	{
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException
	{
		reset();
	}
}
