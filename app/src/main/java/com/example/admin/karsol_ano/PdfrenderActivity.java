package com.example.admin.karsol_ano;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class PdfrenderActivity extends AppCompatActivity {
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private static final String FILENAME = "basic1.pdf";
    private float currentZoomLevel = 12;
    private ParcelFileDescriptor FileDescriptor;
    private PdfRenderer PdfRenderer;
    private PdfRenderer.Page CurrentPage;
    private ImageView ImageView;
    private LinearLayout image_layout;
    private Button Previous,Next;
    private ImageView zoomin,zoomout;
    private int PageIndex;
    private HorizontalScrollView scrollView;
    Matrix matrix=new Matrix();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfrender);
        ImageView = (ImageView) findViewById(R.id.image);
        Previous = (Button) findViewById(R.id.previous);
        Next = (Button) findViewById(R.id.next);
        image_layout=(LinearLayout)findViewById(R.id.imageviewlayout);
        Bundle b=getIntent().getExtras();
        final String pdfname=b.getString("Pdfname");
        Toast.makeText(this,pdfname,Toast.LENGTH_LONG).show();
        scrollView=(HorizontalScrollView)findViewById(R.id.scrollView);
//        zoomin=(ImageView)findViewById(R.id.zoomin);
//        zoomout=(ImageView)findViewById(R.id.zoomout);
        PageIndex = 0;
        if (null != savedInstanceState) {
            PageIndex = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }


        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                currentZoomLevel = 12;
                showPage(CurrentPage.getIndex() - 1);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                currentZoomLevel = 12;
                showPage(CurrentPage.getIndex() + 1);
            }
        });
        RectF imageRectF = new RectF(0, 0, image_layout.getWidth(), image_layout.getHeight());
        RectF viewRectF = new RectF(0, 0, ImageView.getWidth(), ImageView.getHeight());
        matrix.setRectToRect(imageRectF, viewRectF, Matrix.ScaleToFit.CENTER);
        ImageView.setImageMatrix(matrix);
        ImageView.setOnTouchListener(new Touch());


    }


    @Override
    public void onStart() {
        super.onStart();
        try {
            openRenderer();
            showPage(PageIndex);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
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
        File file = new File(getExternalCacheDir(), "/aarzu1/"+FILENAME);
        if (!file.exists()) {

            new DownloadFile().execute("https://www.dropbox.com/s/oi4itxg10gm62zt/Basic%201%20Full%20Theory%20pdf.pdf?dl=1", "basic1.pdf");

            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
//            InputStream asset = getAssets().open(FILENAME);
//            FileOutputStream output = new FileOutputStream(file);
//            final byte[] buffer = new byte[1024];
//            int size;
//            while ((size = asset.read(buffer)) != -1) {
//                output.write(buffer, 0, size);
//            }
//            asset.close();
//            output.close();
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
    private void closeRenderer() throws IOException {
        if (null != CurrentPage) {
            CurrentPage.close();
        }
        PdfRenderer.close();
        FileDescriptor.close();
    }

    /**
     * Shows the specified page of PDF to the screen.
     *
     * @param index The page index.
     */
    private void showPage(int index) {
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
        int newWidth = (int) (getResources().getDisplayMetrics().widthPixels * CurrentPage.getWidth() / 72 * currentZoomLevel / 40);
        int newHeight = (int) (getResources().getDisplayMetrics().heightPixels * CurrentPage.getHeight() / 72 * currentZoomLevel / 64);
        Bitmap bitmap = Bitmap.createBitmap(CurrentPage.getWidth(),CurrentPage.getHeight(),
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

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // f
            String fileName = strings[1];  //
            String extStorageDirectory = getExternalCacheDir().toString();
            File folder = new File(extStorageDirectory, "aarzu1");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);

            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
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

    }

    }
