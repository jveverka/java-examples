RabbitMQ demo
=============
This demo consists of two rabbimq clients. Both are exchanging messages using two queues.
Firsh queue is for outbound messages and second one is for inbound messages. 

rabbitmq-client-app1
--------------------
This client sends messages into outbound queue and waits for replies 
from rabbitmq-client-app2 by listening on inbound queue.

rabbitmq-client-app2
--------------------
This client listens on outbound queue and every message received publishes 
into inbound queue. **This client should be started first.**

Install RabbitMQ
----------------
This demo requires RabbitMQ to be installed on localhost.

```
                      RabbitQM                        
client-app1 --> client-request-queue  --> client-app2 -\
        ^                                              |
        \-  <-- client-response-queue <-- client-app2 -/ 
```   

Build and Run
-------------
```gradle clean build```  
```gradle clean installDist distZip```  

rabbitmq-client-app2: ```itx.examples.rabbitmq.client2.Main```  
rabbitmq-client-app1: ```itx.examples.rabbitmq.client1.Main```  

```./rabbitmq-client-app2/build/install/rabbitmq-client-app2/bin/rabbitmq-client-app2 -Dserver=127.0.0.1```  
```./rabbitmq-client-app1/build/install/rabbitmq-client-app1/bin/rabbitmq-client-app1 -Dserver=127.0.0.1```  

Performance measurements
------------------------
MessageData exchanged between clients in those tests looks like this: ```{ contextId: $i, message: "data" }```

|  client-app2  | RabbimMQ server | client-app1   | network   | avg. msg/sec |
|---------------|-----------------|---------------|-----------|--------------|  
| i7-3632QM CPU | i7-3632QM CPU   | i7-3632QM CPU | localhost | 23214.436    |