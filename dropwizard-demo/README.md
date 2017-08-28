Simple DropWizard application demo
==================================
This is simple [dropwizard.io](http://www.dropwizard.io) application demo.

Web EndPoints
-------------
POST http://localhost:8080/dataservice/data { "data": "payload" }  
http://localhost:8080/web/index.html - static content  
http://localhost:8080/ws/* - websockets 

Build and Run
-------------
```gradle clean installDist distZip```  
```./build/install/dropwizard-demo/bin/dropwizard-demo server```

