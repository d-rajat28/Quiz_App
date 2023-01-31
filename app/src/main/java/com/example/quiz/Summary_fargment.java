package com.example.quiz;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.Locale;


public class Summary_fargment extends Fragment {
    private static final long START_TIME = 600000;
    private static long time_taken;
    private TextView timeTaken;
    private TextView score;
    private Button exit, restart;

    public static Summary_fargment newInstance(long time) {
        Summary_fargment fragment = new Summary_fargment();
        time_taken = time;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.summery_screen_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timeTaken = view.findViewById(R.id.total_time);
        score = view.findViewById(R.id.total_score);
        exit = view.findViewById(R.id.Qexit);
        restart = view.findViewById(R.id.Qrestart);
        //convert to total time taken
        int minutes = (int) ((START_TIME - time_taken) / 1000) / 60;
        int seconds = (int) ((START_TIME - time_taken) / 1000) % 60;
        String totalTime = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timeTaken.setText("Total Time: " + totalTime);
        score.setText("Total Score: " + questionDetails_Fragment.totalScore + "/10");
        Log.d("Total Time",totalTime);
        Log.d("Total Score",String.valueOf(questionDetails_Fragment.totalScore));
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_taken = 0;    //to clear static data
                questionDetails_Fragment.totalScore = 0; ////to clear static data
                System.exit(1);
            }// to exit the app on exit button
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_taken = 0;    //to clear static data
                questionDetails_Fragment.totalScore = 0; ////to clear static data
                Intent intent = new Intent(getContext(), MainActivity.class); //to restart on clicking restart button;
                startActivity(intent);


            }
        });
    }


}
