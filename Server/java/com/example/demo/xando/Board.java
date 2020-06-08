package com.example.demo.xando;

public class Board {
    boolean first_time = true;
    private int [][] board;

    public Board() {
        this.board = new int[4][4];
    }

    public boolean validMove(int x, int y ,int symbol){
        if(board[x][y] != 0)
            return false;
        board[x][y] = symbol;
        System.out.println("La board adaug " + board[x][y]);
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean horizontalCheck(int x, int y){
        if(y+2 >= board[0].length)
            return false;
        for (int i = 1; i < 3; i++) {
            if(board[x][y] != board[x][y+i])
                return false;
        }
        return true;
    }

    public boolean rightobliqueCheck(int x, int y){
        if(y+2 >= board[0].length || x+2 >= board.length)
            return false;
        for (int i = 1; i < 3; i++) {
            if(board[x][y] != board[x+i][y+i])
                return false;
        }
        return true;
    }

    public boolean leftobliqueCheck(int x, int y){
        if(y-2 >= board[0].length || x-2 >= board.length || y-2 < 0 || x-2 < 0)
            return false;
        for (int i = 1; i < 3; i++) {
            if(board[x][y] != board[x-i][y-i])
                return false;
        }
        return true;
    }

    public boolean verticalCheck(int x, int y){
        if(x+2 >= board.length)
            return false;
        for (int i = 1; i < 3; i++) {
            if(board[x][y] != board[x+i][y])
                return false;
        }
        return true;
    }
}
