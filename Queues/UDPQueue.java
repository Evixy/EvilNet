package EvilNet.Queues;


import java.io.IOException;
import java.util.ArrayList;

public class UDPQueue <T> extends SendQueue
{
	public UDPQueue(int tickRate)
	{
		super(tickRate);

	}

	@Override
	public void AddToQueue(ArrayList<Messages.IPMessage> msg)
	{

	}

	@Override
	public void AddToQueue(Messages.IPMessage msg)
	{

	}
	@Override
	public void SendMessages() throws IOException
	{

	}
}