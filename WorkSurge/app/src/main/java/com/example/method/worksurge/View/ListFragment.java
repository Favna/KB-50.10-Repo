package com.example.method.worksurge.View;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;
import com.example.method.worksurge.WebsiteConnector.WebsiteConnector;

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
    private WebsiteConnector wc = null;
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

        final ArrayList<VacancyModel> list = ((FoundVacanciesActivity) getActivity()).getVacancyList();
        ArrayList<String> list_temp = new ArrayList<String>();

        for(VacancyModel element : list)
        {
            list_temp.add(element.getTitle());
        }

        if(list != null)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, list_temp);

            liv_vacancy.setAdapter(adapter);

            liv_vacancy.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            liv_vacancy.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    /*parent.getChildAt(position).setBackgroundColor(
                            Color.parseColor("#A9BCF5"));

                    if (save != -1 && save != position) {
                        parent.getChildAt(save).setBackgroundColor(
                                Color.parseColor("#d6e6ff"));
                    }*/

                    save = position;

                    // ListView Clicked item index
                    int itemPosition = position;

                    // ListView Clicked item value
                    itemValue = (String) liv_vacancy.getItemAtPosition(position);

                    wc = new WebsiteConnector();
                    new ReadWebsiteAsync(view.getContext().getApplicationContext()).execute(
                            list.get(itemPosition).getURL()
                    );
                }
            });
        }
        else
        {
            Toast.makeText(view.getContext(), "An unexpected error prevented showing your information", Toast.LENGTH_LONG).show();
        }
    }

    private class ReadWebsiteAsync extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private String[] params;
        private VacancyDetailModel model = new VacancyDetailModel();

        private ReadWebsiteAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            this.params = params;
            VacancyDetailModel test = new VacancyDetailModel();
            model = wc.readWebsite(params[0]);
            return true; // Return false if reading is unsuccesful
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if(result)
            {
                if(model != null ? model.getTitle().isEmpty() : false)
                {
                    Toast.makeText(context, "Something went wrong with showing the vacancy...", Toast.LENGTH_LONG).show();
                    return;
                }

                model.setUrl(params[0]);

                Intent iDetailActivity = new Intent(view.getContext(), DetailActivity.class);
                iDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iDetailActivity.putExtra(IntentEnum.FOUND_SINGLE_VACANCY.toString(), model);
                view.getContext().startActivity(iDetailActivity);
            }
            else
            {
                Toast.makeText(context, "Couldn't connect to the online Database", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
