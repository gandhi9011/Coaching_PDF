package com.example.admin.karsol_ano.MenuItems;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.karsol_ano.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ChangePasswordActivity extends AppCompatActivity {
     EditText email,oldpass,newpass;
     Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        email=(EditText)findViewById(R.id.emailun);
        oldpass=(EditText)findViewById(R.id.oldPassword);
        newpass=(EditText)findViewById(R.id.newPassword);
        submit=(Button)findViewById(R.id.submit);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("Change Password");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#DB1400")));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new ChangePassword().execute("https://aarzucompact.herokuapp.com/ChangePasswordStudentServlet?semail="+email.getText().toString()+"&spassword="+oldpass.getText().toString()+"&npassword="+newpass.getText().toString());
            }
        });
    }

    private class ChangePassword extends AsyncTask<String, Void, String> {




        @Override
        protected String doInBackground(String... Url) {
            String output = null;
            Log.e("result",Url[0]);
            output = getOutputFromUrl(Url[0]);


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


        protected void onPostExecute(String Result)
        {

            if (Result!=null)
            {
                Toast.makeText(ChangePasswordActivity.this,Result,Toast.LENGTH_LONG).show();
//                Intent pdfview=new Intent(EnglishGujratiActivity.this,PdfrenderActivity.class);
//                pdfview.putExtra("Link",Result);
//                pdfview.putExtra("Course",CoursePart);
//                pdfview.putExtra("Language","Gujrati");
//                startActivity(pdfview);
            }
            else
            {
                Toast.makeText(ChangePasswordActivity.this,"Server Problem",Toast.LENGTH_LONG).show();
            }
        }


    }

}
