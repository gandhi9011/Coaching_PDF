package com.example.admin.karsol_ano;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasicActivity extends AppCompatActivity {
    LinearLayout basic1,basic2,basic3;
    TextView tvbasic1,tvbasic2,tvbasic3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        basic1=(LinearLayout)findViewById(R.id.basic1);
        basic2=(LinearLayout)findViewById(R.id.basic2);
        basic3=(LinearLayout)findViewById(R.id.basic3);
        tvbasic1=(TextView)findViewById(R.id.basictv1);
        tvbasic2=(TextView)findViewById(R.id.basictv2);
        tvbasic3=(TextView)findViewById(R.id.basictv3);
        basic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        basic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });


        basic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

    }
}
