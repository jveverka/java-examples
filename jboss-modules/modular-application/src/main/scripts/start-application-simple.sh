#!/usr/bin/env bash

CLASS_PATH=modules/service-registry/service-registry-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/service-module-02-api/service-module-02-api-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/service-module-02/service-module-02-impl-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/service-module-01-api/service-module-01-api-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/service-module-01/service-module-01-impl-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/service-client/service-client-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/common/log4j-1.2.17.jar
CLASS_PATH=${CLASS_PATH}:modules/common/slf4j-api-1.7.25.jar
CLASS_PATH=${CLASS_PATH}:modules/common/slf4j-log4j12-1.7.25.jar
CLASS_PATH=${CLASS_PATH}:modules/app/modular-application-1.0.0-SNAPSHOT.jar
CLASS_PATH=${CLASS_PATH}:modules/service-module-01/guava-23.0.jar
CLASS_PATH=${CLASS_PATH}:modules/service-module-02/guava-18.0.jar

java -cp ${CLASS_PATH} itx.examples.modules.application.Main
