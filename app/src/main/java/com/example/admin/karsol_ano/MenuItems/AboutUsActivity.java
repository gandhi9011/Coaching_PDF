package com.example.admin.karsol_ano.MenuItems;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.karsol_ano.R;

public class AboutUsActivity extends AppCompatActivity {
TextView para;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        para=(TextView)findViewById(R.id.aboutus);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("About Us");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB1400")));
        para.setText("Aarzu Compact\n" +
                "Aarzu Cmpact is an independent  publisher of Accounting books. The company was founded by Amar S Purswania professional who is dedicated to publish the best in various topical areas for the benift of Students worldwide.\n" +
                "At Aarzu, we believe in delivering quality content at LOW prices with more and more Compact versions each and every day. We believe in leveraging on the latest publishing technology to deliver quality products and services for the benefit of our customers and partners. \n" +
                "At Aarzu we specially trying to give Compact studies so...students\n" +
                "1. Get psychological satisfaction..\n" +
                "2. Can Complete basic logical knowledge\n" +
                "3. Maintains interest in accounts\n" +
                "4. Less guidance needed\n" +
                "5. Became Self dependent\n" +
                "6. Less time to complete studies...\n" +
                "Our Mission\n" +
                "To deliver excellence in academic and tobe a part of education system only to Surve as many as Students as possible....\n" +
                "Our Values\n" +
                "Passion for Excellence - we challenge ourselves to excel in all aspects of publishing and most importantly, we enjoy in what we are doing.\n" +
                "Student Oriented - we are committed to provide superior products and services at the highest level of quality and professionalism.\n" +
                "Our People - we treat our employees as individuals and we empower them to realize their full potential and contribution. We believe in open communication and share our success with everyone in the organization.\n" +
                "Innovation & Technology - we actively encourage a culture of innovation, which facilitates the development of new technologies and ensures a high quality product.\n" +
                "Partnership - our market is global and we believe in the power of partnership with our customers and suppliers to meet our common objectives.\n");
    }
}
