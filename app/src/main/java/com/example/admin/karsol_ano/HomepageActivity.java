package com.example.admin.karsol_ano;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomepageActivity extends AppCompatActivity {
LinearLayout basic,company,partnership;
TextView  tvbasic,tvcompany,tvpartnership;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        basic=(LinearLayout)findViewById(R.id.basic);
        company=(LinearLayout)findViewById(R.id.company);
        partnership=(LinearLayout)findViewById(R.id.partnership);
        tvbasic=(TextView)findViewById(R.id.basictv);
        tvcompany=(TextView)findViewById(R.id.companytv);
        tvpartnership=(TextView)findViewById(R.id.partnershiptv);
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent basic=new Intent(HomepageActivity.this,BasicActivity.class);
                startActivity(basic);
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent company=new Intent(HomepageActivity.this,CompanyActivity.class);
                startActivity(company);
            }
        });


        partnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent partnership=new Intent(HomepageActivity.this,PartnershipActivity.class);
                startActivity(partnership);
            }
        });

    }
}
