package com.example.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class timer_fragment extends Fragment implements LifecycleOwner {
    public static timer_fragment fragment;
    private TextView timer_text;
    private Button submit_btn;
    private timer_viewModel viewModel;
    private Summary_fargment summary_fargment;

    public static timer_fragment newInstance() {
        fragment = new timer_fragment();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer_submit_frame, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timer_text = view.findViewById(R.id.timer_textView);
        submit_btn = view.findViewById(R.id.submit_button);
        //to call timer view model and observe change
        viewModel = new ViewModelProvider(getActivity()).get(timer_viewModel.class);
        viewModel.getTime().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                //to set the timer text
                Log.d("time",s);
                timer_text.setText(s);
            }
        });
        //update the timer
        viewModel.updateCountDownText();

    // on clicking submit button open alert dialog box
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });
    }

    private void alertDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Submit");
        dialog.setMessage("Are you sure you want to submit the quiz");
        dialog.setPositiveButton("OK", (dialogInterface, i) -> {
            changefragment();//function to change the fragment on clicking ok
        });
        dialog.setNegativeButton("cancel", (dialogInterface, i) -> {
            dialogInterface.dismiss();

        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }
//implementation on changefragment to go to summary screen
    public void changefragment() {
        summary_fargment = Summary_fargment.newInstance(viewModel.mTimeLeftInMillis);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, summary_fargment);
        fragmentTransaction.commit();
    }
}
