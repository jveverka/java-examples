# HTTP Jetty server and client demo

This demo starts jetty server on following URLs
```
http://localhost:8080/system/info  
https://localhost:8443/system/info
```
Server provides secure and non-secure connection on ports 8080 and 8443.  
Server uses src/main/resources/server.jks file which contains self-signed server's certificate and server's private key.  
Client uses src/main/resources/client.jks file which contains server's self-signed certificate.
