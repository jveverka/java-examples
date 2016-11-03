#!/bin/bash

export PATH=/opt/jre/bin:$PATH
export JAVA_HOME=/opt/jre
export CLASSPATH=/tmp/java-logging/build_gradle/libs/java-logging.jar
export LOGGING_PROPERTIES=/tmp/java-logging/src/main/resources/logging.linux.properties

java -Djava.util.logging.config.file=$LOGGING_PROPERTIES -classpath $CLASSPATH itx.logging.Main

