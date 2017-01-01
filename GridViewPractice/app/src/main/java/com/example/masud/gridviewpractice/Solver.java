package com.example.masud.gridviewpractice;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/*
Main function takes, the input file, constructs a  Solver object. 
In the constructor solve the initial board with A* search.
Save the number of minimum moves and  sequence of solution in member variables.
The functions solution() and moves() return these member functions.
 */

/**/
public class Solver 
{
       //public int size = 0;

 ArrayList<Board> solution; //stores the sequence of boards upto solution
 int minMove; //the number of moves
 
 
 class MyComparator implements Comparator<Board>
 {

  @Override
  public int compare(Board o1, Board o2) {
   // TODO Auto-generated method stub
   return o1.f()-o2.f();
  }
  
 }
 public int nodesExpanded=0;
 public Solver(Board initial) // find a solution to the initial
 {        // board (using the A* algorithm)

  PriorityQueue<Board> PQ = new PriorityQueue<>(10, new MyComparator());
  
  //insert initial node into a priority queue PQ
  PQ.add(initial);
  //System.out.println("Root: "+initial);
  //PQ always pops the node with lowest f(s)
  while (!PQ.isEmpty())
  {
   nodesExpanded++;
   Board node = PQ.poll();
   //System.out.println("Pop: "+node);
   
   if(node.isGoal())
   {
    minMove = node.get_g();
    //System.out.println("Goal: "+node.toString());
    solution = new ArrayList<Board>();
                                        solution.add(node);
    //populate solution arraylist with the board sequence from initial to goal
    return;
   }
   
   ArrayList<Board> neighbors = node.neighbors();
   
   for(int i=0;i<neighbors.size();i++)
   {
    PQ.add(neighbors.get(i));
    //System.out.println(neighbors.get(i));
   }

  }

 }

 // Returns the minimum number of moves to solve
 public int moves()     
 {
  return minMove;
 }
 // sequence of boards in the
 // shortest solution
 public ArrayList<Board> solution() 
 {  
  return solution;
 }
 public void returnSolution(int [][] colors)
 {

   Board initial = new Board(colors,null,0);
   //System.out.println(initial.toString());


   Solver solver = new Solver(initial);
   //System.out.println("Nodes Expanded: "+ solver.nodesExpanded);
   //System.out.println("Minimum number of moves = " + solver.moves());
   ArrayList<Board> solution = solver.solution();
                        Board solve = solution.remove(0);
                        while(solve!=null)
                        {
                            solution.add(solve);
                            solve=solve.getParent();
                        }
   //for (int i=solution.size()-1;i>=0;i--)
    //System.out.println(solution.get(i));

   //for bonus - show_result_in_gui(solution);

  }
 }
 


