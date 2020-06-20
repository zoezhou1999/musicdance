package com.example.zhouyuhong.musicdance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView coinNum=findViewById(R.id.coin_num);

        SharedPreferences sharedpreferences = getSharedPreferences("myPreference",
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains("currentCoin")) {
            coinNum.setText("当前金币: "+sharedpreferences.getString("currentCoin", ""));
        }else{
            coinNum.setText("当前金币: 0");

            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("currentCoin", "0");
            editor.commit();
        }
    }

    public void enterSelectingMenu(View view){

        Intent intent=new Intent(this,SelectingMenu.class);
        Button btn=(Button) view;
        intent.putExtra("section",btn.getText().toString());
//        intent.putExtra()
        startActivity(intent);

    }

    public void returnStart(View view){

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }


}
