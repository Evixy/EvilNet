package EvilNet.Queues;


import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

public class UDPQueue <T> extends SendQueue
{
	public Map<InetAddress, Messages.UDPMessage> messages;

	public UDPQueue(int tickRate)
	{
		super(tickRate);

	}

	@Override
	public void AddToQueue(ArrayList<Messages.IPMessage> msg)
	{
		for(int i = 0; i < msg.size(); i++)
		{
			Messages.UDPMessage m = (Messages.UDPMessage) msg.get(i);
			Messages.UDPMessage ms = this.messages.get(m.IP);
			if(ms != null)
			{
				ms.msg.addAll(m.msg);
			}
			else
			{
				this.messages.put(m.IP, m);
			}
		}
	}

	@Override
	public void SendMessages() throws IOException
	{

	}
}