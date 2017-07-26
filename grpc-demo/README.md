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
```./build/install/grpc-demo/bin/grpc-demo --host <serverHostName> --port 50051 --scenario <scenarioName>```

Supported client scenarios
--------------------------

#### sayHello 
client sends one hello message to server and finish.  
```./build/install/grpc-demo/bin/grpc-demo --host localhost --port 50051 --scenario sayHello```

