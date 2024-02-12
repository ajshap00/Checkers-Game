/*
* Names: Alex Shapiro
* netID: ashapi,
* G#: 01370758
* Lecture section: 001
* Lab section: 202
*/

import java.util.ArrayList;

public class AIPlayer extends Player {
    private String name;
    private Player opponent;

    public AIPlayer(String name, Player opponent) {
        this.name = name;
        this.opponent = opponent;
    }

    @Override
    public String toString() {
        return name + " (AI)";
    }

    @Override
    public MiniCheckers chooseMove(MiniCheckers currentBoard) {
        ArrayList<MiniCheckers> possibleMoves = currentBoard.possibleMoves(this);
        double maxValue = Double.NEGATIVE_INFINITY;
        MiniCheckers bestMove = possibleMoves.get(0);

        // Initial depth for the recursive process
        int depth = 10;

        for (MiniCheckers move : possibleMoves) {
            double value = minValue(move, depth - 1);
            if (value > maxValue) {
                maxValue = value;
                bestMove = move;
            }
        }

        return bestMove;
    }

    @Override
    public double boardValue(MiniCheckers currentBoard) {
        return maxValue(currentBoard, 10);
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    private double minValue(MiniCheckers game, int depth) {
        if (game.checkWin(this)) {
            return 10.0;
        } else if (game.checkLose(this)) {
            return -10.0;
        } else if (depth < 1) {
            return relativeStrength(game);
        }

        double minValue = Double.POSITIVE_INFINITY;
        ArrayList<MiniCheckers> possibleMoves = game.possibleMoves(opponent);

        for (MiniCheckers move : possibleMoves) {
            minValue = Math.min(minValue, maxValue(move, depth - 1));
        }

        return minValue;
    }

    private double maxValue(MiniCheckers game, int depth) {
        if (game.checkWin(this)) {
            return 10.0;
        } else if (game.checkLose(this)) {
            return -10.0;
        } else if (depth < 1) {
            return relativeStrength(game);
        }

        double maxValue = Double.NEGATIVE_INFINITY;
        ArrayList<MiniCheckers> possibleMoves = game.possibleMoves(this);

        for (MiniCheckers move : possibleMoves) {
            maxValue = Math.max(maxValue, minValue(move, depth - 1));
        }

        return maxValue;
    }

    private double relativeStrength(MiniCheckers game) {
    }
}
