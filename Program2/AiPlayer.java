
import java.util.*;

public class AiPlayer {

    public int depth_Level;
    public GameBoard gameShallow;

    public AiPlayer(int depth, GameBoard curr_Game) {
        this.depth_Level = depth;
        this.gameShallow = curr_Game;
    }
    
    public int findBestPlay(GameBoard curr_Game) throws CloneNotSupportedException {
        int play_variable = Maxconnect4.INVALID;
        if (curr_Game.getCurrentTurn() == Maxconnect4.ONE) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.Number_of_Columns; i++) {
                if (curr_Game.isValidPlay(i)) {
                    GameBoard nextMove = new GameBoard(curr_Game.getGameBoard());
                    nextMove.playPiece(i);
                    int value = Max_Utility(nextMove, depth_Level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v > value) {
                        play_variable = i;
                        v = value;
                    }
                }
            }
        } else {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.Number_of_Columns; i++) {
                if (curr_Game.isValidPlay(i)) {
                    GameBoard nextMove = new GameBoard(curr_Game.getGameBoard());
                    nextMove.playPiece(i);
                    int value = Min_Utility(nextMove, depth_Level, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (v < value) {
                        play_variable = i;
                        v = value;
                    }
                }
            }
        }
        return play_variable;
    }


    private int Min_Utility(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
     
        if (!gameBoard.isBoardFull() && depth_level > 0) {
            int v = Integer.MAX_VALUE;
            for (int i = 0; i < GameBoard.Number_of_Columns; i++) {
                if (gameBoard.isValidPlay(i)) {
                    GameBoard board4NextMove = new GameBoard(gameBoard.getGameBoard());
                    board4NextMove.playPiece(i);
                    int value = Max_Utility(board4NextMove, depth_level - 1, alpha_value, beta_value);
                    if (v > value) {
                        v = value;
                    }
                    if (v <= alpha_value) {
                        return v;
                    }
                    if (beta_value > v) {
                        beta_value = v;
                    }
                }
            }
            return v;
        } else {
            
            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
        }
    }

    private int Max_Utility(GameBoard gameBoard, int depth_level, int alpha_value, int beta_value)
        throws CloneNotSupportedException {
        
        if (!gameBoard.isBoardFull() && depth_level > 0) {
            int v = Integer.MIN_VALUE;
            for (int i = 0; i < GameBoard.Number_of_Columns; i++) {
                if (gameBoard.isValidPlay(i)) {
                    GameBoard next_board_move = new GameBoard(gameBoard.getGameBoard());
                    next_board_move.playPiece(i);
                    int value = Min_Utility(next_board_move, depth_level - 1, alpha_value, beta_value);
                    if (v < value) {
                        v = value;
                    }
                    if (v >= beta_value) {
                        return v;
                    }
                    if (alpha_value < v) {
                        alpha_value = v;
                    }
                }
            }
            return v;
        } else {
          
            return gameBoard.getScore(Maxconnect4.TWO) - gameBoard.getScore(Maxconnect4.ONE);
        }
    }

}
