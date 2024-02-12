/*
 * Names: Alex Shapiro
 * netID: ashapi,
 * G#: 01370758
 * Lecture section: 001
 * Lab section: 202
 */

import java.util.Scanner;

public class PVPGame {
    public static void main(String[] args) {
        // Set up a scanner for user input
        Scanner s = new Scanner(System.in);

        // Create two human players (Red and Black) for the game
        Player p1 = new UserPlayer(s, "Red");
        Player p2 = new UserPlayer(s, "Black");

        // Create a MiniCheckers board with the two players
        MiniCheckers board = new MiniCheckers(p1, p2);

        // Game loop: Continue until there's a winner or no possible moves
        while (!board.checkWin(p1) && !board.checkWin(p2) && board.possibleMoves(p1).size() > 0) {
            // Alternate turns between players
            board = (p1 == p2) ? p1.chooseMove(board) : p2.chooseMove(board);
            
            // Swap players for the next turn
            Player tmp = p1;
            p1 = p2;
            p2 = tmp;
        }

        // Determine the outcome of the game and display the result
        if (board.checkWin(p1)) {
            System.out.println("Player [" + p1 + "] wins!");
        } else if (board.checkWin(p2)) {
            System.out.println("Player [" + p2 + "] wins!");
        } else {
            System.out.println("Draw?");
        }

        // Display the final game board
        System.out.println("Final game board:");
        System.out.println(board);		
    }
}
