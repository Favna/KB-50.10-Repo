package com.example.method.worksurge.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.method.worksurge.Enum.FragmentEnum;
import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyDetailModel;
import com.example.method.worksurge.Model.VacancyMapDetail;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;
import com.example.method.worksurge.WebsiteConnector.WebsiteConnector;

import java.util.ArrayList;
import java.util.List;

public class FoundVacanciesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<VacancyModel> list = null;
    private ArrayList<VacancyMapDetail> mapList = null;
    private WebsiteConnector wc = new WebsiteConnector();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_vacancies);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Handle Intent
        list = this.getIntent().getParcelableArrayListExtra(IntentEnum.FOUND_MULTIPLE_VACANCIES.toString());
        mapList = this.getIntent().getParcelableArrayListExtra(IntentEnum.FOUND_MULTIPLE_MAP_VACANCIES.toString());
        FragmentEnum chosen = (FragmentEnum) this.getIntent().getSerializableExtra(IntentEnum.DECISION.toString());

        // Set User's chosen preference
        viewPager.setCurrentItem(chooseFragmentTitle(chosen));
    }

    /*
        ViewPager & FragmentPage adapters to make tabs work
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ListFragment(), chooseFragmentTitle(0));
        adapter.addFrag(new MapFragment(), chooseFragmentTitle(1));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private String chooseFragmentTitle(int num)
    {
        switch(num)
        {
            case 0:
                return FragmentEnum.LIST.toString();
            case 1:
                return FragmentEnum.MAP.toString();
            default:
                return FragmentEnum.LIST.toString();
        }
    }

    private int chooseFragmentTitle(FragmentEnum name)
    {
        switch(name)
        {
            case LIST:
                return 0;
            case MAP:
                return 1;
            default:
                return 0;
        }
    }

    public ArrayList<VacancyModel> getVacancyList() {
        return this.list;
    }

    public ArrayList<VacancyMapDetail> getVacancyMapList() { return this.mapList; }

    public WebsiteConnector getWebsiteConnector() { return this.wc; }

    public void ReadWebsiteAsync(int pos, FragmentEnum decision) {
        switch(decision)
        {
            case LIST:
                new ReadWebsiteAsync(getApplicationContext(), this).execute(
                        list.get(pos).getURL()
                );
                break;
            case MAP:
                new ReadWebsiteAsync(getApplicationContext(), this).execute(
                        mapList.get(pos).getVacancyModel().getURL()
                );
                break;
            default:
                break;
        }
    }

    public boolean checkConnectivity()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private class ReadWebsiteAsync extends AsyncTask<String, Void, Boolean> {
        private Context context;
        private ProgressDialog dialog;
        private String[] params;
        private VacancyDetailModel model = new VacancyDetailModel();

        private ReadWebsiteAsync(Context context, FoundVacanciesActivity activity) {
            this.context = context;
            this.dialog = new ProgressDialog(activity);
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
            if(dialog.isShowing())
                dialog.dismiss();

            if(result)
            {
                if(model != null ? model.getTitle().isEmpty() : false)
                {
                    Toast.makeText(context, getResources().getString(R.string.error_unexpected), Toast.LENGTH_LONG).show();
                    return;
                }

                model.setUrl(params[0]);

                Intent iDetailActivity = new Intent(getApplicationContext(), DetailActivity.class);
                iDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iDetailActivity.putExtra(IntentEnum.FOUND_SINGLE_VACANCY.toString(), model);
                startActivity(iDetailActivity);
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
}
