package com.example.masud.gridviewpractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends Activity {
    GridView gridview;
    GridViewAdapter gridviewAdapter;
    ArrayList<Item> data = new ArrayList<Item>();

    ArrayList<Board> inputBoardList = new ArrayList<Board>();
    Spinner spinner1;
    Button btnSubmit,btnMove;
    //Context context = getApplicationContext();

    int currentBoard=-1;
    Solver s;
    ArrayList<Board> solution;
    int solutionNumber=-1;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMove=(Button) findViewById(R.id.btnMove);
        tv = (TextView) findViewById(R.id.txtMove);
        tv.setTypeface(null, Typeface.BOLD_ITALIC);
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(solutionNumber>=solution.size()){
                    return ;
                }
                gridviewAdapter.clearData();

                data = new ArrayList<Item>();
                Board newBoard=solution.get(solution.size()-1-solutionNumber);
                int size = newBoard.board.length;
                initView(size); // Initialize the GUI Components
                fillData(newBoard.getCopy(newBoard.board)); // Insert The Data
                setDataAdapter();

                solutionNumber++;
            }
        });
        spinner1 = (Spinner) findViewById(R.id.spinnerBoard);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gridviewAdapter.clearData();

                currentBoard=position;
                data = new ArrayList<Item>();
                Board newBoard= inputBoardList.get(position);
                int size = newBoard.board.length;
                initView(size); // Initialize the GUI Components
                fillData(newBoard.getCopy(newBoard.board)); // Insert The Data
                setDataAdapter();


                s=new Solver(newBoard);
                solution = s.solution();
                Board solve = solution.remove(0);
                while(solve!=null)
                {
                    solution.add(solve);
                    solve=solve.getParent();
                }

                solutionNumber=0;
                tv.setText("Nodes Expanded: "+ s.nodesExpanded+"\nMinimum number of moves = " + s.moves());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadInputBoard(getApplicationContext());
        boardDraw();
        //configureBoard();
        //initView(4); // Initialize the GUI Components
        //fillData(4); // Insert The Data
        //setDataAdapter(); // Set the Data Adapter

    }

    // Initialize the GUI Components
    private void initView(int size) {
        gridview = (GridView) findViewById(R.id.gridView);
        /*gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                // Sending image id to FullScreenActivity
                Toast.makeText(getApplicationContext(), "Position" + position, Toast.LENGTH_SHORT).show();
                LinearLayout view = (LinearLayout) v;
                ImageView img = (ImageView) view.getChildAt(0);
                img.setBackgroundColor(Color.YELLOW);
            }
        });*/
        gridview.setNumColumns(size);
    }

    // Insert The Data
    private void fillData(int arr[][]) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                int i1 = arr[i][j];
                String color;
                if (i1 == 1) color = "DKGRAY";
                else if (i1 == 2) color = "RED";
                else if (i1 == 3) color = "GREEN";
                else if (i1 == 4) color = "YELLOW";
                else if (i1 == 5) color = "BLUE";
                else color = "CYAN";
                data.add(new Item(color, arr.length));
            }
        }


    }

    private void fillData(int size) {

        for (int i = 0; i < size * size; i++) {
            Random r = new Random();
            long i1 = ((10 + i) * (100 + i) * r.nextInt()) % 6;


            //long a=System.currentTimeMillis()*101;


            String color;
            if (i1 == 0) color = "DKGRAY";
            else if (i1 == 1) color = "RED";
            else if (i1 == 2) color = "GREEN";
            else if (i1 == 3) color = "YELLOW";
            else if (i1 == 4) color = "BLUE";
            else color = "CYAN";
            data.add(new Item(color, size));
        }

    }

    // Set the Data Adapter
    private void setDataAdapter() {
        gridviewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.row_grid, data);
        gridviewAdapter.notifyDataSetChanged();
        gridview.setAdapter(gridviewAdapter);
        //gridview.setOnItemClickListener(this);
    }


    /*@Override
    public void onItemClick(final AdapterView<?> arg0, final View view, final int position, final long id)
    {
        String message = "Clicked : " + data.get(position).getTitle();
        Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();
    }*/

    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {



    }
    /*public void configureBoard() {


        spinner1 = (Spinner) findViewById(R.id.spinnerBoard);

        TextView tv = (TextView) findViewById(R.id.txtMove);
        tv.setTypeface(null, Typeface.BOLD_ITALIC);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                gridviewAdapter.clearData();
                data = new ArrayList<Item>();
                int size = 0;
                if (spinner1.getSelectedItem().) {
                    size = 4;
                } else if (spinner1.getSelectedItem().toString().contains("Board 2")) {
                    size = 6;
                } else if (spinner1.getSelectedItem().toString().contains("Board 3")) {
                    size = 10;
                } else if (spinner1.getSelectedItem().toString().contains("14*14")) {
                    size = 14;
                }


                initView(size); // Initialize the GUI Components
                fillData(size); // Insert The Data
                setDataAdapter(); // Set the Data Adapter
                /*Toast.makeText(MainActivity.this,
                        "OnClickListener : " +
                                "\nSpinner 1 : " + String.valueOf(spinner1.getSelectedItem()) +
                                "\nSpinner 2 : " + String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }*/

    void loadInputBoard(Context context) {

        String[] array;
        Scanner in = null;
        try {
            //in = new Scanner(new File(getAssets().open(String.format("myFile.txt"))));

            in= new Scanner(getApplicationContext().getAssets().open("input.txt"));
            //in = new Scanner(new File("D:\\GridViewPractice\\app\\src\\main\\java\\com\\example\\masud\\gridviewpractice\\input.txt"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        while (true) {

            int N = in.nextInt();
            if (N == 0) break;


            int[][] colors = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    colors[i][j] = in.nextInt();

            Board board = new Board(colors, null, 0);
            inputBoardList.add(board);
        }

        array= new String[inputBoardList.size()];

        for (int i = 0; i < inputBoardList.size(); i++) {
            array[i] = "Board " + (i + 1);
        }

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, array);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(aa);
    }

    void boardDraw(){

        currentBoard=0;
        data = new ArrayList<Item>();
        Board newBoard= inputBoardList.get(0);
        int size = newBoard.board.length;
        initView(size); // Initialize the GUI Components
        fillData(newBoard.getCopy(newBoard.board)); // Insert The Data
        setDataAdapter();

        s=new Solver(newBoard);
        solution = s.solution();
        Board solve = solution.remove(0);
        while(solve!=null)
        {
            solution.add(solve);
            solve=solve.getParent();
        }
        solutionNumber=0;
    }




}
