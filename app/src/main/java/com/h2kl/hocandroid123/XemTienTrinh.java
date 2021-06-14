package com.h2kl.hocandroid123;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XemTienTrinh extends AppCompatActivity {
    Button btn_tt;
    TextView xemtt;
    ArrayList<baihoc> BaiHoc;
    int thutubai=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_tien_trinh);

        BaiHoc =  new ArrayList<>();

        String json = LoadData("danhsachbaihoc.json");
        parseJSON(json);

        btn_tt = findViewById(R.id.btnTT);
        xemtt = findViewById(R.id.xemTT);

        // lưu tiến trình
        SharedPreferences pre = getSharedPreferences("myPrefs", MODE_PRIVATE);

         String tb = pre.getString("Tientrinh","Không có");
         try {
             thutubai = Integer.parseInt(tb);
         }catch (Exception e){
             thutubai = -1;
         }

         if(thutubai>=0){
             xemtt.setText("Bạn đã học tới bài "+ BaiHoc.get(thutubai).tenbaihoc);
             btn_tt.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Intent i = new Intent(XemTienTrinh.this, CN6.class);
                     Bundle bundle = new Bundle();
                     bundle.putInt("mabaihoc", thutubai+1);
                     i.putExtra("baihoc",bundle);
                     startActivity(i);
                 }
             });

         }
        else{
            xemtt.setText("Bắt đầu học ");
            btn_tt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(XemTienTrinh.this, CN6.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("mabaihoc", 0);
                    i.putExtra("baihoc",bundle);
                    startActivity(i);
                }
            });

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
    private void parseJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<baihoc>>(){}.getType();
        List<baihoc> dsbaihoc = gson.fromJson(jsonString, type);
        for (baihoc BH : dsbaihoc){
            BaiHoc.add(new baihoc(BH.mabaihoc, BH.tenbaihoc, BH.chuong));
        }
    }
}