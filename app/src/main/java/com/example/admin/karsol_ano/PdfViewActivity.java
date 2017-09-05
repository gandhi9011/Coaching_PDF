package com.example.admin.karsol_ano;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class PdfViewActivity extends AppCompatActivity {
PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        pdfView=(PDFView)findViewById(R.id.pdf);
        File pdfFile = new File(getCacheDir() + "/testthreepdf/" + "testing.pdf");
        Uri path = Uri.fromFile(pdfFile);
        pdfView.fromUri(path).load();
    }
}
