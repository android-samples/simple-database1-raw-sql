package com.example.simpledatabase;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	ListView mListView;
	ArrayList<String> mData = new ArrayList<String>();
	ArrayAdapter<String> mAdapter; 
	
	// DB結合用アダプタ
	// SimpleCursorAdapter mAdapter2;

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
		
		// DB
		// new SimpleCursorAdapter(this, )
	}
	
	// 結果表示
	public void clearResult(){
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText("");
	}
	public void addResult(String result){
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(textView.getText().toString() + "\n" + result);
	}
	
	// まずは挿入する
	public void buttonMethodInsert(View v){
		clearResult();
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		// 直接SQL版
		try{
			db.execSQL("INSERT INTO messages(body) VALUES('abc')");
			addResult("execSQLが成功しました");
		}
		catch(Exception ex){
			addResult("execSQLが失敗しました");
		}
		// insertメソッド版
		try{
			ContentValues values = new ContentValues();
			values.put("body", "def");
			db.insert(
				"messages", // テーブル名
				null,		// データを挿入する際にnull値が許可されていないカラムに代わりに利用される値
				values		// 値群
			);
			addResult("insertメソッドが成功しました");
		}
		catch(Exception ex){
			addResult("insertメソッドが失敗しました");
		}
	}
}
