package com.example.admin.karsol_ano;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class EnglishGujratiActivity extends AppCompatActivity {
    TextView tv1,tv2;
    LinearLayout english,gujrati;
    private static final String FILENAME = "basic1.pdf";
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_gujrati);
        tv1=(TextView)findViewById(R.id.engtv1);
        tv2=(TextView)findViewById(R.id.gujtv2);
        english=(LinearLayout)findViewById(R.id.english);
        gujrati=(LinearLayout)findViewById(R.id.gujrati);

        Bundle b=getIntent().getExtras();
        final String pdfname=b.getString("BtnValue");
        //Toast.makeText(this,pdfname,Toast.LENGTH_LONG).show();

        ProgressDialog mProgressDialog;

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(EnglishGujratiActivity.this);
        mProgressDialog.setMessage("A message");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);
        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent pdfview=new Intent(EnglishGujratiActivity.this,PdfrenderActivity.class);
                File file = new File(getExternalCacheDir(), "/aarzu1/"+FILENAME);
                if(!file.exists())
                {
                    new DownloadFile().execute("https://www.dropbox.com/s/oi4itxg10gm62zt/Basic%201%20Full%20Theory%20pdf.pdf?dl=1", "basic1.pdf");
                }


                pdfview.putExtra("Pdfname",pdfname+"English");
                startActivity(pdfview);

            }
        });

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //downloadTask.cancel(true);
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
    private class DownloadFile extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
           super.onPreExecute();
            // Create progress dialog
            mProgressDialog = new ProgressDialog(EnglishGujratiActivity.this);
            // Set your progress dialog Title
            mProgressDialog.setTitle("Progress Bar Tutorial");
            // Set your progress dialog Message
            mProgressDialog.setMessage("Downloading, Please Wait!");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            // Show progress dialog
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // f
            String fileName = strings[1];  //
            String extStorageDirectory = getExternalCacheDir().toString();
            File folder = new File(extStorageDirectory, "aarzu1");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;




        }
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext(), "Download PDf successfully", Toast.LENGTH_SHORT).show();

            Log.d("Download complete", "----------");
        }

        protected void onProgressUpdate(Integer... progress) {
            //super.onProgressUpdate(progress);
            // Update the progress dialog
            mProgressDialog.setProgress(progress[0]);
            // Dismiss the progress dialog
            //mProgressDialog.dismiss();
        }



    }



}
