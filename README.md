# EvilNet
Small serialization and network engine for Java. 

The goal of this engine is to show how to do serialization for network applications properly in Java.

The engine can handle everything between taking objects and serialize them and send them on one end and
receiving them as a byte-array on the other end and then deserialize them into objects with the same values. 

The engine makes use of generic types so a user can send any type of object into the engine as long as the 
object implements the Serializable-interface. 

EvilNet can make use of all network traffic protocols, including multicast sockets. 

Check the file EvilNet.java to get an idea of how to make use of the engine.
