package com.example.admin.karsol_ano.course;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.admin.karsol_ano.MenuItems.ChangePasswordActivity;
import com.example.admin.karsol_ano.LoginModule.LoginActivity;
import com.example.admin.karsol_ano.MenuItems.AboutUsActivity;
import com.example.admin.karsol_ano.MenuItems.ContactUsActivity;
import com.example.admin.karsol_ano.MenuItems.Developed_Activity;
import com.example.admin.karsol_ano.MenuItems.ForgotPasswordActivity;
import com.example.admin.karsol_ano.MenuItems.PriceActivity;
import com.example.admin.karsol_ano.PdfrenderActivity;
import com.example.admin.karsol_ano.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EnglishGujratiActivity extends AppCompatActivity {
    private static PowerManager.WakeLock mWakeLock;
    TextView tv1, tv2;
    LinearLayout english, gujrati;
    ImageView egiv;
    String Course = "", CoursePart = "";

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
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));


// instantiate it within the onCreate method

        tv1 = (TextView) findViewById(R.id.engtv1);
        tv2 = (TextView) findViewById(R.id.gujtv2);
        egiv=(ImageView)findViewById(R.id.imageVieweg);
        english = (LinearLayout) findViewById(R.id.english);
        gujrati = (LinearLayout) findViewById(R.id.gujrati);

        Bundle b = getIntent().getExtras();
        CoursePart = b.getString("BtnValue");
        if (CoursePart.equals("BASIC1") || CoursePart.equals("BASIC2") || CoursePart.equals("BASIC3")) {

            Course = "BASIC";
            if (CoursePart.equals("BASIC1"))
            {
                egiv.setImageResource(R.drawable.basic11);
                Log.e("if condition","in basic1");
            }
            else  if (CoursePart.equals("BASIC2"))
            {
                egiv.setImageResource(R.drawable.basic22);
                Log.e("if condition","in basic2");
            }
            else  if (CoursePart.equals("BASIC3"))
            {
                egiv.setImageResource(R.drawable.basic33);
                Log.e("if condition","in basic3");
            }
        }

        if (CoursePart.equals("COMPANY1") || CoursePart.equals("COMPANY2")) {
            Course = "COMPANY";
            if (CoursePart.equals("COMPANY1"))
            {
                egiv.setImageResource(R.drawable.company_11);
                Log.e("if condition","in company1");
            }
            else  if (CoursePart.equals("COMPANY2"))
            {
                egiv.setImageResource(R.drawable.company_22);
                Log.e("if condition","in Company2");
            }
        }

        if (CoursePart.equals("PARTNERSHIP1") || CoursePart.equals("PARTNERSHIP2")) {
            Course = "PARTNERSHIP";
            if (CoursePart.equals("PARTNERSHIP1"))
            {
                egiv.setImageResource(R.drawable.partnership_11);
                Log.e("if condition","in partnership1");
            }
            else  if (CoursePart.equals("PARTNERSHIP2"))
            {
                egiv.setImageResource(R.drawable.partnership_22);
                Log.e("if condition","in partnership2");
            }
        }
        Toast.makeText(this, CoursePart + "" + Course, Toast.LENGTH_LONG).show();


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("PDFStructure", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Course", Course);
                editor.putString("CoursePart", CoursePart);
                editor.putString("Language", "ENGLISH");
                editor.commit();
                GetXMLTask downloadpdf = new GetXMLTask();
                downloadpdf.execute("https://aarzucompact.herokuapp.com/GetCourseUrlServlet?course=" + Course + "&coursepart=" + CoursePart + "&language=ENGLISH");

            }
        });


        gujrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("PDFStructure", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Course", Course);
                editor.putString("CoursePart", CoursePart);
                editor.putString("Language", "GUJARATI");
                editor.commit();

                GetXMLTask downloadpdf = new GetXMLTask();
                downloadpdf.execute("https://aarzucompact.herokuapp.com/GetCourseUrlServlet?course=" + Course + "&coursepart=" + CoursePart + "&language=GUJARATI");

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

    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;

            output = getOutputFromUrl(urls[0]);
            Log.e("", urls[0] + "" + output);

            return output;
        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            if (output.trim().equals("error")) {
                Toast.makeText(EnglishGujratiActivity.this, "PDF Coming Soon", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(EnglishGujratiActivity.this,output,Toast.LENGTH_LONG).show();
                Intent pdfview = new Intent(EnglishGujratiActivity.this, PdfrenderActivity.class);
                pdfview.putExtra("Link", output.trim());
                startActivity(pdfview);
            }
        }
    }


}
