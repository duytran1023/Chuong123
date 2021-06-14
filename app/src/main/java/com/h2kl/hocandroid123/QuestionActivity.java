package com.h2kl.hocandroid123;


import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionActivity extends AppCompatActivity {
    Intent intent = null, chooser = null;
    EditText editText;
    String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



        editText = findViewById(R.id.edtQuestion);
        Button buttonSendMail = findViewById(R.id.btnSendMail);
        buttonSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = editText.getText().toString();
                if (text == ""){
                    text = "Xin chào, tôi có thắc mắc đó là....";
                }
                intent = new Intent(Intent.ACTION_SEND);
                intent.setData(Uri.parse("mailto:"));
                String[] to={"hkhadev@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hỏi đáp chương 1,2,3 Android");

                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.setType("message/rfc822");
                chooser = Intent.createChooser(intent, "Send Mail");
                startActivity(chooser);
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
