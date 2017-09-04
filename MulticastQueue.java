package EvilNet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The Multicast Queue. Inherits from SendQueue.
 *
 * Queues messages to be sent over the Multicast protocol.
 */
class MulticastQueue extends SendQueue
{
	private ArrayList<Messages.MultiMessage> messages;

	MulticastQueue(int tickRate)
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
			Messages.MultiMessage m = (Messages.MultiMessage) msg.get(i);
			this.messages.add(m);
		}
	}

	@Override
	void
	SendMessages() throws IOException
	{
		while(this.messages.size() > 0)
		{
			Messages.MultiMessage msgToSend = this.messages.remove(0);
			Evil.e_assert(msgToSend != null, "Message is null for some reason even though we have an IP for it");
			Sender.SendMulticast(msgToSend.socket, msgToSend.msg, msgToSend.GROUP, msgToSend.PORT);
		}
	}
}
