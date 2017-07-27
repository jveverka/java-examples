gRPC simple demo
================
This example implements very simple demo of [gRPC](https://grpc.io/) communication
between simple server and client application. Once server is
started, client connects to the server and can run one of pre-defined
scenarios. Server always responds to particular client scenario.

Build and Install
-----------------
```gradle clean installDist distZip```  
```./build/install/grpc-demo/bin/grpc-demo --help```

Run Server
----------
```./build/install/grpc-demo/bin/grpc-demo --port 50051```


Run Client
----------
```./build/install/grpc-demo/bin/grpc-demo --host <serverHostName> --port 50051 <scenarioName> <scenarioParameters>```

Supported client scenarios
--------------------------

#### sayHello 
client sends single hello message to server synchronously and ends.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 sayHello -m hi```

#### getDataSync
client sends synchronously several warm-up messages and than number of data messages to server synchronously and ends.
example shows getDataSync with 500 warm-up messages and 1k data messages. Test is repeated 5 times, each time time and performance is printed 
on stdout when done.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 getDataSync -w 500 -c 1000 -m hi -r 5```

#### getDataAsync
client sends asynchronously several warm-up messages and than number of data messages to server asynchronously and ends.
example shows getDataSync with 500 warm-up messages and 1k data messages. Test is repeated 5 times, each time time and performance is printed 
on stdout when done.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 getDataAsync -w 500 -c 1000 -m hi -r 5```

Performance measurements
------------------------
Here are some performance measurements of for synchronous and asynchronous message sending.

| Scenario    | Parameters            | Server       | Client        | Network   | Result [msg/s] |
|-------------|-----------------------|--------------|---------------|:---------:|---------------:|
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | i7-3632QM CPU | localhost | 22 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | i7-3632QM CPU | localhost |  6 400         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | Odroid C1 SBC | 1 Gig/s   | 10 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | Odroid C1 SBC | 1 Gig/s   |    860         |
| getDataAsync|-c 100000 -r 20 -m data|i7-3632QM CPU | i7-4810MQ CPU | 1 Gig/s   | 43 000         |
| getDataSync |-c 100000 -r 20 -m data|i7-3632QM CPU | i7-4810MQ CPU | 1 Gig/s   |  1 600         |

