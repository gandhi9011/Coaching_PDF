package com.example.admin.karsol_ano;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompanyActivity extends AppCompatActivity {
    LinearLayout company1,company2;
    TextView tvcompany1,tvcompany2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("COMPANY");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB1400")));
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(this, HomepageActivity.class));

            case R.id.action_aboutus:
                // search action
                return true;
            case R.id.action_contactus:
                // location found

                return true;
            case R.id.action_developer:
                startActivity(new Intent(this, Developed_Activity.class));



            case R.id.action_signout:
                // location found

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
