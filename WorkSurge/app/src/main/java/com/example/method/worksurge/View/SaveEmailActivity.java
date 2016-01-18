package com.example.method.worksurge.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.method.worksurge.R;

import java.io.OutputStreamWriter;

public class SaveEmailActivity extends AppCompatActivity {

    private final static String STORETEXT = "storeEmailText.txt";
    private EditText txtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtEditor = (EditText)findViewById(R.id.TextEmail);
    }

    public void saveEmail(View v){
        try{
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(STORETEXT, 0));
            out.write(txtEditor.getText().toString());
            Toast.makeText(this, "Email is saved", Toast.LENGTH_SHORT).show();
        }catch(Throwable t){
            Toast.makeText(this, "Error"+t.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
