package EvilNet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The UDP Queue. Inherits from SendQueue.
 *
 * Queues messages to be sent over the UDP protocol.
 */
class UDPQueue extends SendQueue
{
	private ArrayList<Messages.UDPMessage> messages;

	UDPQueue(int tickRate)
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
			Messages.UDPMessage m = (Messages.UDPMessage) msg.get(i);
			this.messages.add(m);
		}
	}

	@Override
	void
	SendMessages() throws IOException
	{
		while(this.messages.size() > 0)
		{
			Messages.UDPMessage msgToSend = this.messages.remove(0);
			Evil.e_assert(msgToSend != null, "Message is null for some reason even though we have an IP for it");
			Sender.SendDatagram(msgToSend.socket, msgToSend.msg, msgToSend.IP, msgToSend.PORT);
		}
	}
}