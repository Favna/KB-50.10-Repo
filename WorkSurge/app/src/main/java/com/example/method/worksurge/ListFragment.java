package com.example.method.worksurge;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View view;
    private ListView liv_vacancy;
    private int save = -1;
    private String itemValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_list, container, false);

        fillList(); // Test data

        return view;
    }

    private void fillList() {
        liv_vacancy = (ListView) view.findViewById(R.id.liv_vacancies);

        List<String> list = new ArrayList<String>();
        list.add("test");
        list.add("test2");
        list.add("1");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, list);

        liv_vacancy.setAdapter(adapter);
        liv_vacancy.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                parent.getChildAt(position).setBackgroundColor(
                        Color.parseColor("#A9BCF5"));

                if (save != -1 && save != position) {
                    parent.getChildAt(save).setBackgroundColor(
                            Color.parseColor("#d6e6ff"));
                }

                save = position;

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                itemValue = (String) liv_vacancy.getItemAtPosition(position);
            }
        });
    }
}
