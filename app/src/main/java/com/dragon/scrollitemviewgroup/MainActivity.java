package com.dragon.scrollitemviewgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClickStartSimpleActivity(View view){
		Intent intent = new Intent();
		intent.setClass(this, SimpleActivity.class);
		startActivity(intent);
	}
	public void onClickStartListActivity(View view){
		Intent intent = new Intent();
		intent.setClass(this, ListActivity.class);
		startActivity(intent);
	}
}
