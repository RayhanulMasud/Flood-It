package com.example.masud.gridviewpractice;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Board {

    private int g;
    public int board[][] = null;
    private Board parent = null;

    //New variables
    ArrayList<Integer> Neighbor = new ArrayList<Integer>();

    //constructor for creating a "board" class object
    public Board(int[][] colors, Board parent, int g) {
        //construct a board from an N-by-N
        //this.board = colors;           //array of colors 
        this.parent = parent;            // (where colors[i][j] = color in              //row i, column j)
        this.g = g;
        board = new int[colors.length][colors[0].length];

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < colors[i].length; j++) {
                board[i][j] = colors[i][j];
            }
        }

    }

    public int f() {
        return g + heuristic3();
    }

    // returns the estimated distance from current board to final state using heuristic1
    //remaining distinct colors in the board
    public int heuristic1() {
        ArrayList<Integer> m = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                m.add(board[i][j]);
            }
        }
        
        Set<Integer> ss = new HashSet<Integer>(m);
        
        return ss.size()-1; // this is  the minimum no of moves required for reaching destination
    }
    
    
    public int heuristic3() {

        Stack s = new Stack();
        ArrayList<Board> list = new ArrayList<Board>();   // contains the possible child board
        ArrayList<position> p = new ArrayList<position>(); //contains visited position

        position o = new position(0, 0); // starting position in board
        s.push(o);
        position cur = new position();

        while (!s.empty()) {

            cur = (position) s.pop(); // type casting

            if (isExist(p, cur)) {
                //System.out.println("Exists");
                continue;
            }

            p.add(cur);

            //upper position
            if (cur.get_x() - 1 >= 0) {
                if (board[cur.get_x() - 1][cur.get_y()] == board[0][0]) {

                    position add = new position(cur.get_x() - 1, cur.get_y());

                    s.push(add);

                }
            }
            //lower position
            if (cur.get_x() + 1 < board.length) {

                if (board[cur.get_x() + 1][cur.get_y()] == board[0][0]) {

                    position add = new position(cur.get_x() + 1, cur.get_y());
                    s.push(add);
                }
            }

            //Left position
            if (cur.get_y() - 1 >= 0) {
                if (board[cur.get_x()][cur.get_y() - 1] == board[0][0]) {
                    position add = new position(cur.get_x(), cur.get_y() - 1);
                    s.push(add);

                } 
            }

            // Right position
            if (cur.get_y() + 1 < board.length) {

                if (board[cur.get_x()][cur.get_y() + 1] == board[0][0]) {
                    position add = new position(cur.get_x(), cur.get_y() + 1);
                    s.push(add);

                }
            }

        }  // while loop end
        
        int nextBoard[][] = getCopy(this.board);
            
        ArrayList<Integer> m = new ArrayList<Integer>();
        position temp;
        for (int i = 0; i < nextBoard.length; i++) {
            for (int j = 0; j < nextBoard[i].length; j++) {
              boolean found=false;
              for(int k=0;k<p.size();k++){
                temp=p.get(k);
                if(temp.get_x()==i && temp.get_y()==j){
                  found = true;
                  break;
                }
              }
              if(found==false){
                m.add(nextBoard[i][j]);
              }
            }
        }
        
        Set<Integer> ss = new HashSet<Integer>(m);
        
        return ss.size(); 
    }

    

    // returns the estimated distance from current board to final state using heuristic2
    // High flooded area means lower h value,so that state needs minimum state to reach goal state
    public int heuristic2() {
        Stack s = new Stack();
        ArrayList<Board> list = new ArrayList<Board>();   // contains the possible child board
        ArrayList<position> p = new ArrayList<position>(); //contains visited position

        position o = new position(0, 0); // starting position in board
        s.push(o);
        position cur = new position();
        
        int max_x=0;
        int max_y=0;
        int max=0;

        while (!s.empty()) {

            cur = (position) s.pop(); // type casting

            if (isExist(p, cur)) {
                //System.out.println("Exists");
                continue;
            }

            p.add(cur);

            //upper position
            if (cur.get_x() - 1 >= 0) {
                if (board[cur.get_x() - 1][cur.get_y()] == board[0][0]) {

                    position add = new position(cur.get_x() - 1, cur.get_y());

                    s.push(add);

                } else {

                    int add = board[cur.get_x() - 1][cur.get_y()];

                    if (!isNeighbor(add)) {
                        Neighbor.add(add);

                    }

                }
            }
            //lower position
            if (cur.get_x() + 1 < board.length) {

                if (board[cur.get_x() + 1][cur.get_y()] == board[0][0]) {

                    position add = new position(cur.get_x() + 1, cur.get_y());
                    s.push(add);
                } else {
                    int add = board[cur.get_x() + 1][cur.get_y()];

                    if (!isNeighbor(add)) {

                        Neighbor.add(add);

                    }

                }
            }

            //Left position
            if (cur.get_y() - 1 >= 0) {
                if (board[cur.get_x()][cur.get_y() - 1] == board[0][0]) {
                    position add = new position(cur.get_x(), cur.get_y() - 1);
                    s.push(add);

                } else {

                    int add = board[cur.get_x()][cur.get_y() - 1];

                    if (!isNeighbor(add)) {

                        Neighbor.add(add);

                    }

                }
            }

            // Right position
            if (cur.get_y() + 1 < board.length) {

                if (board[cur.get_x()][cur.get_y() + 1] == board[0][0]) {
                    position add = new position(cur.get_x(), cur.get_y() + 1);
                    s.push(add);

                } else {

                    int add = board[cur.get_x()][cur.get_y() + 1];

                    if (!isNeighbor(add)) {

                        Neighbor.add(add);

                    }

                }
            }

        }
        
        
         for (int i = 0; i < p.size(); i++) {
                position temp = null;
                temp = p.get(i);
                
                if(temp.get_x()>max_x)
                    max_x=temp.get_x();
                
                if(temp.get_y()>max_y)
                    max_y=temp.get_y();
            }
         if(max_x<max_y)
             max=max_x;
         else
             max=max_y;
        
        
        
       return (this.board.length-max);
    }

    // is this board the goal board? i.e., all color same. 
    public boolean isGoal() {
        int temp = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == 0 && j == 0) {
                    temp = board[i][j]; //color of top-left grid
                }
                if (temp != board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    //get clone of a board
    public int[][] getCopy(int board[][]) {
        int[][] nxtBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                nxtBoard[i][j] = board[i][j];
            }
        }
        return nxtBoard;
    }

    // Finding all neighboring boards ( without DFS )
    public ArrayList<Board> neighbors_01() {
        int init_color = board[0][0];
        int[] track_color = {-1, 0, 0, 0, 0, 0, 0};
        track_color[init_color] = 1;
        int count = 0;
        int mid = 0;

        for (int i = 0; i < board.length; i++) {
            int flag = 0;

            if (count == -1) {
                break;
            }
            if (board[i][0] == init_color) {
                flag = 1;
                count = 0;
                mid = 0;
            }
            for (int j = 0; j < board[i].length; j++) {
                if (mid != 0) {
                    j = mid;
                }

                if ((i == 0 && j == 0) || board[i][j] == init_color) {
                    if (flag == 0 && mid == 0) {
                        mid = j;
                        count = 0;
                    }
                    count++;
                    continue;
                }
                count--;
                if (track_color[board[i][j]] == 0) {
                    track_color[board[i][j]] = 1;
                    if (flag == 1 || mid != 0) {
                        break;
                    }
                }
                if (count == -1) {
                    break;
                }

            }
        }

        return null;
    }

    // does this board equal y?
    public boolean equals(Board y) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (y.board[i][j] != this.board[i][j]) {
                    return false;
                }

            }
        }
        return true;
    }

    public int get_g() {
        return g;
    }

    public Board getParent() {
        return parent;
    }

    // string representation of the
    // board (in the output format specified below)
    public String toString() {
        String str = "\ng: " + g + " h:" + heuristic1() + "\n";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                str += (board[i][j] + " ");
            }
            str += "\n";
        }
        return str;
