package com.BITP3453.myExpenses;

import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;

import com.example.helloworld.R;
import com.example.helloworld.R.id;
import com.example.helloworld.R.layout;
import com.example.helloworld.R.menu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ExpensesListActivity extends ActionBarActivity {

	SQLiteDatabase dbMyExpenses;
	 
	String strPrice,strExpName,strExpenses ="" ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expenses_list);
		
		Runnable run = new Runnable() {	
			@Override
			public void run() {
				
				dbMyExpenses = openOrCreateDatabase("dbMyExpense", MODE_PRIVATE, null);
				Cursor resultSet = dbMyExpenses.rawQuery("Select * from expenses;", null);
				
			    if(resultSet.moveToFirst())
			    {
			    	do
					{
						strExpName = resultSet.getString(resultSet.getColumnIndex("exp_name"));
						strPrice = resultSet.getString(resultSet.getColumnIndex("exp_price"));
						strExpenses += strExpName + ":RM"+strPrice+"\n"; 
					}while(resultSet.moveToNext());
			    }
				
				
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						 TextView txtVwExpenses = (TextView)findViewById(R.id.tvtView1);
						 txtVwExpenses.setText(strExpenses);
						 txtVwExpenses.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
					}
				});
			}
		};
		Thread thr = new Thread(run);
		thr.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expenses_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
