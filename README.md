# EvilNet
Small serialization and network engine for Java. 

The goal of this engine is to show how to do serialization for network applications properly in Java.

The engine can handle everything between taking objects and serialize them and send them on one end and
receiving them as a byte-array on the other end and then deserialize them into objects with the same values. 

The engine makes use of generic types so a user can send any type of object into the engine as long as the 
object implements the Serializable-interface. 

EvilNet can make use of all network traffic protocols, including multicast sockets. Messages are sent based on
a tick rate that is defined when EvilNet is initialized. 

Download this engine as a zip and put it into your project. Depending on where you put the files you may have 
to chanage the package-names for EvilNet. After that, check the file EvilNet.java to get an idea of how to 
make use of the engine in your project.