//  System.out.println();

    }

    // Finding all neighboring nodes ( with DFS approach )
    public ArrayList<Board> neighbors() {

        Stack s = new Stack();
        ArrayList<Board> list = new ArrayList<Board>();   // contains the possible child board
        ArrayList<position> p = new ArrayList<position>(); //contains visited position

        position o = new position(0, 0); // starting position in board
        s.push(o);
        position cur = new position();

        while (!s.empty()) {

            cur = (position) s.pop(); // type casting

            if (isExist(p, cur)) {
                //System.out.println("Exists");
                continue;
            }

            p.add(cur);

            //upper position
            if (cur.get_x() - 1 >= 0) {
                if (board[cur.get_x() - 1][cur.get_y()] == board[0][0]) {

                    position add = new position(cur.get_x() - 1, cur.get_y());

                    s.push(add);

                } else {

                    int add = board[cur.get_x() - 1][cur.get_y()];

                    if (!isNeighbor(add)) {
                        Neighbor.add(add);

                    }

                }
            }
            //lower position
            if (cur.get_x() + 1 < board.length) {

                if (board[cur.get_x() + 1][cur.get_y()] == board[0][0]) {

                    position add = new position(cur.get_x() + 1, cur.get_y());
                    s.push(add);
                } else {
                    int add = board[cur.get_x() + 1][cur.get_y()];

                    if (!isNeighbor(add)) {

                        Neighbor.add(add);

                    }

                }
            }

            //Left position
            if (cur.get_y() - 1 >= 0) {
                if (board[cur.get_x()][cur.get_y() - 1] == board[0][0]) {
                    position add = new position(cur.get_x(), cur.get_y() - 1);
                    s.push(add);

                } else {

                    int add = board[cur.get_x()][cur.get_y() - 1];

                    if (!isNeighbor(add)) {

                        Neighbor.add(add);

                    }

                }
            }

            // Right position
            if (cur.get_y() + 1 < board.length) {

                if (board[cur.get_x()][cur.get_y() + 1] == board[0][0]) {
                    position add = new position(cur.get_x(), cur.get_y() + 1);
                    s.push(add);

                } else {

                    int add = board[cur.get_x()][cur.get_y() + 1];

                    if (!isNeighbor(add)) {

                        Neighbor.add(add);

                    }

                }
            }

        }  // while loop end 

        // now making the neighboring state
        int r;
        while (!Neighbor.isEmpty()) {
            r = Neighbor.remove(0);
            
            int nextBoard[][] = getCopy(this.board);
            

            for (int i = 0; i < p.size(); i++) {
                position temp = null;
                temp = p.get(i);
                nextBoard[temp.get_x()][temp.get_y()] = r;
            }

            nextBoard[0][0] = r;

            /*printing the board
            for (int i = 0; i < nextBoard.length; i++) {
                for (int j = 0; j < nextBoard.length; j++) {
                    System.out.print(nextBoard[i][j] + " ");
                }
                System.out.println();
               
            }*/

            Board n = new Board(nextBoard, this, this.g + 1); //increment g
            list.add(n);

        }

        return list;
    }

    public boolean isNeighbor(int j) {
        for (int i = 0; i < Neighbor.size(); i++) {
            if (Neighbor.get(i) == j) {
                return true;
            }
        }
        return false;
    }

    public boolean isExist(ArrayList<position> p, position cur) {
        for (position i : p) {
            if (i.get_x() == cur.get_x() && i.get_y() == cur.get_y()) {
                return true;
            }
        }

        return false;
    }

// new class for saving the position of board
    public class position {

        private int x;
        private int y;

        public position(int a, int b) {
            this.x = a;
            this.y = b;
        }

        public position() {
            this.x = -1;
            this.y = -1;
        }

        public int get_x() {
            return this.x;
        }

        public int get_y() {
            return this.y;
        }
    }

}
    // for testing purpose
//public static void main(String[] args) 
//{
//}

