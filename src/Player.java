/*
* Names: Alex Shapiro
* netID: ashapi,
* G#: 01370758
* Lecture section: 001
* Lab section: 202
*/

public abstract class Player {
    // Abstract method for choosing a move
    public abstract MiniCheckers chooseMove(MiniCheckers currentBoard);

    // Default implementation for boardValue
    public double boardValue(MiniCheckers currentBoard) {
        if (currentBoard.checkWin(this)) {
            return 1.0;
        } else if (currentBoard.checkLose(this)) {
            return -1.0;
        } else {
            return 0.0;
        }
    }
}

