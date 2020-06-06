
import java.util.Scanner;

public class Maxconnect4 {

    public static Scanner input_stream = null;
    public static GameBoard current_game = null;
    public static AiPlayer aiPlayer = null;

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static int HUMAN_PAWN;
    public static int COMPUTER_PAWN;
    public static int INVALID = 99;
    public static final String COMPUTER_FILE = "computer.txt";
    public static final String HUMAN_FILE = "human.txt";

    public enum MODE {
        INTERACTIVE,
        ONE_MOVE
    };

    public enum PLAYER_TYPE {
        HUMAN,
        COMPUTER
    };

    public static void main(String[] args) throws CloneNotSupportedException {
        // check for the correct number of arguments
        if (args.length != 4) {
            System.out.println("Looks like you have entered wrong number of argumemnts. 4 arguments are needed:\n"
                + "Command should be: java Maxconnect4 interactive [input file] [computer-next / human-next] [depth]\n"
                + " or:  java Maxconnect4 one-move [inputfile] [output_file] [depth]\n");

            exit_function(0);
        }

        // parse the input arguments
        String game_mode = args[0].toString(); // the game mode 
        String inputFile = args[1].toString(); // the input file
        int depthLevel = Integer.parseInt(args[3]); //  the depth level of the ai search

        // create and initialize the game board as per the provided input file
        current_game = new GameBoard(inputFile);

        // create the Ai Player object
        aiPlayer = new AiPlayer(depthLevel, current_game);

        if (game_mode.equalsIgnoreCase("interactive")) {
            current_game.set_GameMode(MODE.INTERACTIVE);
            if (args[2].toString().equalsIgnoreCase("computer-next")) {
                // computer make the next move
                current_game.set_FirstTurn(PLAYER_TYPE.COMPUTER);
                InteractiveMode();
                // human make the next move
            } else if (args[2].toString().equalsIgnoreCase("human-next")){
                current_game.set_FirstTurn(PLAYER_TYPE.HUMAN);
                HumanTurn();
            } else {
                System.out.println("\n" + "next turn's value isn't recognized \nEnter either computer-next or human-next \nPlease try again  \n");
                exit_function(0);
            }

            if (current_game.isBoardFull()) {
                System.out.println("\nThe Board is Full\n\nGame Over.");
                exit_function(0);
            }

        } else if (!game_mode.equalsIgnoreCase("one-move")) {
            System.out.println("\n" + game_mode + " is an unrecognized game mode. Enter either interactive or one-move \n");
            exit_function(0);
        } else {
            //one-move mode 
            current_game.set_GameMode(MODE.ONE_MOVE);
            String outputFileName = args[2].toString(); // the output game file that has been provided in the command line
            OneMoveMode(outputFileName);
        }
    } 
  
    //main() ends here

    //OneMoveMode() handles the computer's move for one move mode 
     
    private static void OneMoveMode(String outputFileName) throws CloneNotSupportedException {
        
        int playColumn = 99; // the players choice of column to play
        boolean playMade = false; // set to true once a play has been made

        System.out.print("\nMaxConnect-4 game:\n");

        System.out.print("Game state before move:\n");

        // print the current game board
        current_game.printGameBoard();

        // print the current scores
        System.out.println("Score: Player1 = " + current_game.getScore(ONE) + ", Player2 = " + current_game.getScore(TWO)
            + "\n ");

        if (current_game.isBoardFull()) {
            System.out.println("\n\nThe Board is Full\n\nGame Over.");
            return;
        }

        int current_player = current_game.getCurrentTurn();

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(current_game);

        if (playColumn == INVALID) {
            System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        current_game.playPiece(playColumn);

        // display the current game board
        System.out.println("move " + current_game.getPieceCount() + ": Player " + current_player + ", column "
            + (playColumn + 1));

        System.out.print("Game state after move:\n");

        // print the current game board
        current_game.printGameBoard();

        // print the current scores
        System.out.println("Score: Player1 = " + current_game.getScore(ONE) + ", Player2 = " + current_game.getScore(TWO)
            + "\n ");

        current_game.printGameBoardToFile(outputFileName);
    }

        // InteractiveMode() handles computer's move for interactive mode  
    private static void InteractiveMode() throws CloneNotSupportedException {

        printBoardAndScore();

        System.out.println("\n Please wait \n Computer's turn:\n");

        int playColumn = INVALID; // the players choice of column to play

        // AI play - random play
        playColumn = aiPlayer.findBestPlay(current_game);

        if (playColumn == INVALID) {
            System.out.println("\n\nThe Board is Full\n\nGame Over.");
            return;
        }

        // play the piece
        current_game.playPiece(playColumn);

        System.out.println("move: " + current_game.getPieceCount() + " , Player: Computer , Column: " + (playColumn + 1));

        current_game.printGameBoardToFile(COMPUTER_FILE);

        if (current_game.isBoardFull()) {
            printBoardAndScore();
            print_Result();
        } else {
            HumanTurn();
        }
    }

    //Final result is printed in this method.
    private static void print_Result() {
        int humanScore = current_game.getScore(Maxconnect4.HUMAN_PAWN);
        int computerScore = current_game.getScore(Maxconnect4.COMPUTER_PAWN);
        
        System.out.println("\n Final Result of the game:");
       
        if(computerScore>humanScore){
           System.out.println("\n You lost! Better luck next time.");
          
          
        }else if(computerScore<humanScore){
          
          System.out.println("\n Congratulations human! You won this game."); 
        }
         else {
            System.out.println("\n Game is tie! Well played");
        }
    }

    //HumanTurn() manages the human move  
    private static void HumanTurn() throws CloneNotSupportedException {
        
        printBoardAndScore();

        System.out.println("\n Human's turn:\nEnter between 1-7 here :");

        input_stream = new Scanner(System.in);

        int playColumn = INVALID;

        do {
            playColumn = input_stream.nextInt();
        } while (!isValidPlay(playColumn));

        // play the piece
        current_game.playPiece(playColumn - 1);

        System.out.println("move: " + current_game.getPieceCount() + " , Player: Human , Column: " + playColumn);
        
        current_game.printGameBoardToFile(HUMAN_FILE);

        if (current_game.isBoardFull()) {
            printBoardAndScore();
            print_Result();
        } else {
            InteractiveMode();
        }
    }
    //Prints board and score 
    public static void printBoardAndScore() {
        System.out.print("Game state :\n");

        // print the current game board
        current_game.printGameBoard();

        // print the current scores
        System.out.println("Score: Player1 = " + current_game.getScore(ONE) + ", Player2 = " + current_game.getScore(TWO)
            + "\n ");
    }

    private static boolean isValidPlay(int playColumn) {
        if (current_game.isValidPlay(playColumn - 1)) {
            return true;
        }
        System.out.println("Invalid column , Please enter column value between 1 to 7.");
        return false;
    }

    
    //exit function
    private static void exit_function(int value) {
        System.out.println("\n\nexiting from MaxConnectFour.java!\n\n");
        System.exit(value);
    }
}