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
rabbitmq-client-app2: ```itx.examples.rabbitmq.client2.Main```  
rabbitmq-client-app1: ```itx.examples.rabbitmq.client1.Main```  
