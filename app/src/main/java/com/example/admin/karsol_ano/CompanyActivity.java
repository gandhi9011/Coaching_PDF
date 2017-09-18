package com.example.admin.karsol_ano;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyActivity extends AppCompatActivity {
    LinearLayout company1,company2;
    TextView tvcompany1,tvcompany2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        company1=(LinearLayout)findViewById(R.id.company1);
        company2=(LinearLayout)findViewById(R.id.company2);
        tvcompany1=(TextView)findViewById(R.id.companytv1);
        tvcompany2=(TextView)findViewById(R.id.companytv2);
        company1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent company_i=new Intent(CompanyActivity.this,EnglishGujratiActivity.class);
                company_i.putExtra("BtnValue","company1");
                startActivity(company_i);
            }
        });

        company2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent company_i=new Intent(CompanyActivity.this,EnglishGujratiActivity.class);
                company_i.putExtra("BtnValue","company2");
                startActivity(company_i);
            }
        });


    }
}
