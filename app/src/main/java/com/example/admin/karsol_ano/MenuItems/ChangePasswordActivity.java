package com.example.admin.karsol_ano.MenuItems;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.karsol_ano.LoginModule.LoginActivity;
import com.example.admin.karsol_ano.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText email, new_pass, old_pass;
    Button submit1;
    private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change2_password);
        email = (EditText) findViewById(R.id.editText);
        old_pass = (EditText) findViewById(R.id.editText2);
        new_pass = (EditText) findViewById(R.id.editText3);
        submit1 = (Button) findViewById(R.id.btnregister);
        Log.d("hello","oncretae");
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setTitle("CHANGE PASSWORD");
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));
        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        email.setError(null);
        new_pass.setError(null);
        old_pass.setError(null);

        // Store values at the time of the login attempt.
        String email_g = email.getText().toString();
        String newpassword = new_pass.getText().toString();
        String oldpassword = old_pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(oldpassword) && !isPasswordValid(newpassword)) {
            old_pass.setError(getString(R.string.error_invalid_password));
            focusView = old_pass;
            cancel = true;
        }

        if (!TextUtils.isEmpty(newpassword) && !isPasswordValid(newpassword)) {
            new_pass.setError(getString(R.string.error_invalid_password));
            focusView = new_pass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email_g)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(email_g)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            mAuthTask = new UserLoginTask(email_g, newpassword, oldpassword);
            mAuthTask.execute("https://aarzucompact.herokuapp.com/ChangePasswordStudentServlet?semail=");
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
//        Pattern pattern;
//        Matcher matcher;
//        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
//        pattern = Pattern.compile(PASSWORD_PATTERN);
//        matcher = pattern.matcher(password);

        return password.length()>=5;
    }

    public class UserLoginTask extends AsyncTask<String, Void, String> {

        private final String mEmail;
        private final String mPassword, mOldPassward;


        UserLoginTask(String email, String password, String oldpassword) {
            mEmail = email;
            mPassword = password;
            mOldPassward = oldpassword;

        }


        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.

            try {// mPassword
                // Simulate network access.
                Log.e("123456789", params[0] + mEmail + "&spassword=" + mOldPassward + "&npassword=" + mPassword);

                return getOutputFromUrl(params[0].trim() + URLEncoder.encode(mEmail, "UTF-8") + "&spassword=" + URLEncoder.encode(mOldPassward, "UTF-8") + "&npassword=" + URLEncoder.encode(mPassword, "UTF-8"));
            } catch (IOException e) {
                Log.e("err", e.getMessage());
                return null;
            }


        }

        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);

                Log.e("1233211111", "" + output);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("123321", output);
            return output;
        }

        @Override
        protected void onPostExecute(String output) {
            mAuthTask = null;


            Log.e("123456789beforeif", output);
            if (output.trim().equals("success")) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangePasswordActivity.this);

                // set title
                alertDialogBuilder.setTitle("New Password");

                // set dialog message
                alertDialogBuilder
                        .setMessage("Your Password is Changed.")
                        .setCancelable(false)
                        .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                Intent login = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                startActivity(login);
                                dialog.dismiss();
                            }
                        });
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
            else
            {

                Toast.makeText(ChangePasswordActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                old_pass.setError(getString(R.string.error_incorrect_password));
                old_pass.requestFocus();
            }

        }
    }


}
