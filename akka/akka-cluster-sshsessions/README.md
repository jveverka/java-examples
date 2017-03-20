Akka cluster demo
=================
This is the demo od clustered service which provides ssh connection to clients and runs in akka cluster.

Build and Install
-----------------
```
gradle clean install
gradle clean installDist
```

Run cluster or standalone
-------------------------
Run application as 3-node cluster.
```
gradle clean installDist
./docs/bin/start-node-01.sh
./docs/bin/start-node-02.sh
./docs/bin/start-node-03.sh
```
Run 3 node cluster in one terminal window (requires tmux)
```
./docs/bin/start-cluster.sh
```
Run standalone node
```
./docs/bin/start-single-node.sh
```
