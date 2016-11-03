rem echo off
set PATH=%PATH%;c:\Program Files\Java\jdk1.8.0_60\bin
set JAVA_HOME=c:\Program Files\Java\jdk1.8.0_60
set CLASSPATH=d:\Private\java-logging\build_gradle\libs\java-logging.jar
set LOGGING_PROPERTIES=d:\Private\java-logging\src\main\resources\logging.windows.properties

java -Djava.util.logging.config.file=%LOGGING_PROPERTIES% -classpath %CLASSPATH% itx.logging.Main

