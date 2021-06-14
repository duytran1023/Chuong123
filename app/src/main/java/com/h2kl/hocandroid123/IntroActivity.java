package com.h2kl.hocandroid123;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {
    ViewPager viewPager;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        button = findViewById(R.id.btnTieptuc);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, DsBaiHoc.class);
                startActivity(intent);
            }
        });


        SharedPreferences pre = getSharedPreferences("frist_lauch", MODE_PRIVATE);

        boolean frist_lauch = pre.getBoolean("frist_lauch",true);
        final Intent i = getIntent();
        Bundle b = i.getBundleExtra("dulieu");
        if (b != null){
            frist_lauch = b.getBoolean("frist_lauch");
        }
        if (frist_lauch ){
            viewPager = findViewById(R.id.viewpager);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.add(new intro1());
            adapter.add(new intro2());
            adapter.add(new intro2());
            viewPager.setAdapter(adapter);
            SharedPreferences.Editor edit = pre.edit();
            edit.putBoolean("frist_lauch", false);
            edit.commit();

        }
        else{
            Intent trangchu = new Intent(getApplicationContext(), DsBaiHoc.class);
            startActivity(trangchu);
            finish();
        }
    }
}