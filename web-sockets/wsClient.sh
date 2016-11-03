#!/bin/bash

CLASS_PATH=WebSocketClient.jar

java -classpath $CLASS_PATH org.itx.wsclient.Client ws://127.0.0.1:8080/ws

