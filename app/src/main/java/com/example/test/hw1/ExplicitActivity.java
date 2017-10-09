package com.example.test.hw1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExplicitActivity extends AppCompatActivity {
    private static final int REQ_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);

        final EditText editText = findViewById(R.id.editTextExp);

        final Button shareButton = findViewById(R.id.button_share_exp);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent(getApplicationContext(), ApproveActivity.class);
                sendIntent.putExtra("myText", editText.getText().toString());
                startActivityForResult(sendIntent, REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            Context context = getApplicationContext();
            CharSequence text;
            int duration = Toast.LENGTH_SHORT;

            if (resultCode == RESULT_OK) {
                text  = "Result: OK!";
            } else {
                text = "Result: Canceled!";
            }

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
