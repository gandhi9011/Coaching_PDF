package com.example.admin.karsol_ano;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PartnershipActivity extends AppCompatActivity {
    LinearLayout partnership1,partnership2;
    TextView tvpartnership1,tvpartnership2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership);
        partnership1=(LinearLayout)findViewById(R.id.partnership1);
        partnership2=(LinearLayout)findViewById(R.id.partnership2);
        tvpartnership1=(TextView)findViewById(R.id.partnershiptv1);
        tvpartnership2=(TextView)findViewById(R.id.partnershiptv2);
        partnership1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        partnership2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });



    }
}
