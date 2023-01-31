package com.example.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class common_fragment extends Fragment {
    private questionList_Fragment questionListFragment;
    private timer_fragment timerFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //to call questionList screen and timer screen
        if (savedInstanceState == null) {
            questionListFragment = questionList_Fragment.newInstance();
            timerFragment = timer_fragment.newInstance();
            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.Q_A_frame, questionListFragment, "questionListFrame");
            fragmentTransaction.replace(R.id.timeframe, timerFragment, "timerFragment");
            fragmentTransaction.commit();
        }
    }
}

