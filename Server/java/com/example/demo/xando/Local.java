package com.example.demo.xando;

import com.example.demo.models.Game;
import com.example.demo.models.User;


public class Local {
    Game game;
    Board board;
    User turn;
    User player1;
    User player2;
    int lastMoveX = -1;
    int lastMoveY = -1;
    boolean weHaveAWinner = false;

    public Local(Game game, Board board) {
        this.game = game;
        this.board = board;
    }

    public boolean submitMove(int x, int y, int symbol){
        System.out.println("Sunt board si ultima mutare o am la " + x + "_" + y);
        lastMoveX = x;
        lastMoveY = y;
        return board.validMove(x, y,symbol);
    }

    public boolean checkOver(){
        for (int i = 1; i < board.getBoard().length; i++) {
            for (int j = 1; j < board.getBoard()[0].length; j++) {
                if( (board.horizontalCheck(i,j) || board.leftobliqueCheck(i,j) || board.rightobliqueCheck(i,j) || board.verticalCheck(i,j))  && board.getBoard()[i][j]!=0) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void changeTurn(User you) {
        if( you == player1 ) {
            turn = player2;
        }
        else
            turn = player1;
        notifyAll();
    }

    public void setTurn(User turn) {
        this.turn = turn;
    }

    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    public void setPlayer1(User player1) {
        this.player1 = player1;
    }
}
