
@echo off
set JUNIT=lib\junit-4.13.2.jar
set HAMCREST=lib\hamcrest-core-1.3.jar

@echo "Running tests ..."
java -cp %JUNIT%;%HAMCREST%;bin org.junit.runner.JUnitCore tests.requirements