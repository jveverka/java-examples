Java modules demo
=================
This is simple example of 'modular' java application, 
based on [JBoss Modules](https://github.com/jboss-modules/jboss-modules) project.
For further details, see [this manual](https://jboss-modules.github.io/jboss-modules/manual/).
Modular application means that the modules listed below have own class loaders.

This demo simulates the situation where java application has to use java libraries that are 
not aligned and they have same dependencies but of different versions. Artifacts used in this 
demo application does not need to be OSGi compatible or have special files in META-INF directory.
That's why JBoss modules system is much easier to use than OSGi.

__Application projects:__
* __modular-application__ - app module and module.xml files for other modules
* __service-client__ - implementation of service-client module
* __service-module-01-api__ - implementation of service-module-01-api module
* __service-module-01__ - implementation of service-module-01 module
* __service-module-02-api__ - implementation of service-module-02-api module
* __service-module-02__ - implementation of service-module-02 module
* __service-registry__ - implementation of service-registry module

__Application modules:__
* __app__ - main application module, app is initialized here.
* __common__ - common libraries like log4j, slf4j  
* __service-client__ - service that is using service-module-01 and service-module-02
* __service-module-01-api__ - APIs for service-module-01
* __service-module-01__ - data service using guava 23.0
* __service-module-02-api__ - APIs for service-module-01
* __service-module-02__ - data service using guava 16.0
* __service-registry__ - simple replacement for DI

Build and Run
-------------
```gradle clean buildmodules```

### Run as standard java app
In this case run pre-build application:  
```cd modular-application/build/app```   
```./start-application-simple.sh```  
There is ```java.lang.NoSuchMethodError``` thrown and application fails because service-module-01 and service-module-02 are both using guava, but 
different versions (23.0 and 16.0). service-module-02 uses intentionally deprecated APIs that are not available in newer version of guava. 
Check the [start script](modular-application/src/main/scripts/start-application-simple.sh).

### Run as modular java app
In this case run pre-build application:  
```cd modular-application/build/app```   
```./start-application-modular.sh```  
This application uses static modularity, all modules are prepared in application directory ```app/modules``` and are 
loaded and activated on application start by __app__ module. The __app__ module is started by name, see 
[start script](modular-application/src/main/scripts/start-application-modular.sh). 
There is no ```java.lang.NoSuchMethodError``` because modules are
separated by their own class loaders. Application runs using both version of guava.
