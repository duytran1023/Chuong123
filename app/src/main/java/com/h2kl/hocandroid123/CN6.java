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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CN6 extends AppCompatActivity {
    WebView webView;
    ArrayList<baihoc> BaiHoc;
    Button btn_baitruoc, btn_baisau;
    int current=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_n6);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        BaiHoc =  new ArrayList<>();

        String json = LoadData("danhsachbaihoc.json");
        parseJSON(json);


        Intent intent = getIntent();
        Bundle b=intent.getBundleExtra("baihoc");
        if(b !=null) {
            current=b.getInt("mabaihoc");
        }

        String maBaiHoc = BaiHoc.get(current).mabaihoc;
        btn_baitruoc = findViewById(R.id.btn_baitruoc);
        btn_baisau = findViewById(R.id.btn_baisau);

        if(current==0){
            btn_baitruoc.setEnabled(false);
        }
        if(current==BaiHoc.size()-1){
            btn_baisau.setEnabled(false);
        }
        btn_baitruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(CN6.this, CN6.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("mabaihoc", current-1);
                    i.putExtra("baihoc",bundle);
                    startActivity(i);


            }
        });


        btn_baisau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(CN6.this, CN6.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("mabaihoc", current+1);
                    i.putExtra("baihoc",bundle);
                    startActivity(i);
            }
        });
        // xem bai viet

        webView = findViewById(R.id.ww_noidung);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/"+maBaiHoc+".html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // lưu tiến trình
        SharedPreferences pre = getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor edit = pre.edit();
        edit.putInt("Tientrinh", current);
        edit.commit();

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
    private void parseJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<baihoc>>(){}.getType();
        List<baihoc> dsbaihoc = gson.fromJson(jsonString, type);
        for (baihoc BH : dsbaihoc){
            BaiHoc.add(new baihoc(BH.mabaihoc, BH.tenbaihoc, BH.chuong));
        }
    }
}