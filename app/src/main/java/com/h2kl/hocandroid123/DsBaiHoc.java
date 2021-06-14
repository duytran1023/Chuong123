package com.h2kl.hocandroid123;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DsBaiHoc extends AppCompatActivity {
    ProgressBar pgbTienTrinh;
    ArrayList<baihoc> BaiHoc;
    ListView lviewbai;
    Button btnTiepTuc;
    TextView txttientrinh;
    int thutubai = 0;
    SharedPreferences pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_bai_hoc);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



        pgbTienTrinh = (ProgressBar) findViewById(R.id.pgbTienTrinh);
        btnTiepTuc = (Button) findViewById(R.id.btnTiepTuc);
        txttientrinh = (TextView) findViewById(R.id.txttientrinh);
        lviewbai = (ListView) findViewById(R.id.lviewbai);
        pre = getSharedPreferences("myPrefs", MODE_PRIVATE);
        thutubai = pre.getInt("Tientrinh",0);


        BaiHoc= new ArrayList<>();
        String json = LoadData("danhsachbaihoc.json");
        parseJSON(json);

        AdapterBaiHoc arrayAdapter = new AdapterBaiHoc(this, R.layout.layoutbaihoc, BaiHoc);
        lviewbai.setAdapter(arrayAdapter);
        lviewbai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!BaiHoc.get(position).chuong.equals("true")){
                    Intent intent= new Intent(DsBaiHoc.this, CN6.class);
                    Bundle bundle= new Bundle();
                    bundle.putInt("mabaihoc",position);
                    intent.putExtra("baihoc",bundle);
                    startActivity(intent);
                }

            }
        });



        if(thutubai>0){
            txttientrinh.setText("Bạn đã học tới bài "+ BaiHoc.get(thutubai).tenbaihoc);
            btnTiepTuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DsBaiHoc.this, CN6.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("mabaihoc", thutubai);
                    i.putExtra("baihoc",bundle);
                    startActivity(i);
                }
            });

        }
        else{
            txttientrinh.setText("Bắt đầu học ");
            pgbTienTrinh.setVisibility(View.INVISIBLE);
            btnTiepTuc.setText("Bắt đầu");
            btnTiepTuc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DsBaiHoc.this, CN6.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("mabaihoc", 0);
                    i.putExtra("baihoc",bundle);
                    startActivity(i);
                }
            });

        }
    }


    @Override
    protected void onResume() {
        updateProgressbar();
        super.onResume();
    }

    //update progesbar
    public void updateProgressbar(){
        thutubai = pre.getInt("Tientrinh",0);
        float stt = (float) thutubai;
        if (BaiHoc.size() > 0){
            int sz = BaiHoc.size();
            float pr = stt/sz;
            float prv = pr*100;
            int proc = (int) prv + 1;
            pgbTienTrinh.setProgress(proc);
        }
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


    private void parseJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<baihoc>>(){}.getType();
        List<baihoc> dsbaihoc = gson.fromJson(jsonString, type);
        for (baihoc BH : dsbaihoc){
            BaiHoc.add(new baihoc(BH.mabaihoc, BH.tenbaihoc, BH.chuong));
        }
    }

    public String LoadData(String inFile) {
        String tContents = "";
        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

}