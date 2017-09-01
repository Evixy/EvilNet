package EvilNet.Queues;



import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

public class MulticastQueue <T> extends SendQueue implements Runnable
{
	Map<InetAddress, Messages.MultiMessage> messages;
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
		for(int i = 0; i < msg.size(); i++)
		{
			Messages.MultiMessage m = (Messages.MultiMessage) msg.get(i);
			Messages.MultiMessage ms = this.messages.get(m.GROUP);
			if(ms != null)
			{
				ms.msg.addAll(m.msg);
			}
			else
			{
				this.messages.put(m.GROUP, m);
			}
		}
	}

	@Override
	public void SendMessages() throws IOException
	{

	}
}
