package com.example.test.hw1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;


public class TaskFragment extends Fragment {

    interface TaskCallbacks {
        void onPostExecute(String[] strings);
    }

    private TaskCallbacks callbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (TaskCallbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        GetStringsTask task = new GetStringsTask();
        task.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }


    private class GetStringsTask extends AsyncTask<Void, Integer, String[]> {

        @Override
        protected String[] doInBackground(Void... ignore) {
            SystemClock.sleep(5000);
            String[] ans = new String[5];
            for (int i = 0; i < 5; i++)
                ans[i] = "This is string " + i;
            return ans;
        }


        @Override
        protected void onPostExecute(String[] strings) {
            if (callbacks != null) {
                callbacks.onPostExecute(strings);
            }
        }
    }
}
