package com.example.admin.karsol_ano;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class EnglishGujratiActivity extends AppCompatActivity {
    TextView tv1,tv2;
    LinearLayout english,gujrati;
    private static final String FILENAME = "basic1.pdf";

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


        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent pdfview=new Intent(EnglishGujratiActivity.this,PdfrenderActivity.class);
                File file = new File(getExternalCacheDir(), "/aarzu1/"+FILENAME);
                pdfview.putExtra("Pdfname",pdfname+"English");
                startActivity(pdfview);

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




}
