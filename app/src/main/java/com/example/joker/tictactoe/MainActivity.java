package com.example.joker.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //yellow = 0 and red = 1
    private int activePlayer = 0;

    //to check the state of the game 2 = available
    private int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    //winning positions
    private int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    //game active status
    private boolean gameActive = true;


    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = findViewById(R.id.lineraLayout);
        linearLayout.setVisibility(View.INVISIBLE);

    }

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int state = Integer.parseInt(counter.getTag().toString());

        //check for game state
        if (gameState[state] == 2) {

            counter.setTranslationY(-1000f);

            //check for active player
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
                gameState[state] = 0;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
                gameState[state] = 1;
            }

            counter.animate().translationYBy(1000f).setDuration(300);
            counter.animate().rotation(180).setDuration(500);


            //check for winner
            for (int[] winningPosition : winningPositions) {

                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2 &&
                        gameActive) {

                    gameActive = false;

                    linearLayout.setVisibility(View.VISIBLE);
                    TextView winnMessage = findViewById(R.id.winMessage);

                    if (gameState[winningPosition[0]] == 0) {
                        winnMessage.setText(R.string.winner_yellow);
                    } else {
                        winnMessage.setText(R.string.winner_red);
                    }

                }else{

                    int tieState = 1;
                    for (int i = 0 ;i <gameState.length ; i++){

                        if(gameState[i] == 2){
                            tieState = 0;
                        }
                    }

                    if (tieState == 1){
                        linearLayout.setVisibility(View.VISIBLE);
                        TextView winnMessage = findViewById(R.id.winMessage);
                        winnMessage.setText("It's a Tie");
                        gameActive = false;
                    }

                }

            }

        }

    }

    private void makeToast(String s) {

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    public void tryAgain(View view) {

        if (!gameActive) {

            activePlayer = 0;

            for (int i = 0; i < gameState.length; i++) {
                gameState[i] = 2;
            }

            GridLayout gridLayout = findViewById(R.id.gridLayout);

            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
            }

            linearLayout.setVisibility(View.INVISIBLE);
            gameActive = true;
        }

    }

}

