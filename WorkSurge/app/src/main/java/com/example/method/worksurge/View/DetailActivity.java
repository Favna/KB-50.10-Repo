package com.example.method.worksurge.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;

/**
 * Created by Nino on 4-1-2016.
 */
public class DetailActivity extends AppCompatActivity {

    private TextView title, meta, details, company;
    private Button telefoon;
    private VacancyDetailModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        this.model = getIntent().getParcelableExtra(IntentEnum.FOUND_SINGLE_VACANCY.toString());
        setViewText(this.model);

        telefoon = (Button) findViewById(R.id.btn_call);
    }

    private void setViewText(VacancyDetailModel model) {
        this.title = (TextView) findViewById(R.id.txtCustomTitle);
        this.meta = (TextView) findViewById(R.id.txtCustomMeta);
        this.details = (TextView) findViewById(R.id.txtCustomDetails);
        this.company = (TextView) findViewById(R.id.txtCustomCompany);

        this.title.setText(model.getTitle());
        this.meta.setText(model.getMeta().get(0)); // TODO: show all meta data
        this.details.setText(model.getDetail());
        this.company.setText(model.getCompany());
    }

    public void call(View v) {
        if(model.getTelefoon().isEmpty())
            telefoon.setEnabled(false);

        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:" + model.getTelefoon()));
        startActivity(intentCall);
    }

    // TODO: Send email on seperate thread
    public void email(View v) {
        String[] TO = {"test@test.nl"}; // TODO: Retrieve user email
        TextView title = (TextView) findViewById(R.id.txtCustomTitle);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "WorkSurge: " + title.getText());
        emailIntent.putExtra(Intent.EXTRA_TEXT, model.getUrl());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Toast.makeText(getApplicationContext(), "Your email has been sent to: " + TO[0], Toast.LENGTH_LONG).show();
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DetailActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
