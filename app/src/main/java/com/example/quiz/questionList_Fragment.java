package com.example.quiz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz.questionViewModel.RequestStatus;

import java.util.List;

public class questionList_Fragment extends Fragment implements LifecycleOwner, QuestionList_Adapter.OnItemClickListener {
    private questionViewModel View_Model;
    private questionDetails_Fragment questionDetails_fragment;
    private RecyclerView recyclerView;
    private ProgressDialog loadingDialog;
    private QuestionList_Adapter mAdapter;
    private timer_viewModel timer_viewModel;

    public static questionList_Fragment newInstance() {
        questionList_Fragment fragment = new questionList_Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.questions_list_recyclerview_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        timer_viewModel = new ViewModelProvider(requireActivity()).get(timer_viewModel.class);
        View_Model = new ViewModelProvider(requireActivity()).get(questionViewModel.class);
        //call mutable livedata get method to observe the changes
        View_Model.getQuesListLiveData().observe(getViewLifecycleOwner(), questionListModels -> handleChange(questionListModels));
        View_Model.getRequestStatusLiveData().observe(getViewLifecycleOwner(), requestStatus -> handleRequestStatus(requestStatus));

    }

    private void handleRequestStatus(RequestStatus requestStatus) {
        switch (requestStatus) {
            case IN_PROGRESS:
                showSpinner();
                break;
            case SUCCEEDED:
                hideSpinner();
                break;
            case FAILED:
                showError();
                break;
        }
    }

    // to handle changes in the recycler view
    private void handleChange(List<questionModel> questionListModels) {
        mAdapter = new QuestionList_Adapter(this, questionListModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    private void showSpinner() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getContext());
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setTitle("Fetching all the Question");
            loadingDialog.setMessage("Please wait...");
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }

    ////To hide processes dialog
    private void hideSpinner() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    //on success of fetching the data start the timer
        timer_viewModel.startTimer();
    }

    ////To show message on fail on loading
    private void showError() {
        hideSpinner();
        Toast.makeText(getContext(), "Check your Network Connection", Toast.LENGTH_LONG).show();

    }

    // on clicking any item in the recycler view call the details fragment screen
    @Override
    public void onItemClicked(int position) {
        Log.d("current position", String.valueOf(position));
        questionDetails_fragment = questionDetails_Fragment.newInstance(position);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Q_A_frame, questionDetails_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
