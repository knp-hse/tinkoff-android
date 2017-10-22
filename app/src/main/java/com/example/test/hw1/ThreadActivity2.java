package com.example.test.hw1;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThreadActivity2 extends AppCompatActivity  implements DuoTaskFragment.TaskCallbacks {

    private DuoTaskFragment duoTaskFragment = null;
    private ProgressBar progressBar;
    private TextView text1, text2;
    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread2);

        text1 = findViewById(R.id.asyncTextView);
        text2 = findViewById(R.id.asyncTextView2);

        progressBar = findViewById(R.id.myBar);
        FragmentManager fm = getSupportFragmentManager();
        final String fragmentTag = "RetainedFragment";
        duoTaskFragment = (DuoTaskFragment) fm.findFragmentByTag(fragmentTag);

        if (duoTaskFragment == null) {
            duoTaskFragment = new DuoTaskFragment();
            fm.beginTransaction().add(duoTaskFragment, fragmentTag).commit();
        }



        String str = duoTaskFragment.getStr1();
        if (str != null) {
            num++;
            text1.setText(str);
            str = duoTaskFragment.getStr2();
            if (str != null) {
                progressBar.setVisibility(View.INVISIBLE);
                text2.setText(str);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!isChangingConfigurations())
            duoTaskFragment.stopTask();
    }

    @Override
    public void onProgressUpdate(String res) {
        if (res == null)
            return;
        if (num ==  1) {
            num++;
            text1.setText(res);
        }
        else {
            text2.setText(res);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
