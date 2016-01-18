package com.example.method.worksurge.View;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.method.worksurge.R;

import java.io.OutputStreamWriter;

/**
 * Created by Gebruiker on 18-1-2016.
 */
public class SaveViewPreferenceActivity extends AppCompatActivity {

    private final static String STORETEXT = "storeViewText.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void saveViewPreference(View v){
        RadioGroup rg = (RadioGroup)findViewById(R.id.radiobtngroupViewPreference);
        int selected = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton)findViewById(selected);
        try{
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(STORETEXT, 0));
            out.write(rb.getText().toString());
            Toast.makeText(this, "View preference is saved", Toast.LENGTH_SHORT).show();
        }catch(Throwable t){
            Toast.makeText(this, "Error"+t.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
