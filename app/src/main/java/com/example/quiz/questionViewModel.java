package com.example.quiz;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class questionViewModel extends AndroidViewModel implements Response.Listener<String>, Response.ErrorListener {
    private static final String URL = "https://gist.githubusercontent.com/Rajat-Deb/4d9de4063be2f92b3e0559a10bdcd877/raw/d733eb3b1247b9e2380c78bbe68d82428cdcac32/question.json";
    private static final String ENTRY_KEY = "questions";
    private static final String QUESTION_KEY = "question";
    private static final String OPTION_KEY = "options";
    private static final String CORRECT_ANSWER_KEY = "correct_option";

    private final RequestQueue queue;
// mutable data
    private final MutableLiveData<List<questionModel>> quesListLiveData = new MutableLiveData<>();
    private final MutableLiveData<RequestStatus> requestStatusLiveData = new MutableLiveData<>();


    public questionViewModel(@NonNull Application application) {
        super(application);
        // new volley requested
        queue = Volley.newRequestQueue(application);
        requestStatusLiveData.postValue(RequestStatus.IN_PROGRESS);
        fetchQuestion();
    }


    public LiveData<List<questionModel>> getQuesListLiveData() {
        return quesListLiveData;
    }

    public LiveData<RequestStatus> getRequestStatusLiveData() {
        return requestStatusLiveData;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        requestStatusLiveData.postValue(RequestStatus.FAILED);
    }

    @Override
    public void onResponse(String response) {
        try {
            List<questionModel> questionModelList = parseResponse(response);
            quesListLiveData.postValue(questionModelList);
            requestStatusLiveData.postValue(RequestStatus.SUCCEEDED);
        } catch (JSONException e) {
            e.printStackTrace();
            requestStatusLiveData.postValue(RequestStatus.FAILED);
        }
    }

    //request to fetch data
    private void fetchQuestion() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, this, this);
        queue.add(stringRequest);
    }

    //to fetch data from json
    private List<questionModel> parseResponse(String response) throws JSONException {
        Log.d("response string", "response");
        List<questionModel> questionListModels = new ArrayList<>();
        JSONObject res = new JSONObject(response);
        JSONArray entries = res.optJSONArray(ENTRY_KEY);
        if (entries == null) {
            return questionListModels;
        }
        for (int i = 0; i < entries.length(); i++) {
            Log.d("###", "inside json");
            JSONObject obj = (JSONObject) entries.get(i);
            String Question_Title = obj.optString(QUESTION_KEY);
            int Answer = obj.getInt(CORRECT_ANSWER_KEY);
            // to get the option in a list
            JSONArray Option_arr = obj.optJSONArray(OPTION_KEY);
            List<String> option = new ArrayList<>();
            for (int j = 0; j < Option_arr.length(); j++) {
                option.add(Option_arr.getString(j));
            }
            Log.d("option list",String.valueOf(option));
            String answer = option.get(Answer);
            Log.d("correctAnswer",answer);
            questionModel model = new questionModel(Question_Title, option, answer, false, false);
            questionListModels.add(model);
        }
        Collections.shuffle(questionListModels);//to shuffle the question list
        return questionListModels;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public enum RequestStatus {
        /* Show API is in progress. */
        IN_PROGRESS,
        /* Show API request is failed. */
        FAILED,
        /* Show API request is successfully completed. */
        SUCCEEDED
    }

}
