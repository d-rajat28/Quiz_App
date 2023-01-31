package com.example.quiz;


import android.os.CountDownTimer;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Locale;

public class timer_viewModel extends ViewModel {
    private static final long START_TIME_IN_MILLIS = 600000;
    public long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;
    private CountDownTimer mCountDownTimer;
    private MutableLiveData<String> time = new MutableLiveData<>(); //Live Data to save time text view


    public MutableLiveData<String> getTime() {
        return time;
    }

    void updateCounter(long millisUntilFinished) {
        mTimeLeftInMillis = millisUntilFinished;
    }

    //Method for start the countdown timer
    void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateCounter(millisUntilFinished);
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                // to call summary fragment when time is finish
                timer_fragment.fragment.changefragment();
            }
        }.start();

    }

    //Method to update the textView
    void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        set_Text(timeLeftFormatted);
    }


    public void set_Text(String text) {
        time.setValue(text);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

