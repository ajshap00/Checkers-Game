/*
 * Names: Alex Shapiro
 * netID: ashapi,
 * G#: 01370758
 * Lecture section: 001
 * Lab section: 202
 */

import java.util.ArrayList;

public class MiniCheckers {
    // The game board represented as a 2D array of characters
    private char[][] board;

    // Players representing Red and Black in the game
    private Player red;
    private Player black;

    // Constructor to initialize MiniCheckers with Red and Black players and an initial game board
    public MiniCheckers(Player red, Player black) {
        this.red = red;
        this.black = black;
        this.board = new char[][]{
            {'r', '.', 'r', '.', 'r'},
            {'.', '_', '.', '_', '.'},
            {'_', '.', '_', '.', '_'},
            {'.', '_', '.', '_', '.'},
            {'b', '.', 'b', '.', 'b'}
        };
    }

    // Getter for the game board
    public char[][] getBoard() {
        return board;
    }

    // Setter for the game board
    public void setBoard(char[][] board) {
        this.board = board;
    }

    // Getter for the Red player
    public Player getRed() {
        return red;
    }

    // Setter for the Red player
    public void setRed(Player red) {
        this.red = red;
    }

    // Getter for the Black player
    public Player getBlack() {
        return black;
    }

    // Setter for the Black player
    public void setBlack(Player black) {
        this.black = black;
    }

