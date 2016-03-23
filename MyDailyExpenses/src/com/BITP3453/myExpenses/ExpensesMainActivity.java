package com.BITP3453.myExpenses;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.example.helloworld.R;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import generic.ExpensesDB;
import generic.WebServiceCall;

public class ExpensesMainActivity extends AppCompatActivity {

	EditText edtExpName, edtExpPrice,edtTime,edtDate;
	
	WebServiceCall wsc = new WebServiceCall();
	String  strDate, strTime,strMsg ;
	ExpensesDB dbMyExpenses;
	JSONObject jsnObj = new JSONObject();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_main);
		
		dbMyExpenses = new ExpensesDB(getApplicationContext());
		edtExpName = (EditText) findViewById(R.id.edtExpName);
		edtExpPrice = (EditText) findViewById(R.id.edtPrice);
		edtTime = (EditText) findViewById(R.id.edtTime);
		edtDate = (EditText) findViewById(R.id.edtDate);
			  
		//try get Date and time from server 	 
		Runnable run = new Runnable() {		
			@Override
			public void run() {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("selectFn", "fnGetDateTime"));
				
				try {
					jsnObj = wsc.makeHttpRequest(wsc.fnGetURL(), "POST", params);
					strDate = jsnObj.getString("currDate");
					strTime = jsnObj.getString("currTime");
					strMsg = "Successfully retrieve date and time from server! ";
				} catch (Exception e) {
					//if fail to get from server, get from local mobile time				
					strMsg ="Unable to connect to server..Getting mobile date and time";
					strDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
					strTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
				}			
				runOnUiThread(new Runnable() {									 
					public void run() {
						fnDisplayToastMSg(strMsg);
						edtTime.setText(strTime);
						edtDate.setText(strDate);					
					}
				});
				
			}
		};
		
		Thread thrDateTime = new Thread(run);
		thrDateTime.start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hello_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			fnActvListExp(this.getCurrentFocus());
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void fnSave(View vw)
	{
		Runnable run2 = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				String strExpname = edtExpName.getText().toString();
				String strPrice = edtExpPrice.getText().toString();
				String strDate = edtDate.getText().toString();
				
				int intNewId = dbMyExpenses.fnTotalRow() + 1;
				String strQry = "Insert into expenses values('"+ intNewId +"','"+strExpname+"','"+ strPrice +"', '"+ strDate +"');";
				 
				dbMyExpenses.fnExecuteSql(strQry, getApplicationContext()); 
				
				runOnUiThread(new Runnable() {
					
					 
					public void run() {
						// TODO Auto-generated method stub
						Toast showSuccess = Toast.makeText(getApplicationContext(), "Information Successfully Saved! ", Toast.LENGTH_SHORT);
						showSuccess.show();
					}
				});
				
			}
		};
		
		Thread thrSave = new Thread(run2);
		thrSave.start();
		
	}
	
	public void fnActvListExp(View vw)
	{
		Intent intent = new Intent(this, ExpensesListActivity.class);
		startActivityForResult(intent, 0);
		
		
	}
	
	public void fnDisplayToastMSg(String strText)
	{
		Toast tst = Toast.makeText(getApplicationContext(), strText, Toast.LENGTH_LONG);
		tst.show();
		
	}
	
}
