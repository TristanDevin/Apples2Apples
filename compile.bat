@echo off
@echo "Compiling code ..."

javac -cp src -d bin src/cards/*.java
javac -cp src -d bin src/deck/*.java
javac -cp src -d bin src/main/*.java
javac -cp src -d bin src/tests/*.java
javac -cp src -d bin src/utils/*.java
javac -cp src -d bin src/*.java


@echo "Done."