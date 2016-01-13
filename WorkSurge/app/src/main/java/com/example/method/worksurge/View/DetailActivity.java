package com.example.method.worksurge.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;

/**
 * Created by Nino on 4-1-2016.
 */
public class DetailActivity extends AppCompatActivity {

    private TextView title, undertitle, details, url;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setViewText((VacancyModel) getIntent().getParcelableExtra(IntentEnum.FOUND_SINGLE_VACANCY.toString()));

    }

    private void setViewText(VacancyModel model) {
        this.title = (TextView) findViewById(R.id.txtCustomTitle);
        this.undertitle = (TextView) findViewById(R.id.txtCustomUndertitle);
        this.details = (TextView) findViewById(R.id.txtCustomDetails);
        this.url = (TextView) findViewById(R.id.txtCustomUrl);

        this.title.setText(model.getTitle());
        this.undertitle.setText(model.getUndertitle());
        this.details.setText(model.getDetails());
        this.url.setText(Html.fromHtml("<a href=\"" + model.getURL() + "\">" + model.getURL() + "</a>"));
        this.url.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void call(View v) {
        String temp_number = "06-22488840";
        Intent intentCall = new Intent(Intent.ACTION_DIAL);
        intentCall.setData(Uri.parse("tel:" + temp_number));
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
        emailIntent.putExtra(Intent.EXTRA_TEXT, url.getText());

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
