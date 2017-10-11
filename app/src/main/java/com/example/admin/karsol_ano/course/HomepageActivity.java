package com.example.admin.karsol_ano.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import com.example.admin.karsol_ano.LoginModule.SliderAdapter;
import com.example.admin.karsol_ano.MenuItems.AboutUsActivity;
import com.example.admin.karsol_ano.MenuItems.ChangePasswordActivity;
import com.example.admin.karsol_ano.MenuItems.ContactUsActivity;
import com.example.admin.karsol_ano.MenuItems.Developed_Activity;
import com.example.admin.karsol_ano.MenuItems.ForgotPasswordActivity;
import com.example.admin.karsol_ano.MenuItems.PriceActivity;
import com.example.admin.karsol_ano.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomepageActivity extends AppCompatActivity
{
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] AARZU= {R.drawable.one,R.drawable.two,R.drawable.third,R.drawable.four,R.drawable.five};
    private ArrayList<Integer> AARZUArray = new ArrayList<Integer>();
    LinearLayout basic, company, partnership;
    TextView tvbasic, tvcompany, tvpartnership;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        init();
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("HOME");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));
        basic = (LinearLayout) findViewById(R.id.basic);
        company = (LinearLayout) findViewById(R.id.company);
        partnership = (LinearLayout) findViewById(R.id.partnership);
        tvbasic = (TextView) findViewById(R.id.basictv);
        tvcompany = (TextView) findViewById(R.id.companytv);
        tvpartnership = (TextView) findViewById(R.id.partnershiptv);
        basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent basic = new Intent(HomepageActivity.this, BasicActivity.class);
                startActivity(basic);
            }
        });

        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent company = new Intent(HomepageActivity.this, CompanyActivity.class);
                startActivity(company);
            }
        });


        partnership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent partnership = new Intent(HomepageActivity.this, PartnershipActivity.class);
                startActivity(partnership);
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
                SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
                String unm=sp1.getString("Unm", null);
                String pass = sp1.getString("Psw", null);
                SharedPreferences.Editor Ed=sp1.edit();
                Ed.putString("Unm",null);
                Ed.putString("Psw",null);
                Ed.commit();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.action_changepass:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void init() {
        for(int i=0;i<AARZU.length;i++)
            AARZUArray.add(AARZU[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SliderAdapter(HomepageActivity.this,AARZUArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == AARZU.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000,5000);
    }
}
