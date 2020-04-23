package com.example.zhouyuhong.musicdance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
    public void enterSelectingMenu(View view){

        Intent intent=new Intent(this,SelectingMenu.class);
        Button btn=(Button) view;
        intent.putExtra("section",btn.getText().toString());
//        intent.putExtra()
        startActivity(intent);

    }


}
