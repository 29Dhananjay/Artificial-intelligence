
Instructions for compiling and running the Junit tests (MAC)
=====================================================================================================

1. Enter the terminal.

2. Change to the directory which contains the src and the tests folder.

3. Compile JUnit tests: javac -cp junit.jar:hamcrest.jar:./tests/:. tests/*.java

4. Run JUnit tests: java -cp junit.jar:hamcrest.jar:./tests/:. org.junit.runner.JUnitCore ModelTest



Instructions for compiling and running the Junit tests (Windows)
=====================================================================================================

1. Enter the command line.

2. Change to the directory which contains the src and the tests folder.

3. Compile JUnit tests: javac -cp junit.jar;hamcrest.jar;.\tests\;. tests\*.java

4. Run JUnit tests: java -cp junit.jar;hamcrest.jar;.\tests\;. org.junit.runner.JUnitCore ModelTest
