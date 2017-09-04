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
	private ArrayList<Messages.TCPMessage> messages;

	TCPQueue(int tickRate)
	{
		super(tickRate);
		this.messages = new ArrayList<>();
	}

	@Override
	void
	AddToQueue(ArrayList<Messages.IPMessage> msg)
	{
		for(int i = 0; i < msg.size(); i++)
		{
			Messages.TCPMessage m = (Messages.TCPMessage) msg.get(i);
			this.messages.add(m);
		}
	}

	@Override
	void
	SendMessages() throws IOException
	{
		while(this.messages.size() > 0)
		{
			Messages.TCPMessage msg = this.messages.remove(0);
			Evil.e_assert(msg != null, "Message is null even though there is a stream");
			Sender.SendTCP(msg.outputStream, msg.msg);
		}
	}
}
