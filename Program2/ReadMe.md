# Max Connect-4 Game

There are 3 classes:
1. MaxConnect4
* main() Main function has takes command line arguments.
* InteractiveMode() Interactive mode handles computers move.
* OneMoveMode() One move mode handles the computers move. 
* HumanTurn() Manages the human move that is made during the interactive mode play.
* isValidPlay() Checks if user makes a valid play.
* printBoardAndScore() Displays current board state and score of each player.
* print_Result() Prints the final result.


2. GameBoard
* GameBoard() creates a GameBoard object based on the input file given as an argument.
* setPieceValue() Sets piece value for human and computer 
* printGameBoard() prints the GameBoard to the screen


3. AiPlayer
* findBestPlay() method makes the decision to make a move for the computer using
  the min and max value.
* Min_Utility() Calculates the minimum utility value.
* Max_Utility() Calculates the maximum utility value.


4. To Compile
javac Maxconnect4.java AiPlayer.java Gameboard.java

5. To run the program
* One-Move mode:
java Maxconnect4 one-move [input_file] [output_file] [depth]
Eg:java Maxconnect4 one-move input2.txt output1.txt 2

* Interactive Mode
java Maxconnect4 interactive [input file] [computer-next / human-next] [depth]
Eg:java Maxconnect4 interactive input2.txt computer-next 2
![alt text]()
