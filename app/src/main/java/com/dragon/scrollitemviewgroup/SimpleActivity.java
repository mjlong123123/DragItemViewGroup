package com.dragon.scrollitemviewgroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleActivity extends AppCompatActivity {

	TextView mainView;
	TextView menuView;
	ViewGroup viewGroup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple);
		viewGroup = findViewById(R.id.mainLayout);
		mainView = findViewById(R.id.mainItem);
		menuView = findViewById(R.id.menuItem);
	}

	public void onClickItem(View view){
		Log.e("dragon", "onClickItem");
		viewGroup.requestLayout();
	}
	public void onClickMenu(View view){
		Log.e("dragon", "onClickMenu");
		viewGroup.requestLayout();
	}
}
