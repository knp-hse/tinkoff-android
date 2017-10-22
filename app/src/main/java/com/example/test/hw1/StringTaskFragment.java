package com.example.test.hw1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;


public class StringTaskFragment extends Fragment {

    interface TaskCallbacks {
        void onPostExecute(String[] strings);
    }

    private TaskCallbacks callbacks;
    private String[] data;
    private GetStringsTask task;

    public String[] getData() {
        return data;
    }

    public void stopTask() {
        task.cancel(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (TaskCallbacks) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        task = new GetStringsTask();
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
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled())
                    return null;
            }
            String[] ans = new String[5];
            for (int i = 0; i < 5; i++)
                ans[i] = "This is string " + i;
            if (isCancelled())
                return null;
            return ans;
        }


        @Override
        protected void onPostExecute(String[] strings) {
            if (callbacks != null) {
                data = strings;
                callbacks.onPostExecute(strings);
            }
        }
    }
}
