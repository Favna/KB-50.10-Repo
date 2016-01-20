package com.example.method.worksurge.View;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.method.worksurge.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class SettingsActivity extends AppCompatActivity {

    private final static String STOREVIEWTEXT = "storeViewText.txt";
    private final static String STOREEMAILTEXT = "storeEmailText.txt";
    private EditText txtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtEditor = (EditText)findViewById(R.id.TextEmail);
        readEmailFile();
    }

    public void readEmailFile(){
        try{
            FileInputStream is = openFileInput(STOREEMAILTEXT);
            TextView currentEmail = (TextView)findViewById(R.id.currentEmail);
            Reader r = new InputStreamReader(is, "UTF-8");
            StringBuilder sb = new StringBuilder();
            int i = r.read();
            while(i >= 0){
                sb.append((char)i);
                i = r.read();
            }
            is.close();
            currentEmail.setText(sb);
            Toast.makeText(this, "Email found", Toast.LENGTH_LONG).show();

        }catch(java.io.FileNotFoundException fne){
            Toast.makeText(this, "Email file not found",Toast.LENGTH_LONG).show();
        }
        catch(Throwable t){
            Toast.makeText(this, "Exception"+ t.toString(),Toast.LENGTH_LONG).show();
        }
    }

    public void saveEmail(View v){
        try{
            FileOutputStream out = openFileOutput(STOREEMAILTEXT, Context.MODE_PRIVATE);
            String str = txtEditor.getText().toString();
            out.write(str.getBytes());
            Toast.makeText(this, "Email is saved", Toast.LENGTH_SHORT).show();
        }catch(Throwable t){
            Toast.makeText(this, "Error"+t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void saveViewPreference(View v){
        RadioGroup rg = (RadioGroup)findViewById(R.id.radiobtngroupViewPreference);
        int selected = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)findViewById(selected);
        try{
            FileOutputStream out = openFileOutput(STOREVIEWTEXT, Context.MODE_PRIVATE);
            if(rb.getText().toString().equalsIgnoreCase("map")) {
                String map = "map";
                out.write(map.getBytes());
            }else{
                String list = "list";
                out.write(list.getBytes());
            }
            Toast.makeText(this, "View preference is saved", Toast.LENGTH_SHORT).show();
        }catch(Throwable t){
            Toast.makeText(this, "Error"+t.toString(), Toast.LENGTH_LONG).show();
        }
    }

}