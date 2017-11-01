# Jetty HTTP2 server/client demo

Server URLs:
```
GET HTTP 1.1 http://localhost:8080/data/system/info
GET HTTP 2.0 https://localhost:8443/data/system/info
```

For Java8, start JVM with VM option:  
```-Xbootclasspath/p:/opt/alpn-boot-8.1.11.v20170118.jar```

