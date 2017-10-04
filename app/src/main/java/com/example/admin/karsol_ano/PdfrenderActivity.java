package com.example.admin.karsol_ano;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.PowerManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PdfrenderActivity extends AppCompatActivity {
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private float currentZoomLevel = 12;
    private static String  PDFFILENAME;
    private static String  URL_LINK;
    private static PowerManager.WakeLock mWakeLock;
    private ParcelFileDescriptor FileDescriptor;
    private PdfRenderer PdfRenderer;
    private PdfRenderer.Page CurrentPage;
    private ImageView ImageView;
    private LinearLayout image_layout;
    private Button Previous,Next;
    private int PageIndex;
    Matrix matrix=new Matrix();
    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfrender);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window statusBar = getWindow();
            statusBar.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            statusBar.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBar.setStatusBarColor(ContextCompat.getColor(this, R.color.appbar));
        }
        final ActionBar actionar = getSupportActionBar();

        actionar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BB4E97")));
        ImageView = (ImageView) findViewById(R.id.image);
        Previous = (Button) findViewById(R.id.previous);
        Next = (Button) findViewById(R.id.next);
        image_layout=(LinearLayout)findViewById(R.id.imageviewlayout);
        SharedPreferences sharedpreferences = getSharedPreferences("PDFStructure", Context.MODE_PRIVATE);
        ProgressDialog mProgressDialog;

// instantiate it within the onCreate method
        mProgressDialog = new ProgressDialog(PdfrenderActivity.this);
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
        PageIndex = 0;
        if (null != savedInstanceState) {
            PageIndex = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }


        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showPage(CurrentPage.getIndex() - 1);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                showPage(CurrentPage.getIndex() + 1);
            }
        });
        RectF imageRectF = new RectF(0, 0, image_layout.getWidth(), image_layout.getHeight());
        RectF viewRectF = new RectF(0, 0, ImageView.getWidth(), ImageView.getHeight());
        matrix.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER);
        ImageView.setImageMatrix(matrix);
        ImageView.setOnTouchListener(new Touch());
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //downloadTask.cancel(true);
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            openRenderer();
            showPage(PageIndex);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Downloading" , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        try {


            super.onStop();
            closeRenderer();
        }
        catch (Exception e)
        {Toast.makeText(PdfrenderActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != CurrentPage) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, CurrentPage.getIndex());
        }
    }

    /**
     * Sets up a {@link android.graphics.pdf.PdfRenderer} and related resources.
     */
    private void openRenderer() throws IOException {
        // In this sample, we read a PDF from the assets directory.
        File file = new File(getExternalCacheDir(), "/aarzu/"+PDFFILENAME);
        if (!file.exists()) {

            new DownloadFile().execute(URL_LINK+"1");


        }
        FileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (FileDescriptor != null) {
            PdfRenderer = new PdfRenderer(FileDescriptor);
        }
    }

    /**
     * Closes the {@link android.graphics.pdf.PdfRenderer} and related resources.
     *
     * @throws java.io.IOException When the PDF file cannot be closed.
     */
    private void closeRenderer()
    {
        try
        {
            if (null != CurrentPage) {
                CurrentPage.close();
            }
            FileDescriptor.close();
            PdfRenderer.close();
        }
        catch (IOException e)
        {

            Toast.makeText(PdfrenderActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();


        }
    }


    private void showPage(int index) {
        try {
            if (PdfRenderer.getPageCount() <= index) {
                return;
            }
            // Make sure to close the current page before opening another one.
            if (null != CurrentPage) {
                CurrentPage.close();
            }
            // Use `openPage` to open a specific page in PDF.
            CurrentPage = PdfRenderer.openPage(index);
            // Important: the destination bitmap must be ARGB (not RGB).
            Bitmap bitmap = Bitmap.createBitmap(CurrentPage.getWidth(), CurrentPage.getHeight(),
                    Bitmap.Config.ARGB_8888);
            // Here, we render the page onto the Bitmap.
            // To render a portion of the page, use the second and third parameter. Pass nulls to get
            // the default result.
            // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
            CurrentPage.render(bitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            // We are ready to show the Bitmap to user.
            ImageView.setImageBitmap(bitmap);
            updateUi();
        }
        catch (Exception e)
        {e.printStackTrace();}
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    private void updateUi() {
        int index = CurrentPage.getIndex();
        int pageCount = PdfRenderer.getPageCount();
        Previous.setEnabled(0 != index);
        Next.setEnabled(index + 1 < pageCount);
    }

    /**
     * Gets the number of pages in the PDF. This method is marked as public for testing.
     *
     * @return The number of pages.
     */
    public int getPageCount() {
        return PdfRenderer.getPageCount();
    }

    private class DownloadFile extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create progress dialog
            mProgressDialog = new ProgressDialog(PdfrenderActivity.this);
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
