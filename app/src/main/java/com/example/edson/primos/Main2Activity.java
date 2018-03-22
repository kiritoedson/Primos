package com.example.edson.primos;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i=getIntent();
        TextView tv=new TextView(this);
        String color=i.getStringExtra(MainActivity.NOTIFICACION2);
        if (color.equals("rojo")) {
            tv.setTextColor(Color.RED);
        }

        if (color.equals("azul")) {
            tv.setTextColor(Color.BLUE);
        }

        if (color.equals("negro")) {
            tv.setTextColor(Color.BLACK);
        }

        if (color.equals("amarillo")) {
            tv.setTextColor(Color.YELLOW);
        }


        int a= Integer.parseInt(i.getStringExtra(MainActivity.NOTIFICACION2));
        tv.setTextSize(a);
        tv.setText(i.getStringExtra(MainActivity.NOTIFICACION));
        setContentView(tv);

    }
}
