package com.example.quiz;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;

public class QuestionList_Adapter extends Adapter<QuestionList_Adapter.QuestionListHolder> {
    private OnItemClickListener mItemClickListener;
    private List<questionModel> questionModelList;

    //adapter constructor
    public QuestionList_Adapter(@NonNull OnItemClickListener listener, List<questionModel> questionListModels) {
        this.mItemClickListener = listener;
        this.questionModelList = questionListModels;

    }

    @NonNull
    @Override
    public QuestionListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_screen, parent, false);
        return new QuestionListHolder(view, mItemClickListener);//call class QuestionListHolder
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListHolder holder, int position) {
        holder.bindView(questionModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return questionModelList.size();
    }

    interface OnItemClickListener {
        void onItemClicked(int position);
    }

    static class QuestionListHolder extends ViewHolder implements View.OnClickListener {
        private QuestionList_Adapter.OnItemClickListener itemClickListener;
        private TextView question_title;
        private TextView answered_status;
        private TextView bookmark_status;

        public QuestionListHolder(View view, OnItemClickListener mItemClickListener) {
            super(view);
            itemClickListener = mItemClickListener;
            view.setOnClickListener(this);
            question_title = view.findViewById(R.id.question_heading);
            answered_status = view.findViewById(R.id.answer_status);
            bookmark_status = view.findViewById(R.id.bookmark_status);

        }
// To set data  to the questionList screen in recyclerView
        public void bindView(questionModel questionModel) {
            question_title.setText(String.valueOf(getBindingAdapterPosition() + 1) + ". " + questionModel.getQuestion());
            if (questionModel.isAnswer_status()) {
        // set the text and color if any answer is selected
                answered_status.setText(R.string.answer);
                answered_status.setTextColor(Color.parseColor("#D2042D"));
            } else {
                answered_status.setText(R.string.unanswered);
            }

            if (questionModel.isBookMarked_status()) {
        // set the text and color if bookmark is selected
                bookmark_status.setText(R.string.bookmarked);
                bookmark_status.setTextColor(Color.parseColor("#D2042D"));
            } else {

                bookmark_status.setText(R.string.not_bookmarked);
            }

        }
// to get current position of the item in recycler view
        @Override
        public void onClick(View view) {
            itemClickListener.onItemClicked(getBindingAdapterPosition());
        }
    }
}
