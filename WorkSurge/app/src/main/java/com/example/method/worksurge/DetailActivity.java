package com.example.method.worksurge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyModel;

/**
 * Created by Nino on 4-1-2016.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setViewText((VacancyModel) getIntent().getParcelableExtra(IntentEnum.FOUND_SINGLE_VACANCY.toString()));

    }

    private void setViewText(VacancyModel model) {
        TextView title = (TextView) findViewById(R.id.txtCustomTitle);
        TextView undertitle = (TextView) findViewById(R.id.txtCustomUndertitle);
        TextView details = (TextView) findViewById(R.id.txtCustomDetails);

        title.setText(model.getTitle());
        undertitle.setText(model.getUndertitle());
        details.setText(model.getDetails());
    }

    public void call(View v) {
        String temp_number = "06-22488840";
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:" + temp_number));
        startActivity(intentCall);
    }

    public void email(View v) {
        String temp_email = "test@test.nl";
        Toast.makeText(getApplicationContext(), "Your email has been sent to: " + temp_email, Toast.LENGTH_LONG).show();
    }
}
