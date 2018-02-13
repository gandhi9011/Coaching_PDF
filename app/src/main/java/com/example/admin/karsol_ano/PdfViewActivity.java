package com.example.admin.karsol_ano;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PdfViewActivity extends AppCompatActivity {
    private static String  PDFFILENAME;
    private static String  URL_LINK;
    private static PowerManager.WakeLock mWakeLock;

    ProgressDialog mProgressDialog;
PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        pdfView=(PDFView)findViewById(R.id.pdf);
        final ActionBar actionar = getSupportActionBar();
        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));

        SharedPreferences sharedpreferences = getSharedPreferences("PDFStructure", Context.MODE_PRIVATE);
        ProgressDialog mProgressDialog;

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(PdfViewActivity.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        Bundle b=getIntent().getExtras();
        PDFFILENAME=sharedpreferences.getString("CoursePart",null)+sharedpreferences.getString("Language",null);
        actionar.setTitle(PDFFILENAME);
        URL_LINK=b.getString("Link");
        URL_LINK=b.getString("Link");
        URL_LINK=URL_LINK.substring(0,URL_LINK.length()-1);

        Toast.makeText(this,PDFFILENAME,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            openPDFView();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Downloading" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        try {


            super.onStop();

        }
        catch (Exception e)
        {
            Toast.makeText(PdfViewActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    /**
     * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
     */
    private void openPDFView() throws IOException {
        // In this sample, we read a PDF from the assets directory.
        File file = new File(getExternalCacheDir(), "/aarzu/"+PDFFILENAME);
        Uri path = Uri.fromFile(file);
        pdfView.fromUri(path).load();
        if (!file.exists()) {

            new DownloadFile().execute(URL_LINK+"1");


        }

    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            mProgressDialog = new ProgressDialog(PdfViewActivity.this);
            // Set your progress dialog Title
            mProgressDialog.setTitle(PDFFILENAME);
            // Set your progress dialog Message
            mProgressDialog.setMessage("Downloading, Please Wait!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show progress dialog
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... Url) {
            try {
                URL url = new URL(Url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // Detect the file lenghth
                int fileLength = connection.getContentLength();

                // Locate storage location
                String extStorageDirectory = getExternalCacheDir().toString();
                File folder = new File(extStorageDirectory, "aarzu");
                folder.mkdir();
                File pdfFile = new File(folder, PDFFILENAME);

                // Download the file
                InputStream input = new BufferedInputStream(url.openStream());

                // Save the downloaded file
                OutputStream output = new FileOutputStream(pdfFile);

                byte data[] = new byte[1024];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // Publish the progress
                    publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }

                // Close connection
                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
                // Error Log
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // Update the progress dialog
            mProgressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
            //mProgressDialog.dismiss();

        }

        protected void onPostExecute(String Result)
        {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (Result!=null)
            {
                onStop();
            }
            else
            {
                onStart();
            }
        }


    }
}
