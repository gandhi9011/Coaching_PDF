package com.example.admin.karsol_ano.LoginModule;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.admin.karsol_ano.MenuItems.AboutUsActivity;
import com.example.admin.karsol_ano.MenuItems.ContactUsActivity;
import com.example.admin.karsol_ano.MenuItems.Developed_Activity;
import com.example.admin.karsol_ano.MenuItems.PriceActivity;
import com.example.admin.karsol_ano.R;
import com.example.admin.karsol_ano.course.HomepageActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String LOGIN_URL = "https://aarzucompact.herokuapp.com/loginStudentServlet?semail=";
    public static final String REGISTRATION_URL = "https://aarzucompact.herokuapp.com/StudentInsertServlet?semail=";
    public static final String ForgotPassword_URL = "https://aarzucompact.herokuapp.com/StudentForgotPassword?semail=";

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
     User user;
    UserOperation operation;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         operation=new UserOperation(this);

        if(!isNetworkConnected())
        {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

            // set title
            alertDialogBuilder.setTitle("Connection Error");

            // set dialog message
            alertDialogBuilder
                    .setMessage("You are not Connected with Network.")
                    .setCancelable(false)
                    .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                            startActivity(intent);
                            // if this button is clicked, close
                            // current activity
                            dialog.dismiss();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));



            // Set up the login form.
            mEmailView = (EditText) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);
            user = operation.getUser();
        if(user!=null){
            mEmailView.setText(user.getEmail());
            mPasswordView.setText(user.getPassword());
        }
        mEmailView.setHintTextColor(getResources().getColor(R.color.colorPrimary));

        mPasswordView.setHintTextColor(getResources().getColor(R.color.colorPrimary));

            mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                    if (id == R.id.password || id == EditorInfo.IME_NULL) {
                        attemptLogin();
                        return true;
                    }
                    return false;
                }
            });

            MaterialRippleLayout mEmailSignInButton = (MaterialRippleLayout) findViewById(R.id.email_sign_in_button);
        MaterialRippleLayout mEmailRegistrationButton = (MaterialRippleLayout) findViewById(R.id.email_registration_button);
            TextView mForgotpassword = (TextView) findViewById(R.id.mforgotpass);
            mEmailSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {

                    attemptLogin();

                }
            });
            mEmailRegistrationButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    attemptRegistration();
                }
            });
            mForgotpassword.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    attemptForgotPassword();
                }
            });

            mLoginFormView = findViewById(R.id.login_form);
            mProgressView = findViewById(R.id.login_progress);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        String email,password;
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

//        SharedPreferences sp1=this.getSharedPreferences("Login", MODE_PRIVATE);
//        String unm=sp1.getString("Unm", null);
//        String pass = sp1.getString("Psw", null);
//        if(!(unm.equals(null)) && !(pass.equals(null)) )
//        {
//            email=unm;
//            password=pass;
//        }
//        else
//            {
            // Store values at the time of the login attempt.
            email = mEmailView.getText().toString();
            password = mPasswordView.getText().toString();
//        }
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, 1);
            mAuthTask.execute(LOGIN_URL);
        }
    }


    private void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password, 2);
            mAuthTask.execute(REGISTRATION_URL);
        }
    }

    private void attemptForgotPassword() {

        final String[] email_forgot = new String[1];
        LayoutInflater li = LayoutInflater.from(LoginActivity.this);
        View promptsView = li.inflate(R.layout.forgot_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text

                                if (mAuthTask != null) {
                                    return;
                                }

                                // Reset errors.
                                userInput.setError(null);

                                // Store values at the time of the login attempt.
                                String email = userInput.getText().toString();

                                boolean cancel = false;
                                View focusView = null;


                                // Check for a valid email address.
                                if (TextUtils.isEmpty(email)) {
                                    userInput.setError(getString(R.string.error_field_required));
                                    focusView = userInput;
                                    cancel = true;
                                } else if (!isEmailValid(email)) {
                                    userInput.setError(getString(R.string.error_invalid_email));
                                    focusView = userInput;
                                    cancel = true;
                                }

                                if (cancel) {
                                    // There was an error; don't attempt login and focus the first
                                    // form field with an error.
                                    focusView.requestFocus();
                                } else {
                                    // Show a progress spinner, and kick off a background task to
                                    // perform the user login attempt.
                                    showProgress(true);
                                    mAuthTask = new UserLoginTask(email, "forgot", 3);
                                    mAuthTask.execute(ForgotPassword_URL);
                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();


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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu_item, menu);

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

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class UserLoginTask extends AsyncTask<String, Void, String> {

        private final String mEmail;
        private final String mPassword;
        private int mx;

        UserLoginTask(String email, String password, int x) {
            mEmail = email;
            mPassword = password;
            mx = x;
        }


        @Override
        protected String doInBackground(String... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                Log.e("12345101010", "456789");
                return e.getMessage();
            }
            try {
                if (mPassword.equals("forgot") && mx == 3) {


                    return getOutputFromUrl(params[0] + URLEncoder.encode(mEmail, "UTF-8"));

                } else {
                    return getOutputFromUrl(params[0] + URLEncoder.encode(mEmail, "UTF-8") + "&spassword=" + URLEncoder.encode(mPassword, "UTF-8"));
                }
            } catch (IOException IO) {
                Log.e("errr", IO.getMessage());
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
            Log.e("123321", "" + output);
            return output;
        }

        @Override
        protected void onPostExecute(final String output) {
           try {
               mAuthTask = null;

               showProgress(false);
               Log.e("123456789", "" + output);
               if (output.trim().equals("success") && mx == 1) {
                   Log.e("123456789--in if", "" + output);
                   user = new User();
                   user.setEmail(mEmailView.getText().toString());
                   user.setPassword(mPasswordView.getText().toString());
                   String x = operation.Change(user);
                   Intent login = new Intent(LoginActivity.this, HomepageActivity.class);
                   startActivity(login);

               }

               if (output.trim().equals("success") && mx == 2) {
                   Log.e("123456789--in if", "" + output);
                   final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                   // set title
                   alertDialogBuilder.setTitle("New User");

                   // set dialog message
                   alertDialogBuilder
                           .setMessage("Registration done!")
                           .setCancelable(false)
                           .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // if this button is clicked, close
                                   // current activity
                                   dialog.dismiss();
                               }
                           });
                   // create alert dialog
                   AlertDialog alertDialog = alertDialogBuilder.create();

                   // show it
                   alertDialog.show();
               }

               if (mx == 3) {
                   final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

                   // set title
                   alertDialogBuilder.setTitle("Your Password");

                   // set dialog message
                   alertDialogBuilder
                           .setMessage(output)
                           .setCancelable(false)
                           .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // if this button is clicked, close
                                   // current activity
                                   dialog.dismiss();
                               }
                           });
                   // create alert dialog
                   AlertDialog alertDialog = alertDialogBuilder.create();

                   // show it
                   alertDialog.show();
               } else {
                   if (mx == 1 && output.trim().equals("error")) {
                       Toast.makeText(LoginActivity.this, "login error", Toast.LENGTH_LONG).show();
                   } else if (mx == 1 && output.trim().equals("logout")) {
                       Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_LONG).show();
                      // mPasswordView.setError(getString(R.string.error_incorrect_password));
                      // mPasswordView.requestFocus();
                   }

               }
           }
           catch (Exception e)
           {e.printStackTrace();}
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}

