package com.example.test.hw1;

import android.support.v4.app.FragmentManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ThreadActivity1 extends AppCompatActivity implements TaskFragment.TaskCallbacks {

    private TaskFragment taskFragment = null;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread1);

        FragmentManager fm = getSupportFragmentManager();
        final String fragmentTag = "RetainedFragment";
        taskFragment = (TaskFragment) fm.findFragmentByTag(fragmentTag);

        if (taskFragment == null) {
            taskFragment = new TaskFragment();
            fm.beginTransaction().add(taskFragment, fragmentTag).commit();
        }

        listView = findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list));
    }

    @Override
    public void onPostExecute(String[] strings) {
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list, strings));
    }
}
