This is a list of things to do with EvilNet.

1. 	Make SendQeueus linked to a specific connection to make the engine able to scale better.
	As of right now, there is only one SendQueue of each type on the server side, 
	meaning that with a very high number of active connections th engine will eventually 
	not be able to cope with the amount of messages to send with every tick. A way to do this 
	while still keeping the engine generic could be to create an interface that the user can 
	implement. SendQueues already extend Thread so they could be stored in EvilNet as a HashMap
	with either a DataOutputStream or an IP-adress/Multicast-group as key and the SendQueue 
	as a value. 
	
2.	Something that I'll come to think about later :)
