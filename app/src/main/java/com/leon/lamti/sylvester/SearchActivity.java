package com.leon.lamti.sylvester;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leon.lamti.sylvester.R;
import com.leon.lamti.sylvester.fragments.MapsFragment;
import com.leon.lamti.sylvester.fragments.SearchFragment;
import com.leon.lamti.sylvester.fragments.StatisticsFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;
    static private TabLayout tabLayout;
    static private ViewPager viewPager;
    static private int[] tabIcons = {
            R.mipmap.ic_action_action_dns,
            R.mipmap.ic_action_maps_place_2,
            R.mipmap.ic_action_action_trending_up
        };

    private String mUserInput;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.setStatusBarColor(Color.parseColor("#1f2968"));
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }*/
            setContentView(R.layout.activity_search);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

            Intent intent = getIntent();
            mUserInput = intent.getStringExtra("userText");

        if(toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(mUserInput.replace(".ss", ""));
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setDisplayShowHomeEnabled(true);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);



            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            setupTabIcons();

            //viewPager.setCurrentItem(2);

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                SharedPreferences sp = getSharedPreferences("sp", Context.MODE_PRIVATE);


                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                    if ( tab.getPosition() == 0 ) {

                        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_action_action_dns_pink);
                        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

                    } else if ( tab.getPosition() == 1) {

                        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_action_maps_place_pink);
                        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

                    } else {

                        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
                        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
                        tabLayout.getTabAt(2).setIcon(R.mipmap.ic_action_action_trending_up_pink);
                    }

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("flag1", false);
                    editor.commit();

                    if ( tab.getPosition() == 1 ) {


                        editor.putBoolean("flag1", true);
                        editor.commit();

                    } else {

                        editor.putBoolean("flag1", false);
                        editor.commit();
                    }

                    //Toast.makeText(SearchActivity.this, "tab: " + tab.getPosition() + sp.getBoolean("flag1", true), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        static private void setupTabIcons() {
            tabLayout.getTabAt(0).setIcon(R.mipmap.ic_action_action_dns_pink);
            tabLayout.getTabAt(1).setIcon(tabIcons[1]);
            tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        }

    static ViewPagerAdapter adapter;

        private void setupViewPager(ViewPager viewPager) {

            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new SearchFragment(), "Tweets");
            adapter.addFragment(new MapsFragment(), "Google Maps");
            adapter.addFragment(new StatisticsFragment(), "Statistics");
            viewPager.setAdapter(adapter);
        }

    public static void update( ) {

        adapter.notifyDataSetChanged();

        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Log.e("updateAdapter", "was called");
    }

    public static void changeTab( int i ) {

        viewPager.setCurrentItem(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
            private  List<Fragment> mFragmentList = new ArrayList<>();
            private  List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);

            }

        @Override
        public int getItemPosition(Object object) {
            //return super.getItemPosition(object);

            return POSITION_NONE;
        }

        @Override
            public Fragment getItem(int position) {

                //notifyDataSetChanged();
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                //return mFragmentTitleList.get(position);
                return null;
            }
        }
}
