package com.h2kl.hocandroid123;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TextViewExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view_example);

        findViewById(R.id.btnTrolai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}