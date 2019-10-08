package com.dragon.scrollitemviewgroup;

import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dragon.scrollitemviewgroup.dummy.DummyContent;

import static com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE;

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
