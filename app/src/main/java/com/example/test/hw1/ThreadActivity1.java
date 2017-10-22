package com.example.test.hw1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ThreadActivity1 extends AppCompatActivity implements StringTaskFragment.TaskCallbacks {

    private StringTaskFragment stringTaskFragment = null;
    private ListView listView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread1);

        progressBar = findViewById(R.id.myBar);
        FragmentManager fm = getSupportFragmentManager();
        final String fragmentTag = "RetainedFragment";
        stringTaskFragment = (StringTaskFragment) fm.findFragmentByTag(fragmentTag);
        listView = findViewById(R.id.list_view);

        if (stringTaskFragment == null) {
            stringTaskFragment = new StringTaskFragment();
            fm.beginTransaction().add(stringTaskFragment, fragmentTag).commit();
        }

        String[] data = stringTaskFragment.getData();
        if (data != null) {
            listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, data));
            progressBar.setVisibility(View.INVISIBLE);
        }
        else
            listView.setAdapter(new ArrayAdapter<String>(this, R.layout.item_list));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!isChangingConfigurations())
            stringTaskFragment.stopTask();
    }

    @Override
    public void onPostExecute(String[] strings) {
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.item_list, strings));
        progressBar.setVisibility(View.INVISIBLE);
    }
}
