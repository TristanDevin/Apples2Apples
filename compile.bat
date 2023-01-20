@echo off
@echo "Compiling code ..."
set JUNIT=lib\junit-4.13.2.jar
javac -cp src -d bin src/StartServer.java
javac -cp src -d bin src/StartClient.java
javac -cp src -d bin src/cards/*.java
javac -cp src -d bin src/deck/*.java
javac -cp src -d bin src/main/*.java
javac -cp src -d bin src/utils/*.java
javac -cp src -d bin src/player/*.java
javac -d bin -cp %JUNIT%;src src/tests/*.java
@echo "Done."