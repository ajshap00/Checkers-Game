/*
 * Names: Alex Shapiro
 * netID: ashapi,
 * G#: 01370758
 * Lecture section: 001
 * Lab section: 202
 */

import java.util.ArrayList;
import java.util.Scanner;

public class UserPlayer extends Player {
    // Player's name and input scanner
    private String name;
    private Scanner input;

    // Constructor to initialize UserPlayer with a name and input scanner
    public UserPlayer(Scanner input, String name) {
        this.input = input;
        this.name = name;
    }

    // Override toString to provide a string representation of the player
    @Override
    public String toString() {
        return name;
    }

    // Override chooseMove method to allow the user to choose a move interactively
    @Override
    public MiniCheckers chooseMove(MiniCheckers currentBoard) {
        // Display the current state of the board
        System.out.println("Current Board:");
        System.out.println(currentBoard);
        
        // Get the possible moves for the current player
        ArrayList<MiniCheckers> possibleMoves = currentBoard.possibleMoves(this);
        
        // Display the available move options
        System.out.println("Possible Moves:");
        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println("Option " + (i + 1) + ":");
            System.out.println(possibleMoves.get(i));
        }

        // Prompt the user to enter the index of the move they want to make
        System.out.println("Enter the index of the move you want to make: ");
        int selectedIndex = input.nextInt();

        // Return the selected board based on the user's choice
        return possibleMoves.get(selectedIndex - 1);
    }
}
