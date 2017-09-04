package EvilNet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The UDP Queue. Inherits from SendQueue.
 *
 * Queues messages to be sent over the UDP protocol.
 */
class UDPQueue extends SendQueue
{
	private HashMap<InetAddress, Messages.UDPMessage> messages;
	private ArrayList<InetAddress> ipsToSendTo;
	UDPQueue(int tickRate)
	{
		super(tickRate);
		this.ipsToSendTo = new ArrayList<>();
		this.messages = new HashMap<InetAddress, Messages.UDPMessage>();
	}

	@Override
	void AddToQueue(ArrayList<Messages.IPMessage> msg)
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
				this.ipsToSendTo.add(m.IP);
			}
		}
	}

	@Override
	void SendMessages() throws IOException
	{
		while(this.ipsToSendTo.size() > 0)
		{
			InetAddress ip = this.ipsToSendTo.remove(0);
			Messages.UDPMessage msgToSend = this.messages.remove(ip);
			EvilNet.e_assert(msgToSend != null, "Message is null for some reason even though we have an IP for it");
			Sender.SendDatagram(msgToSend.socket, msgToSend.msg, msgToSend.IP, msgToSend.PORT);
		}
	}
}