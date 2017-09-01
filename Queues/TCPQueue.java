package EvilNet.Queues;



import Shared.Serializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class TCPQueue <T> extends SendQueue
{
	Map<DataOutputStream, Messages.TCPMessage> messages;

	public TCPQueue(int tickRate)
	{
		super(tickRate);

	}


	@Override
	public void AddToQueue(ArrayList<Messages.IPMessage> msg)
	{
		for(int i = 0; i < msg.size(); i++)
		{
			Messages.TCPMessage m = (Messages.TCPMessage) msg.get(i);

			Messages.TCPMessage v = this.messages.get(m.outputStream);
			if(v != null)
			{
				v.msg.addAll(m.msg);
			}
			else
			{
				this.messages.put(m.outputStream, m);
			}
		}
	}

	@Override
	public void AddToQueue(Messages.IPMessage msg)
	{
		ArrayList<Messages.IPMessage> m = new ArrayList<>();
		m.add(msg);
		this.AddToQueue(m);
	}
	@Override
	public void SendMessages() throws IOException
	{
		while(this.messages.size() > 0)
		{
			Messages.TCPMessage msg = this.messages.get(0);
			this.messages.remove(0);

			byte[] b = Serializer.Serialize(msg.msg);
			msg.outputStream.write(b);
			msg.outputStream.flush();
		}
	}

}
