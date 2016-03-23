package generic;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class ExpensesDB extends SQLiteOpenHelper {

	public static final String dbName = "dbMyExpense";
	public static final String tblName = "expenses";
	public static final String colExpName = "expenses_name";
	public static final String colExpPrice = "expenses_price";
	public static final String colExpDate = "exp_date";
	public static final String colExpId = "exp_id";
	
	 public ExpensesDB(Context context) {

		 super(context,dbName,null,1);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS expenses(exp_id VARCHAR,exp_name VARCHAR,exp_price VARCHAR, exp_date DATE );");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS expenses");
        onCreate(db);
	}
	
	public Cursor getDataById(int id )
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("Select * from "+tblName+" where "+colExpId +"= "+id, null);
		
		return cur;
	}
	
	public void fnExecuteSql(String strSql, Context appContext)
	{
		try {
			
			SQLiteDatabase db = this.getReadableDatabase();
			db.execSQL(strSql);
			
		 
		} catch (Exception e) {
			 Log.d("unable to run query", "error!");
		}
		
		
	}
	
	public int fnTotalRow()
	{
	   int intRow;
	   SQLiteDatabase db = this.getReadableDatabase();
	   intRow = (int) DatabaseUtils.queryNumEntries(db, tblName);
	   
	   return intRow;	
		
	}

}
