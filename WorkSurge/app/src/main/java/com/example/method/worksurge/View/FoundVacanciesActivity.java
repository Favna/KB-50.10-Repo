package com.example.method.worksurge.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.method.worksurge.Enum.FragmentEnum;
import com.example.method.worksurge.Enum.IntentEnum;
import com.example.method.worksurge.Model.VacancyModel;
import com.example.method.worksurge.R;

import java.util.ArrayList;
import java.util.List;

public class FoundVacanciesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<VacancyModel> list = null;

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
}
