package com.example.method.worksurge.View;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.method.worksurge.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private ListView list;
    private TextView message;
    private Cursor cursor;
    private int save = -1;

    //Database stuff for favorites
    static final String PROVIDER_NAME = "com.example.method.worksurge.ContentProvider.FavoriteProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/favorite";
    static final Uri CONTENT_URL = Uri.parse(URL);
    ContentResolver resolver;
    //End Database stuff for favorites

    //Class variables
    String itemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resolver = getContentResolver();

        list = (ListView) findViewById(R.id.liv_vacancies);
        message = (TextView) findViewById(R.id.txtMessage);
        message.setVisibility(View.GONE);
        // Retrieve DB content
        getFavorites();

        if(cursor == null)
        {
            message.setVisibility(View.VISIBLE);
            message.setText(getResources().getString(R.string.no_favorite));
        }
        else
        {
            message.setVisibility(View.GONE);
        }

        list.setLongClickable(true);

         list.setOnItemLongClickListener(new OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                 save = position;

                 //ListView Longclicked item index
                 int itemPosition = position;

                 //ListView clicked item value
                 itemValue = (String) list.getItemAtPosition(position);

                 // Retrieve ID
                 if (itemValue.contains(" "))
                     itemValue = itemValue.substring(0, itemValue.indexOf(" "));

                 deleteFavorite();

                 return false;
             }
         });
    }

    public void getFavorites() {
        String[] projection = new String[]{"id", "name"};

        cursor = resolver.query(CONTENT_URL, projection, null, null, null);

        List<String> favoriteListArray = new ArrayList<String>();

        if (cursor == null)
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_record), Toast.LENGTH_LONG).show();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    favoriteListArray.add(id + " " + name + "\n");
                } while (cursor.moveToNext());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, favoriteListArray);
        list.setAdapter(adapter);
    }

    public void deleteFavorite() {
        long idDeleted = resolver.delete(CONTENT_URL, "id = ? ", new String[]{itemValue});
        getFavorites();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_record), Toast.LENGTH_LONG).show();
    }

}
