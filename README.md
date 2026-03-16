# MineSweeper Game
- Create an x by x area, where random mines are added.  Uncover squares with no mines.  Lose if you uncover one with a mine.  Win if all non-mined squares are uncovered.  See bottom for more details.
 
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

## Details of the MineSweeper Game
The game simulates a Minesweeper game on a square grid.

- The game begins by prompting the user for the grid size and the number of mines to be randomly placed on the grid.

- The game then generates the grid and randomly place the specified number of mines on the grid.

- The user will then be prompted to select a square on the grid to uncover.
    - If the selected square contains a mine, the game is over and the user loses.
    - Otherwise, the selected square is uncovered and reveals a number indicating how many of its adjacent squares contain mines.
    - If an uncovered square has no adjacent mines, the program should automatically uncover all adjacent squares until it reaches squares that do have adjacent mines.

- The game is won when all non-mine squares have been uncovered.

- The program displays the game grid and allow the user to input their choices through a command line interface.

- Additionally, the program tracks the user's progress throughout the game, displaying the minefield after each user input.

### Game play

#### Success example
```
Welcome to Minesweeper!

Enter the size of the grid (e.g. 4 for a 4x4 grid): 
4
Enter the number of mines to place on the grid (maximum is 35% of the total squares): 
3

Here is your minefield:
  1 2 3 4
A _ _ _ _
B _ _ _ _
C _ _ _ _
D _ _ _ _

Select a square to reveal (e.g. A1): D4
This square contains 0 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A _ _ 2 0
B _ _ 2 0
C _ 2 1 0
D _ 1 0 0

Select a square to reveal (e.g. A1): B1
This square contains 3 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A _ _ 2 0
B 3 _ 2 0
C _ 2 1 0
D _ 1 0 0

Select a square to reveal (e.g. A1): A1
This square contains 2 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A 2 _ 2 0
B 3 _ 2 0
C _ 2 1 0
D _ 1 0 0

Select a square to reveal (e.g. A1): D1
This square contains 1 adjacent mines. 

Here is your updated minefield:
  1 2 3 4
A 2 _ 2 0
B 3 _ 2 0
C _ 2 1 0
D 1 1 0 0

Congratulations, you have won the game!
Press any key to play again...
```
#### Failure example
```
Welcome to Minesweeper!

Enter the size of the grid (e.g. 4 for a 4x4 grid): 
3
Enter the number of mines to place on the grid (maximum is 35% of the total squares): 
3

Here is your minefield:
  1 2 3
A _ _ _
B _ _ _
C _ _ _

Select a square to reveal (e.g. A1): C3
Oh no, you detonated a mine! Game over.
Press any key to play again...
```