    // Method to count the number of checkers of a given color on the board
    public int countChecker(char color) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == color) {
                    count++;
                }
            }
        }
        return count;
    }

    // Method to check if a player has won
    public boolean checkWin(Player player) {
        char opponentChecker = (player == red) ? 'b' : 'r';
        return countChecker(opponentChecker) == 0;
    }

    // Method to check if a player has lost
    public boolean checkLose(Player player) {
        char opponentChecker = (player == red) ? 'b' : 'r';
        return countChecker(opponentChecker) != 0;
    }

    // Override toString method to provide a string representation of the game board
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (char[] row : board) {
            for (char cell : row) {
                res.append(cell);
            }
            res.append("\n");
        }
        return res.toString();
    }
    
    private MiniCheckers makeClone() {
        /* provided code. You may call this method, but you should NOT modify it */
        MiniCheckers newMiniCheckers = new MiniCheckers(this.red, this.black);
        char[][] newBoard = newMiniCheckers.getBoard();
        char[][] curBoard = this.getBoard();
        for (int i = 0; i < curBoard.length; i++) {
            for (int j = 0; j < curBoard[i].length; j++) {
                newBoard[i][j] = curBoard[i][j];
            }
        }
        newMiniCheckers.setBoard(newBoard);
        return newMiniCheckers;
    }

    private MiniCheckers movePiece(int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        MiniCheckers move = this.makeClone();
        char[][] newBoard = move.getBoard();
        char tmpPiece = newBoard[startRow][startCol];
        newBoard[startRow][startCol] = '_';
        newBoard[endRow][endCol] = tmpPiece;
        if((tmpPiece=='r' && endRow==newBoard.length-1)||(tmpPiece=='b'&&endRow==0)){
            newBoard[endRow][endCol] = Character.toUpperCase(newBoard[endRow][endCol]);
        }
        return move;
    }

    private static void removePiece(char[][] board, int i, int j){
        /* provided code. You may call this method, but you should NOT modify it */
        board[i][j] = '_';
    }

    private static boolean validIndex(char[][] board, int i, int j){
        /* provided code. You may call this method, but you should NOT modify it */
        if(i<0 || j<0 || i>=board.length || j>=board[0].length) return false;
        return true;
    }

    private static boolean redCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol) {
        if (!validIndex(board, startRow, startCol) || !validIndex(board, endRow, endCol)) {
            return false;
        }

        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);

        // Check if indices are exactly 1 greater or less than the starting indices
        if (rowDiff != 1 || colDiff != 1) {
            return false;
        }

        char startChecker = board[startRow][startCol];

        // Check if there's a red checker at the starting position
        if (startChecker != 'r' && startChecker != 'R') {
            return false;
        }

        // Check if the checker is not a king and trying to move backward
        if (startChecker == 'r' && endRow >= startRow) {
            return false;
        }

        // Check if the destination is an unoccupied space
        return board[endRow][endCol] == '_';
    }

    private static boolean redCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) return false;
        if(board[startRow][startCol] == 'r'){
            if(endRow != startRow+2) return false;
            if(board[endRow][endCol] != '_') return false;
            if( (endCol == startCol+2 && (board[startRow+1][startCol+1] == 'b' || board[startRow+1][startCol+1] == 'B')) ||
                (endCol == startCol-2 && (board[startRow+1][startCol-1] == 'b' || board[startRow+1][startCol-1] == 'B'))){
                return true;
            } else {
                return false;
            }
        } else if(board[startRow][startCol] == 'R'){
            if(board[endRow][endCol] != '_') return false;
            if(endRow > startRow && endCol > startCol){
                //down-right
                if(board[startRow+1][startCol+1]=='b' || board[startRow+1][startCol+1]=='B') return true;
                else return false;
            } else if(endRow < startRow && endCol > startCol){
                //up-right
                if(board[startRow-1][startCol+1]=='b' || board[startRow-1][startCol+1]=='B') return true;
                else return false;
            } else if(endRow > startRow && endCol < startCol){
                //down-left
                if(board[startRow+1][startCol-1]=='b' || board[startRow+1][startCol-1]=='B') return true;
                else return false;
            } else if(endRow < startRow && endCol < startCol){
                //up-left
                if(board[startRow-1][startCol-1]=='b' || board[startRow-1][startCol-1]=='B') return true;
                else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    private static boolean blackCanMoveHere(char[][] board, int startRow, int startCol, int endRow, int endCol) {
        if (!validIndex(board, startRow, startCol) || !validIndex(board, endRow, endCol)) {
            return false;
        }

        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);

        // Check if indices are exactly 1 greater or less than the starting indices
        if (rowDiff != 1 || colDiff != 1) {
            return false;
        }

        char startChecker = board[startRow][startCol];

        // Check if there's a black checker at the starting position
        if (startChecker != 'b' && startChecker != 'B') {
            return false;
        }

        // Check if the checker is not a king and trying to move backward
        if (startChecker == 'b' && endRow <= startRow) {
            return false;
        }

        // Check if the destination is an unoccupied space
        return board[endRow][endCol] == '_';
    }

    private static boolean blackCanJumpHere(char[][] board, int startRow, int startCol, int endRow, int endCol){
        /* provided code. You may call this method, but you should NOT modify it */
        if(!validIndex(board, startRow, startCol) || !validIndex(board,endRow,endCol)) return false;
        if(Math.abs(startRow-endRow)!=2 || Math.abs(startCol-endCol)!=2) return false;
        if(board[startRow][startCol] == 'b'){
            if(endRow != startRow-2) return false;
            if(board[endRow][endCol] != '_') return false;
            if( (endCol == startCol+2 && (board[startRow-1][startCol+1] == 'r' || board[startRow-1][startCol+1] == 'R')) ||
                (endCol == startCol-2 && (board[startRow-1][startCol-1] == 'r' || board[startRow-1][startCol-1] == 'R'))){
                return true;
            } else {
                return false;
            }
        } else if(board[startRow][startCol] == 'B'){
            if(board[endRow][endCol] != '_') return false;
            if(endRow > startRow && endCol > startCol){
                //down-right
                if(board[startRow+1][startCol+1]=='r' || board[startRow+1][startCol+1]=='R') return true;
                else return false;
            } else if(endRow < startRow && endCol > startCol){
                //up-right
                if(board[startRow-1][startCol+1]=='r' || board[startRow-1][startCol+1]=='R') return true;
                else return false;
            } else if(endRow > startRow && endCol < startCol){
                //down-left
                if(board[startRow+1][startCol-1]=='r' || board[startRow+1][startCol-1]=='R') return true;
                else return false;
            } else if(endRow < startRow && endCol < startCol){
                //up-left
                if(board[startRow-1][startCol-1]=='r' || board[startRow-1][startCol-1]=='R') return true;
                else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<MiniCheckers> possibleMoves(Player player) {
        /* provided code. You may call this method, but you should NOT modify it */
        char[][] curBoard = this.getBoard();
        ArrayList<MiniCheckers> rv = new ArrayList<MiniCheckers>();
        if(player == this.red){
            boolean couldJump = false;
            for(int i=0;i<curBoard.length;i++){
                for(int j=0;j<curBoard[i].length;j++){
                    if(board[i][j]=='r' || board[i][j] =='R'){
                        if(redCanJumpHere(board,i,j,i-2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                            removePiece(newBoard.board,i-1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i-2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                            removePiece(newBoard.board,i-1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i+2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                            removePiece(newBoard.board,i+1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(redCanJumpHere(board,i,j,i+2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                            removePiece(newBoard.board,i+1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                    }
                }
            }
            if(!couldJump){
                for(int i=0;i<curBoard.length;i++){
                    for(int j=0;j<curBoard[i].length;j++){
                        if(board[i][j]=='r' || board[i][j]=='R'){                            
                            if(redCanMoveHere(board,i,j,i-1,j-1)){
                                rv.add(movePiece(i,j,i-1,j-1));
                            }
                            if(redCanMoveHere(board,i,j,i-1,j+1)){
                                rv.add(movePiece(i,j,i-1,j+1));
                            }
                            if(redCanMoveHere(board,i,j,i+1,j-1)){
                                rv.add(movePiece(i,j,i+1,j-1));
                            }
                            if(redCanMoveHere(board,i,j,i+1,j+1)){
                                rv.add(movePiece(i,j,i+1,j+1));
                            }
                        }
                    }
                }
            }
        } else if(player==this.black){
            boolean couldJump = false;
            //check for jumps first
            for(int i=0;i<curBoard.length;i++){
                for(int j=0;j<curBoard[i].length;j++){
                    if(board[i][j]=='b' || board[i][j] =='B'){
                        if(blackCanJumpHere(board,i,j,i-2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j-2);
                            removePiece(newBoard.board,i-1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i-2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i-2,j+2);
                            removePiece(newBoard.board,i-1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i+2,j-2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j-2);
                            removePiece(newBoard.board,i+1,j-1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                        if(blackCanJumpHere(board,i,j,i+2,j+2)){
                            MiniCheckers newBoard = movePiece(i,j,i+2,j+2);
                            removePiece(newBoard.board,i+1,j+1);
                            rv.add(newBoard);
                            couldJump = true;
                        }
                    }
                }
            }
            if(!couldJump){
                for(int i=0;i<curBoard.length;i++){
                    for(int j=0;j<curBoard[i].length;j++){
                        if(board[i][j]=='b' || board[i][j]=='B'){
                            if(blackCanMoveHere(board,i,j,i-1,j-1)){
                                rv.add(movePiece(i,j,i-1,j-1));
                            }
                            if(blackCanMoveHere(board,i,j,i-1,j+1)){
                                rv.add(movePiece(i,j,i-1,j+1));
                            }
                            if(blackCanMoveHere(board,i,j,i+1,j-1)){
                                rv.add(movePiece(i,j,i+1,j-1));
                            }
                            if(blackCanMoveHere(board,i,j,i+1,j+1)){
                                rv.add(movePiece(i,j,i+1,j+1));
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("Unrecognized player?!");
        }
        return rv;
    }
}