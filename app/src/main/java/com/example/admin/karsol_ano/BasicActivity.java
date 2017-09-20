package com.example.admin.karsol_ano;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BasicActivity extends AppCompatActivity {
    LinearLayout basic1,basic2,basic3;
    TextView tvbasic1,tvbasic2,tvbasic3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("BASIC");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB1400")));
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
                Intent basic_i=new Intent(BasicActivity.this,EnglishGujratiActivity.class);
                basic_i.putExtra("BtnValue","basic1");
                startActivity(basic_i);

            }
        });

        basic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent basic_i=new Intent(BasicActivity.this,EnglishGujratiActivity.class);
                basic_i.putExtra("BtnValue","basic2");
                startActivity(basic_i);
            }
        });


        basic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent basic_i=new Intent(BasicActivity.this,EnglishGujratiActivity.class);
                basic_i.putExtra("BtnValue","basic3");
                startActivity(basic_i);
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
