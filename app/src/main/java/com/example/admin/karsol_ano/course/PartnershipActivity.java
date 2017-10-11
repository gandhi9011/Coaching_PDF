package com.example.admin.karsol_ano.course;

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

import com.example.admin.karsol_ano.LoginModule.LoginActivity;
import com.example.admin.karsol_ano.MenuItems.AboutUsActivity;
import com.example.admin.karsol_ano.MenuItems.ChangePasswordActivity;
import com.example.admin.karsol_ano.MenuItems.ContactUsActivity;
import com.example.admin.karsol_ano.MenuItems.Developed_Activity;
import com.example.admin.karsol_ano.MenuItems.ForgotPasswordActivity;
import com.example.admin.karsol_ano.MenuItems.PriceActivity;
import com.example.admin.karsol_ano.R;

public class PartnershipActivity extends AppCompatActivity {
    LinearLayout partnership1, partnership2;
    TextView tvpartnership1, tvpartnership2;

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
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));
        partnership1 = (LinearLayout) findViewById(R.id.partnership1);
        partnership2 = (LinearLayout) findViewById(R.id.partnership2);
        tvpartnership1 = (TextView) findViewById(R.id.partnershiptv1);
        tvpartnership2 = (TextView) findViewById(R.id.partnershiptv2);
        partnership1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partnership_i = new Intent(PartnershipActivity.this, EnglishGujratiActivity.class);
                partnership_i.putExtra("BtnValue", "PARTNERSHIP1");
                startActivity(partnership_i);
            }
        });

        partnership2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partnership_i = new Intent(PartnershipActivity.this, EnglishGujratiActivity.class);
                partnership_i.putExtra("BtnValue", "PARTNERSHIP2");
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
                return true;
            case R.id.action_aboutus:
                startActivity(new Intent(this, AboutUsActivity.class));
                return true;
            case R.id.action_contactus:
                startActivity(new Intent(this, ContactUsActivity.class));
                return true;
            case R.id.action_developer:
                startActivity(new Intent(this, Developed_Activity.class));
                return true;
            case R.id.action_pricing:
                startActivity(new Intent(this, PriceActivity.class));
                return true;
            case R.id.action_signout:
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.action_changepass:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
