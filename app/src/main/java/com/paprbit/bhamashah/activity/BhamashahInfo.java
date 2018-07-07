package com.paprbit.bhamashah.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.paprbit.bhamashah.R;
import com.paprbit.bhamashah.fragments.FragmentBhamashahInfo;

import butterknife.ButterKnife;

public class BhamashahInfo extends AppCompatActivity {
    private String html0;
    private String html1;
    private String html2;

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bhamashah_info);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_activity_home));
        getSupportActionBar().setTitle(getString(R.string.title_activity_home));

        html0 = "<b>" + getString(R.string.about_bhamashah_yojna) + "</b><br><br>"
                + getString(R.string.bhamashah_info_text) +
                "<br><br><b>" + getString(R.string.bahamashah_benefits_title) + "</b><br><br>" +
                getString(R.string.bhamashah_benefits) + "<br><br>";
        html1 = "<b>" + getString(R.string.about_bhamashah_swasthya_yojna) + "</b><br><br>"
                + getString(R.string.bhamashah_health_text) +
                "<br><br><b>" + getString(R.string.bahamashah_health_benefits_title) + "</b><br><br>" +
                getString(R.string.bhamashah_health_benefits) + "<br><br>";
        html2 = "<b>" + getString(R.string.about_bhamashah_rojgar_srijan_yojna) + "</b><br><br>"
                + getString(R.string.bhamashah_rojgar_info_text) +
                "<br><br><b>" + getString(R.string.bahamashah_rojgar_benefits_title) + "</b><br><br>" +
                getString(R.string.bhamashah_rojgar_benefits) + "<br><br>";

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new FragmentBhamashahInfo(html0);
                case 1:
                    return new FragmentBhamashahInfo(html1);
                case 2:
                    return new FragmentBhamashahInfo(html2);
                default:
                    return new FragmentBhamashahInfo(html0);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.about_bhamashah_yojna);
                case 1:
                    return getString(R.string.about_bhamashah_swasthya_yojna);
                case 2:
                    return getString(R.string.about_bhamashah_rojgar_srijan_yojna);
            }
            return null;
        }
    }
}
