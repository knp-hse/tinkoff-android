package com.example.test.hw1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by dank on 22/10/17.
 */

public class DuoTaskFragment extends Fragment {
    interface TaskCallbacks {
        void onProgressUpdate(String res);
    }

    private GetTwoStringsTask task;
    private TaskCallbacks callbacks;
    private String str1;
    private String str2;
    private int num = 0;

    public String getStr1() {
        return str1;
    }

    public String getStr2() {
        return str2;
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

        task = new GetTwoStringsTask();
        task.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }


    private class GetTwoStringsTask extends AsyncTask<Void, String, Void> {
        @Override
        protected Void doInBackground(Void... ignore) {
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled())
                    return null;
            }
            publishProgress("Data 1");
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCancelled())
                    return null;
            }
            publishProgress("Data 2");
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            callbacks.onProgressUpdate(values[0]);
            num++;
            if (num == 1)
                str1 = values[0];
            else
                str2 = values[0];
        }
    }
}
