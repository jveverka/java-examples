#RabbitMQ demo
This demo consists of two rabbimq clients. Both are exchanging messages using two queues.
Firsh queue is for outbound messages and second one is for inbound messages. 

###rabbitmq-client-app1
This client sends messages into outbound queue and waits for replies 
from rabbitmq-client-app2 by listening on inbound queue.

###rabbitmq-client-app2
This client listens on outbound queue and every message received publishes into inbound queue.

## Build and Run
```gradle clean build```
