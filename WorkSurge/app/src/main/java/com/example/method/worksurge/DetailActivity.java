package com.example.method.worksurge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

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
}
