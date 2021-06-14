package com.h2kl.hocandroid123;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class NhacNhoHocMainActivity extends AppCompatActivity {
    Button button;
    TimePicker timePicker;
    SharedPreferences pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhac_nho_hoc_activity_main);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        pre = getSharedPreferences("time_remind", MODE_PRIVATE);
        button = findViewById(R.id.simple_notification);

        timePicker = findViewById(R.id.time_picker);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, pre.getInt("hour", 0));
                calendar.set(Calendar.MINUTE, pre.getInt("minute", 0));
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                Date dateSchedule = calendar.getTime();
                long period = 24 * 60 * 60 * 1000;

                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Intent service = new Intent(getApplicationContext(), guithongbao.class);
                        startService(service);
                    }
                };

                Timer timer = new Timer();
                timer.schedule(timerTask, dateSchedule, period);
                Toast.makeText(NhacNhoHocMainActivity.this, "Đã lưu", Toast.LENGTH_LONG).show();
            }
        });

            timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute){
                    SharedPreferences.Editor edit = pre.edit();
                    edit.putInt("hour",hourOfDay);
                    edit.putInt("minute",minute);
                    edit.commit();
                }
            });
    }


    //tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intentOptionMenu;
        switch (item.getItemId()){
            case R.id.nav_home:
                intentOptionMenu = new Intent(this, DsBaiHoc.class);
                startActivity(intentOptionMenu);
                return true;
            case R.id.nav_example:
                intentOptionMenu = new Intent(this, DsCodeMau.class);
                startActivity(intentOptionMenu);
                return true;
            case R.id.nav_remind:
                intentOptionMenu = new Intent(this, NhacNhoHocMainActivity.class);
                startActivity(intentOptionMenu);
                return true;
            case R.id.nav_quiz:
                intentOptionMenu = new Intent(this, QuestionActivity.class);
                startActivity(intentOptionMenu);
                return true;
            case R.id.nav_about:
                intentOptionMenu = new Intent(this, IntroActivity.class);
                Bundle bundleOptionMenu = new Bundle();
                bundleOptionMenu.putBoolean("frist_lauch", true);
                intentOptionMenu.putExtra("dulieu", bundleOptionMenu);
                startActivity(intentOptionMenu);
                return true;
            case R.id.nav_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //./ tao menu

}