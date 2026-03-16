# MineSweeper Game
 
## How To Run Using Maven
- At the folder where the pom.xml is found, at the console, please type this below at the command line and enter
  -  mvn clean compile exec:java
     
## How To Run Using Maven With Debug Or Tracing Information
- At the folder where the pom.xml is found, at the console, please type this below at the command line and enter
    -  mvn clean compile exec:java -Ddebug=true

## Assumptions and Rules
- The minimum size is 2, and the maximum is 9.  
  - It is not possible to have a size of 1, as the minimum number of mines is 35%.
  - The maximum uncovering input is I9, because the maximum size is 9.
  - If needed, this maximum limit can be easily increased.  Increase the constant field for the maximum, and make sure that the display remains aligned

## Small Discussion About the Implementation
- The class that needs to be executed is MineSweeper
- The class that contains all of the game board information is MineTracker
- Each square is represented by a Square class

## Test
- Use below to run the tests
    -  mvn test
