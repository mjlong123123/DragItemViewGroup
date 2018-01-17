package com.dragon.scrollitemviewgroup;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dragon.scrollitemviewgroup.dummy.DummyContent;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

public class ListActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

	ViewPager viewPager;
	TabLayout tabLayout;

	String[] tabs = new String[]{"tab1", "tab2", "tab3", "tab4", "tab5", "tab6", "tab7", "tab8", "tab9", "tab10", "tab11"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		init();
	}

	private void init() {
		viewPager = findViewById(R.id.viewPager);
		viewPager.setAdapter(new CustomFragmentAdapter(getSupportFragmentManager()));
		tabLayout = findViewById(R.id.tabLayout);
		tabLayout.setTabMode(MODE_SCROLLABLE);
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	public void onListFragmentInteraction(DummyContent.DummyItem item) {
		Log.e("dragon", "onListFragmentInteraction");
	}

	public class CustomFragmentAdapter extends FragmentStatePagerAdapter {
		public CustomFragmentAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return ItemFragment.newInstance(1, tabs[position]);
		}

		@Override
		public int getCount() {
			return tabs.length;
		}

		@Nullable
		@Override
		public CharSequence getPageTitle(int position) {
			return tabs[position];
		}
	}
}
