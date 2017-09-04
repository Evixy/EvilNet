package EvilNet;



import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The Multicast Queue. Inherits from SendQueue.
 *
 * Queues messages to be sent over the Multicast protocol.
 */
class MulticastQueue extends SendQueue
{
	private HashMap<InetAddress, Messages.MultiMessage> messages;
	private ArrayList<InetAddress> groupsToSendTo;
	MulticastQueue(int tickRate)
	{
		super(tickRate);
		this.groupsToSendTo = new ArrayList<>();
		this.messages = new HashMap<InetAddress, Messages.MultiMessage>();
	}

	@Override
	void AddToQueue(ArrayList<Messages.IPMessage> msg)
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
				this.groupsToSendTo.add(m.GROUP);
			}
		}
	}

	@Override
	void SendMessages() throws IOException
	{
		while(this.groupsToSendTo.size() > 0)
		{
			InetAddress ip = this.groupsToSendTo.remove(0);
			Messages.MultiMessage msgToSend = this.messages.remove(ip);
			EvilNet.e_assert(msgToSend != null, "Message is null for some reason even though we have an IP for it");
			Sender.SendDatagram(msgToSend.socket, msgToSend.msg, msgToSend.GROUP, msgToSend.PORT);
		}
	}
}
