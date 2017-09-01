package EvilNet.Queues;



import java.io.IOException;
import java.util.ArrayList;

class MulticastQueue <T> extends SendQueue implements Runnable
{
	public MulticastQueue(int tickRate)
	{
		super(tickRate);

	}

	@Override
	public void run()
	{

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
