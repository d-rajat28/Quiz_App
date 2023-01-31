package com.example.quiz;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;


public class questionDetails_Fragment extends Fragment {
    public static int totalScore;
    private static int current_position;
    private RadioGroup radioGroup;
    private RadioButton option1, option2, option3, option4, selectedAnswer;
    private Button next, previous;
    private TextView question_Title;
    private ToggleButton toggleButton;
    private View myview;
    private questionViewModel viewModel;

// to get the current the position
    public static questionDetails_Fragment newInstance(int position) {
        questionDetails_Fragment fragment = new questionDetails_Fragment();
        current_position = position;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_details_screen_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myview = view;
        question_Title = view.findViewById(R.id.question_heading);
        radioGroup = view.findViewById(R.id.radio_group);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        toggleButton = view.findViewById(R.id.bookmark_toggle);
        next = view.findViewById(R.id.next);
        previous = view.findViewById(R.id.previous);
    // Call viewModel to handel observe the change in the list
        viewModel = new ViewModelProvider(requireActivity()).get(questionViewModel.class);
        viewModel.getQuesListLiveData().observe(getViewLifecycleOwner(), new Observer<List<questionModel>>() {
            @Override
            public void onChanged(List<questionModel> questionModelList) {
                setChange(questionModelList, current_position);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    //changing the current position on click
                        current_position++;
                        setChange(questionModelList, current_position);//set changes in the current position
                    }
                });
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    //changing the current position on click
                        current_position--;
                        setChange(questionModelList, current_position);//set changes in the current position
                    }
                });
            }
        });


    }

    private void setChange(List<questionModel> questionList, int position) {
        radioGroup.clearCheck();//clear previous selected option
        Log.d("pos in detailscreen",String.valueOf(position));
    //To set next and previous button invisible for last and first page
        if (position == questionList.size() - 1) {
            next.setVisibility(View.INVISIBLE);
        } else {
            next.setVisibility(View.VISIBLE);

        }
        if (position == 0) {
            previous.setVisibility(View.INVISIBLE);
        } else {
            previous.setVisibility(View.VISIBLE);

        }
        //To get data for the current position
        questionModel questionModel = questionList.get(position);
        question_Title.setText(questionModel.getQuestion());
        //to set the text of the radio button
        List<String> option = questionModel.getOption();
        option1.setText(option.get(0));
        option2.setText(option.get(1));
        option3.setText(option.get(2));
        option4.setText(option.get(3));
        //to check current status of the bookmarked
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //to set the bookmarked for current position
                    questionModel.setBookMarked_status(true);
                } else {
                    questionModel.setBookMarked_status(false);
                }
            }
        });
    // change the bookmarked according to the bookmarked status
        toggleButton.setChecked(questionModel.isBookMarked_status());

    //to check which radio button is selected
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id != -1) {
                    //set answer status and radio button id if any radio button is selected for current postion
                    questionModel.setAnswer_status(true);
                    questionModel.setRadioButton_id(id);
                    RadioButton selectedAnswer = (RadioButton) myview.findViewById(id);
                    //check the correct answer
                    Log.d("correctAnswer", questionModel.getAnswer());
                    if (questionModel.getAnswer().equals(selectedAnswer.getText())) {
                        //if correct ans not selected till now and it select the correct ans
                        if (questionModel.isCorrect_Ans() == false) {
                            questionModel.setCorrect_Ans(true);//to set true for correct ans
                            totalScore++;//increase the total score
                            Log.d("totalScore", String.valueOf(totalScore));
                        }
                    } else {
                        // if it already selected the correct ans but it change with wrong ans
                        if (questionModel.isCorrect_Ans() == true) {
                            if (questionModel.getAnswer() != (selectedAnswer.getText())) {
                                totalScore--;
                                Log.d("totalScore", String.valueOf(totalScore));
                                questionModel.setCorrect_Ans(false);
                            }
                        }
                    }
                }
            }
        });

        // if previous radio button is selected on change set the radio button
        if (questionModel.getRadioButton_id() != -1) {
            if (questionModel.getRadioButton_id() == option1.getId()) {
                option1.setChecked(true);
            } else if (questionModel.getRadioButton_id() == option2.getId()) {
                option2.setChecked(true);
            } else if (questionModel.getRadioButton_id() == option3.getId()) {
                option3.setChecked(true);
            } else if (questionModel.getRadioButton_id() == option4.getId()) {
                option4.setChecked(true);
            }
        }

    }

}




