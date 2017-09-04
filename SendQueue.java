package EvilNet;


import java.io.IOException;
import java.util.ArrayList;

/**
 *  This is the abstract class SendQueue.
 *
 *  Each of the respective queues (TCP, Datagram and Multicast) all inherit from this class.
 *  When the user decides to send a message over either of the supported protocols, a queue is created with the tickRate
 *  (amount of times messages are sent every second) specified when EvilNet was initialized.
 *
 *  Once the queue has been made, messages will be added to the queue and messages are sent based on the tick rate.
 */
abstract class SendQueue extends Thread
{
	private int tickRate;
	private long timeBetweenTicks;

	private long previous;
	private long now;
	private long spent;

	SendQueue(int tickRate)
	{
		this.SetTickRate(tickRate);
	}

	private boolean IsTimeToSend()
	{
		this.now = System.currentTimeMillis();
		long delta = this.now - this.previous;
		this.spent += delta;
		this.previous = this.now;
		if(this.spent >= timeBetweenTicks)
		{
			this.spent = 0;
			return true;
		}
		return false;
	}

	private void InitQueue()
	{
		this.now = System.currentTimeMillis();
		this.previous = this.now;
		this.spent = 0;
	}


	void SetTickRate(int tickRate)
	{
		this.tickRate = tickRate;
		this.timeBetweenTicks = 1000L / (long)this.tickRate;
	}

	@Override
	public void run()
	{
		this.InitQueue();
		while(true)
		{
			if(this.IsTimeToSend())
			{
				try
				{
					this.SendMessages();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	void AddToQueue(Messages.IPMessage msg)
	{
		ArrayList<Messages.IPMessage> m = new ArrayList<>();
		m.add(msg);
		this.AddToQueue(m);
	}

	abstract void AddToQueue(ArrayList<Messages.IPMessage> msg);

	abstract void SendMessages() throws IOException;
}
