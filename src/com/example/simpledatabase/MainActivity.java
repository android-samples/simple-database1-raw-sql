package com.example.simpledatabase;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
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
		// execSQLメソッド版
		try{
			db.execSQL("INSERT INTO messages(body) VALUES('abc')");
			addResult("execSQL版が成功しました");
		}
		catch(Exception ex){
			addResult("execSQL版が失敗しました");
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
			addResult("insert版が成功しました");
		}
		catch(Exception ex){
			addResult("insert版が失敗しました");
		}
	}
	
	// SELECT
	public void buttonMethodSelect(View v){
		clearResult();
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		// raqQueryメソッド版
		try{
			Cursor c = db.rawQuery("SELECT _id, body FROM messages", null);
			while(c.moveToNext()){
				int id = c.getInt(c.getColumnIndex("_id")); // ※ c.getString(0) と同じ
				String body = c.getString(c.getColumnIndex("body")); // ※ c.getString(1) と同じ
				addResult(id + ":" + body);
			}
			addResult("rawQuery版が成功しました");
		}
		catch(Exception ex){
			addResult("rawQuery版が失敗しました");
		}
		
		// queryメソッド版
		try{
			Cursor c = db.query(
				"messages",						// テーブル名
				new String[]{"_id", "body"},	// 選択するカラム群
				null,							// selection
				null,							// selectionArgs
				null,							// group by
				null,							// having
				null							// order by
			);
			while(c.moveToNext()){
				int id = c.getInt(c.getColumnIndex("_id")); // ※ c.getString(0) と同じ
				String body = c.getString(c.getColumnIndex("body")); // ※ c.getString(1) と同じ
				addResult(id + ":" + body);
			}
			addResult("query版が成功しました");
		}
		catch(Exception ex){
			addResult("query版が失敗しました");
		}
	}
	
	public void buttonMethodUpdate(View v){
		clearResult();
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		// execSQLメソッド版
		try{
			db.execSQL("UPDATE messages SET body = 'xyz' WHERE _id <= 2"); // _id = 1, 2 のレコードを更新
			addResult("execSQL版が成功しました");
		}
		catch(Exception ex){
			addResult("execSQL版が失敗しました");
		}
		
		// queryメソッド版
		try{
			ContentValues values = new ContentValues();
			values.put("body", "XYZ");
			db.update(
				"messages",				// テーブル名
				values,					// 値群。
				"_id >=3 AND _id <= 4", // 条件。 _id = 3, 4 のレコードを更新
				null // where args
			);
			addResult("update版が成功しました");
		}
		catch(Exception ex){
			addResult("update版が失敗しました");
		}
	}

	public void buttonMethodDelete(View v){
		clearResult();
		MyDbHelper helper = new MyDbHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		
		// execSQLメソッド版
		try{
			db.execSQL("DELETE FROM messages WHERE _id <= 2"); // _id = 1, 2 のレコードを削除
			addResult("execSQL版の削除が成功しました");
		}
		catch(Exception ex){
			addResult("execSQL版の削除が失敗しました");
		}
		
		// queryメソッド版
		try{
			db.delete(
				"messages",				// テーブル名
				"_id >=3 AND _id <= 4", // 条件。 _id = 3, 4 のレコードを削除
				null // where args
			);
			addResult("delete版の削除が成功しました");
		}
		catch(Exception ex){
			addResult("delete版の削除が失敗しました");
		}
	}
}
