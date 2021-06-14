package com.h2kl.hocandroid123;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DsCodeMau extends AppCompatActivity {

    ArrayList<codemau> CodeMau;
    ListView lviewcodemau;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_code_mau);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



        lviewcodemau = (ListView)findViewById(R.id.lviewcodemau);
        CodeMau = new ArrayList<>();
        String json = LoadData("danhsachcodemau.json");
        parseJSON(json);

        AdapterCodeMau arrayAdapter = new AdapterCodeMau(this, R.layout.layoutcodemau, CodeMau);
        lviewcodemau.setAdapter(arrayAdapter);

        lviewcodemau.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(DsCodeMau.this, XemcodemauActivity.class);
                Bundle bundle= new Bundle();
                bundle.putInt("macodemau",position);
                intent.putExtra("codemau",bundle);
                startActivity(intent);

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
            CodeMau.add(new codemau(CM.macodemau, CM.tencodemau));
        }
    }
}