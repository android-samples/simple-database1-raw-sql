package com.example.simpledatabase;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	ListView mListView;
	ArrayList<String> mData = new ArrayList<String>();
	ArrayAdapter<String> mAdapter;
	int mSelected = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// widgets
		mListView = (ListView)findViewById(R.id.listView1);
		// mData.add("AA");
		// mData.add("BB");
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, mData);
		mListView.setAdapter(mAdapter);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mSelected = position;			
			}
		});
		clearResult();
		select();
	}
	
	// DBインターフェース取得
	SQLiteDatabase getDb(){
		// データベースファイルを開く。存在しなければ作って開く。
		SQLiteDatabase db = this.openOrCreateDatabase("test.db", MODE_PRIVATE, null);
		return db;
	}
	
	// 結果表示
	public void clearResult(){
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText("");
	}
	public void addResult(String result){
		TextView textView = (TextView)findViewById(R.id.textView1);
		textView.setText(textView.getText().toString() + result + "\n");
	}
	
	// 選択中のレコードのIDを取得
	public int getSelectedRecordId(){
		int i = mSelected; // mListView.getSelectedItemPosition();
		if(i >= 0 && i < mData.size()){
			return Integer.parseInt(mData.get(i).split(":")[0]);	
		}
		else{
			return -1;
		}
	}
	
	// CREATE TABLE
	public void buttonMethodCreate(View v){
		clearResult();
		SQLiteDatabase db = getDb();
		try{
			// db.execSQL("CREATE TABLE IF NOT EXISTS messages(_id INTEGER PRIMARY KEY AUTOINCREMENT, body VARCHAR)");
			db.execSQL("CREATE TABLE messages(_id INTEGER PRIMARY KEY AUTOINCREMENT, body VARCHAR)");
			addResult("CREATE TABLE 成功");
		}
		catch(Exception ex){
			addResult("CREATE TABLE 失敗: " + ex.getMessage());
		}
		select();
	}

	// DROP TABLE
	public void buttonMethodDrop(View v){
		clearResult();
		SQLiteDatabase db = getDb();
		try{
			// db.execSQL("DROP TABLE IF EXISTS messages");
			db.execSQL("DROP TABLE messages");
			addResult("DROP TABLE 成功");
		}
		catch(Exception ex){
			addResult("DROP TABLE 失敗: " + ex.getMessage());
		}
	}
	
	// INSERT
	public void buttonMethodInsert(View v){
		clearResult();
		SQLiteDatabase db = getDb();
		// execSQLメソッド版
		try{
			db.execSQL("INSERT INTO messages(body) VALUES('abc')");
			addResult("INSERT 成功");
		}
		catch(Exception ex){
			addResult("INSERT 失敗: " + ex.getMessage());
		}
		select();
	}
	
	// SELECT
	public void buttonMethodSelect(View v){
		clearResult();
		select();
	}
	public void select(){
		SQLiteDatabase db = getDb();
		
		// raqQueryメソッド版
		try{
			mData.clear();
			Cursor c = db.rawQuery("SELECT _id, body FROM messages", null);
			while(c.moveToNext()){
				int id = c.getInt(c.getColumnIndex("_id")); // ※ c.getString(0) と同じ
				String body = c.getString(c.getColumnIndex("body")); // ※ c.getString(1) と同じ
				// addResult(id + ":" + body);
				mData.add(id + ":" + body);
			}
			mAdapter.notifyDataSetChanged();
			addResult("SELECT 成功");
		}
		catch(Exception ex){
			addResult("SELECT 失敗: " + ex.getMessage());
		}
	}
	
	public void buttonMethodUpdate(View v){
		clearResult();
		SQLiteDatabase db = getDb();

		// 対象レコードID
		int id = getSelectedRecordId();
		if(id == -1){
			addResult("項目が選択されていません");
			return;
		}

		// execSQLメソッド版
		try{
			db.execSQL("UPDATE messages SET body = body || 'A' WHERE _id = " + id);
			addResult("UPDATE 成功");
		}
		catch(Exception ex){
			addResult("UPDATE 失敗: " + ex.getMessage());
		}
		select();
	}

	public void buttonMethodDelete(View v){
		clearResult();
		SQLiteDatabase db = getDb();
		
		// 対象レコードID
		int id = getSelectedRecordId();
		if(id == -1){
			addResult("項目が選択されていません");
			return;
		}
		
		// execSQLメソッド版
		try{
			db.execSQL("DELETE FROM messages WHERE _id = " + id);
			addResult("DELETE 成功");
		}
		catch(Exception ex){
			addResult("DELETE 失敗: " + ex.getMessage());
		}
		select();
	}
}
