package com.example.method.worksurge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.method.worksurge.WebsiteConnector.WebsiteConnector;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SearchActivity extends AppCompatActivity {

    WebsiteConnector wc = null;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Spinner radiiSpinner = createRadiiSpinner();

        wc = new WebsiteConnector();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Spinner createRadiiSpinner(){
        //Create new Spinner object
        Spinner spinner = (Spinner) findViewById(R.id.static_spinner);
        //Create an ArrayAdapter for the items
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Radii, android.R.layout.simple_spinner_item);
        //Specify the list when using
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //return spinner
        return spinner;
    }

    // Go to foundVacanciesActivity.
    public void foundVacanciesActivity(View v) {
        // Can it be more clean / better?
        TextView textSearchBox = (TextView) findViewById(R.id.txtSearchBox);
        Spinner spinnerKm = (Spinner) findViewById(R.id.static_spinner);
        final String searchCrit = textSearchBox.getText().toString(); // SearchCriteria given by user
        final int radius = Integer.parseInt(spinnerKm.getSelectedItem().toString()); // KM radius, convert if non-standard
        final String location = ""; // GPS Loc
        final String activityChoice = "";

        executorService.execute(new Runnable() {
            public void run() {
                wc.readWebsite(searchCrit, radius, location);
            }
        });
        executorService.shutdown(); // Shutdown thread when done

        // clicking twice invokes same thread twice, thread is already at work or never shut down gives an exception.
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // Placeholder, show user the retrieval of data is taking time.

        } catch (InterruptedException e) {

        }
        Intent iFoundVacanciesActivity = new Intent(this, FoundVacanciesActivity.class);
        startActivity(iFoundVacanciesActivity);
    }
}
