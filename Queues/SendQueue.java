package EvilNet.Queues;


import java.io.IOException;
import java.util.ArrayList;

abstract class SendQueue implements Runnable
{
	private int tickRate;
	private long timeBetweenTicks;
	ArrayList<Messages.IPMessage> messageQueue;

	private long previous;
	private long now;
	private long spent;

	public SendQueue(int tickRate)
	{
		this.SetTickRate(tickRate);
	}

	public boolean IsTimeToSend()
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

	public void InitQueue()
	{
		this.now = System.currentTimeMillis();
		this.previous = now;
		this.spent = 0;
	}


	public void SetTickRate(int tickRate)
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

	public abstract void AddToQueue(ArrayList<Messages.IPMessage> msg);
	public abstract void AddToQueue(Messages.IPMessage msg);
	public abstract void SendMessages() throws IOException;
}
