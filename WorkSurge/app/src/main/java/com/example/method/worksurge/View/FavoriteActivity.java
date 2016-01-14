package com.example.method.worksurge.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.method.worksurge.R;

public class FavoriteActivity extends AppCompatActivity {
    private ListView list;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = (ListView) findViewById(R.id.liv_vacancies);
        message = (TextView) findViewById(R.id.txtMessage);
        message.setVisibility(View.GONE);
        // Retrieve DB content

        // Check if content = 0

        // Tell the user to favorite something
        list.setVisibility(View.GONE);
        message.setVisibility(View.VISIBLE);
        message.setText("Nothing to show! \n Please favorite something first.");
    }

}
