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

#### repeatHello
client sends several warmup messages and than number of hello messages to server synchronously and ends.
example shows repeatHello with 500 warm-up messages and 1k hello messages. Time and performance is printed 
on stdout when done.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 repeatHello -w 500 -c 1000 -m hi```
