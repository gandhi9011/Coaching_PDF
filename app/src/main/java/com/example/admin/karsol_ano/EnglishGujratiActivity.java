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

import java.io.File;

public class EnglishGujratiActivity extends AppCompatActivity {
    TextView tv1,tv2;
    LinearLayout english,gujrati;
    private static final String FILENAME = "basic1.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_gujrati);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("LANGUAGE");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB1400")));
        tv1=(TextView)findViewById(R.id.engtv1);
        tv2=(TextView)findViewById(R.id.gujtv2);
        english=(LinearLayout)findViewById(R.id.english);
        gujrati=(LinearLayout)findViewById(R.id.gujrati);

        Bundle b=getIntent().getExtras();
        final String pdfname=b.getString("BtnValue");
        //Toast.makeText(this,pdfname,Toast.LENGTH_LONG).show();


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent pdfview=new Intent(EnglishGujratiActivity.this,PdfrenderActivity.class);
                File file = new File(getExternalCacheDir(), "/aarzu1/"+FILENAME);
                pdfview.putExtra("Pdfname",pdfname+"English");
                startActivity(pdfview);

            }
        });



        gujrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent pdfview=new Intent(EnglishGujratiActivity.this,PdfrenderActivity.class);
                pdfview.putExtra("Pdfname",pdfname+"Gujrati");
                startActivity(pdfview);

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
