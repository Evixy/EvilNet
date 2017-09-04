package EvilNet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * The TCP Queue. Inherits from SendQueue.
 *
 * Queues messages to be sent over the TCP protocol.
 */
class TCPQueue extends SendQueue
{
	private HashMap<DataOutputStream, Messages.TCPMessage> messages;
	private ArrayList<DataOutputStream> streams;
	TCPQueue(int tickRate)
	{
		super(tickRate);
		this.messages = new HashMap<DataOutputStream, Messages.TCPMessage>();
		this.streams = new ArrayList<>();
	}

	@Override
	void AddToQueue(ArrayList<Messages.IPMessage> msg)
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
				this.streams.add(m.outputStream);
			}
		}
	}

	@Override
	void SendMessages() throws IOException
	{
		while(this.streams.size() > 0)
		{
			DataOutputStream stream = this.streams.remove(0);
			Messages.TCPMessage msg = this.messages.remove(stream);
			EvilNet.e_assert(msg != null, "Message is null even though there is a stream");
			Sender.SendTCP(msg.outputStream, msg.msg);
		}
	}
}
