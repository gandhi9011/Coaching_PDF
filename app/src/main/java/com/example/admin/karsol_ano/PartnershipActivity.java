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

public class PartnershipActivity extends AppCompatActivity {
    LinearLayout partnership1,partnership2;
    TextView tvpartnership1,tvpartnership2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("PARTNERSHIP");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB1400")));
        partnership1=(LinearLayout)findViewById(R.id.partnership1);
        partnership2=(LinearLayout)findViewById(R.id.partnership2);
        tvpartnership1=(TextView)findViewById(R.id.partnershiptv1);
        tvpartnership2=(TextView)findViewById(R.id.partnershiptv2);
        partnership1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent partnership_i=new Intent(PartnershipActivity.this,EnglishGujratiActivity.class);
                partnership_i.putExtra("BtnValue","partnership1");
                startActivity(partnership_i);
            }
        });

        partnership2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent partnership_i=new Intent(PartnershipActivity.this,EnglishGujratiActivity.class);
                partnership_i.putExtra("BtnValue","partnership2");
                startActivity(partnership_i);
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
