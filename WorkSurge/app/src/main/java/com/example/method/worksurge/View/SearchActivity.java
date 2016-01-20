package com.example.method.worksurge.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.method.worksurge.Enum.FragmentEnum;
import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;
import com.example.method.worksurge.WebsiteConnector.WebsiteConnector;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private WebsiteConnector wc = null;
    private FragmentEnum chosen = FragmentEnum.LIST;
    protected List<VacancyModel> list = null;

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
        switch(item.getItemId())
        {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                break;
            case R.id.action_about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                break;
            case R.id.action_favorites:
                startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                break;
            default:
                break;
        }

        return true;
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

    public void onClickList(View v)
    {
        chosen = FragmentEnum.LIST;
    }

    public void onClickMap(View v)
    {
        chosen = FragmentEnum.MAP;
    }

    // Go to foundVacanciesActivity.
    public void foundVacanciesActivity(View v) {
        // Can
        // it be more clean / better?
        EditText textSearchBox = (EditText) findViewById(R.id.txtSearch);
        Spinner spinnerKm = (Spinner) findViewById(R.id.static_spinner);
        int radius = Integer.parseInt(spinnerKm.getSelectedItem().toString().replaceAll("\\D+", "")); // KM radius, convert if non-standard
        String location = ""; // GPS Loc
        String activityChoice = "";

        new ReadWebsiteAsync(this.getApplicationContext(), SearchActivity.this).execute(
                new UserParam(textSearchBox.getText().toString(), radius, location)
        );

    }

    private class ReadWebsiteAsync extends AsyncTask<UserParam, Void, Boolean> {
        private Context context;
        private ProgressDialog dialog;

        private ReadWebsiteAsync(Context context, SearchActivity activity) {
            this.context = context;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected Boolean doInBackground(UserParam... params) {
            list = wc.readWebsite(params[0].searchCrit, params[0].radius, params[0].location);
            return true; // Return false if reading is unsuccesful
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(dialog.isShowing())
                dialog.dismiss();

            if(result)
            {
                if(list == null ? true : list.size() == 0)
                {
                    Toast.makeText(context, getResources().getString(R.string.no_vacancy), Toast.LENGTH_LONG).show();
                    return;
                }
                Intent iFoundVacanciesActivity = new Intent(context, FoundVacanciesActivity.class);
                iFoundVacanciesActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iFoundVacanciesActivity.putParcelableArrayListExtra(IntentEnum.FOUND_MULTIPLE_VACANCIES.toString(), (ArrayList<VacancyModel>) list);
                iFoundVacanciesActivity.putExtra(IntentEnum.DECISION.toString(), chosen);
                context.startActivity(iFoundVacanciesActivity);
            }
            else
            {
                Toast.makeText(context, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getResources().getString(R.string.loading));
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    private class UserParam {
        String searchCrit;
        int radius;
        String location;

        UserParam(String searchCrit, int radius, String location) {
            this.searchCrit = searchCrit;
            this.radius = radius;
            this.location = location;
        }
    }
}
