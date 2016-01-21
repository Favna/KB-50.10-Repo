package com.example.method.worksurge.View;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    //Database stuff for favorites
    static final String PROVIDER_NAME = "com.example.method.worksurge.ContentProvider.FavoriteProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/favorite";
    static final Uri CONTENT_URL = Uri.parse(URL);
    ContentResolver resolver;
    //End Database stuff for favorites

    //Class variables
    private String itemValue;
    private ListView list;
    private TextView message;
    private Cursor cursor;
    private int save = -1;

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

        // Initialize onClickListeners for the List
        initListeners();
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete:
                deleteFavorite();
                return true;
            default:
                return false;
        }
    }

    // Initialize all listeners for the listView
    private void initListeners()
    {
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

                PopupMenu popupMenu = new PopupMenu(FavoriteActivity.this, view);
                popupMenu.setOnMenuItemClickListener(FavoriteActivity.this);
                popupMenu.inflate((R.menu.popup_menu));
                popupMenu.show();

                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;

                Toast.makeText(getApplicationContext(), "Seeing your Favorite details is not complete yet", Toast.LENGTH_LONG).show();
                // Start DetaiLActivity
                /*
                Intent iDetailActivity = new Intent(view.getContext(), DetailActivity.class);
                iDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iDetailActivity.putExtra(IntentEnum.FOUND_SINGLE_VACANCY.toString(), favoriteListArray.get(itemPosition));
                view.getContext().startActivity(iDetailActivity);
                */
            }
        });
    }

    // Retrieve all the favorites of the user
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

    // Retrieve Favorite information from the Content provider to pass to the DetailActivity
    private VacancyDetailModel getSingleFavoriteInformation(int favoriteId)
    {
        String[] projection = new String[]{"id", "name", "details", "companyUrl", "meta"};

        cursor = resolver.query(CONTENT_URL, projection, null, null, null);

        VacancyDetailModel singleFavorite = null;

        if (cursor == null)
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_record), Toast.LENGTH_LONG).show();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String details = cursor.getString(cursor.getColumnIndex("details"));
                    String companyUrl = cursor.getString(cursor.getColumnIndex("companyUrl"));
                    String meta = cursor.getString(cursor.getColumnIndex("meta"));

                    // TODO: Initialize model
                    //VacancyDetailModel = new VacancyDetailModel(name, details, companyUrl, meta)
                } while (cursor.moveToNext());
            }
        }

        return singleFavorite;
    }

    // Delete a single favorite
    public void deleteFavorite() {
        long idDeleted = resolver.delete(CONTENT_URL, "id = ? ", new String[]{itemValue});
        getFavorites();
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.item_deleted), Toast.LENGTH_LONG).show();
    }

}
