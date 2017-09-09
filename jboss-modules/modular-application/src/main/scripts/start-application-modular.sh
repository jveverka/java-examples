#!/usr/bin/env bash
# This script starts application using Jboss Modules library
# to separate class loaders for different modules.

java -jar jboss-modules-1.6.0.Final.jar -mp modules app
