package com.h2kl.hocandroid123;

import com.h2kl.hocandroid123.example.*;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XemcodemauActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;
    WebView webView;
    ArrayList<codemau> DanhSachCodeMau;
    int current=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemcodemau);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        DanhSachCodeMau = new ArrayList<>();
        String json = LoadData("danhsachcodemau.json");
        parseJSON(json);

        Intent intent = getIntent();
        Bundle b=intent.getBundleExtra("codemau");
        if(b !=null) {
            current = b.getInt("macodemau");
        }
        String maCode = DanhSachCodeMau.get(current).macodemau;

        bottomNavigationView = findViewById(R.id.BotNav);
        frameLayout = findViewById(R.id.FrXemcode);
        webView = findViewById(R.id.WvXemcode);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/xml/"+ DanhSachCodeMau.get(current).macodemau +".html");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        frameLayout.setBackgroundResource(R.color.colorPrimary);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.itemXML:
                    webView.loadUrl("file:///android_asset/xml/"+ DanhSachCodeMau.get(current).macodemau +".html");
                    break;
                case R.id.itemJAVA:
                    webView.loadUrl("file:///android_asset/java/"+ DanhSachCodeMau.get(current).macodemau +".html");
                    break;
                case R.id.itemRUN:
                    Intent itt;
                    switch (current){
                        case 0:
                            itt = new Intent(XemcodemauActivity.this, textview.class);
                            break;
                        case 1:
                            itt = new Intent(XemcodemauActivity.this, edittext.class);
                            break;
                        case 2:
                            itt = new Intent(XemcodemauActivity.this, button.class);
                            break;
                        case 3:
                            itt = new Intent(XemcodemauActivity.this, imagebutton.class);
                            break;
                        case 4:
                            itt = new Intent(XemcodemauActivity.this, checkbox.class);
                            break;
                        case 5:
                            itt = new Intent(XemcodemauActivity.this, togglebutton.class);
                            break;
                        case 6:
                            itt = new Intent(XemcodemauActivity.this, progressbar.class);
                            break;
                        case 7:
                            itt = new Intent(XemcodemauActivity.this, timepicker.class);
                            break;
                        case 8:
                            itt = new Intent(XemcodemauActivity.this, datepicker.class);
                        break;
                        default:
                            itt = new Intent(XemcodemauActivity.this, textview.class);
                    }
                    startActivity(itt);
                    break;
            }
            return true;
        }
    };
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
        Type type = new TypeToken<List<codemau>>(){}.getType();
        List<codemau> dscodemau = gson.fromJson(jsonString, type);
        for (codemau CM : dscodemau){
            DanhSachCodeMau.add(new codemau(CM.macodemau, CM.tencodemau));
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

}