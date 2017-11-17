# Atomix.io clustering demo
Simple demo for [atomix.io](http://atomix.io/) library.

## Build and Run
```
gradle clean build
gradle clean installDist distZip
```
To start 3 node cluster:
```
./build/install/atomix/bin/atomix 0
./build/install/atomix/bin/atomix 1
./build/install/atomix/bin/atomix 2
```
