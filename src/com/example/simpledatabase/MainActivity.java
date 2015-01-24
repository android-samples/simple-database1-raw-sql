package com.example.simpledatabase;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView mListView;
	ArrayList<String> mData = new ArrayList<String>();
	ArrayAdapter<String> mAdapter; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// widgets
		mListView = (ListView)findViewById(R.id.listView1);
		mData.add("AA");
		mData.add("BB");
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mData);
		mListView.setAdapter(mAdapter);
	}
}
