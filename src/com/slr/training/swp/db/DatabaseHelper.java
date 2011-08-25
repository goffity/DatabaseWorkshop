package com.slr.training.swp.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Button;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DB_VERSION = 1;
	private static final String DB_NAME = "acc_memo.db";

	private static final String DB_STRUCTURE_CREATE_TEXT_V1 = "CREATE TABLE 'tbl_account' (acc_id INTEGER PRIMARY KEY AUTOINCREMENT, acc_name TEXT, acc_bank TEXT, acc_no TEXT);";
	private static final String DB_STRUCTURE_CREATE_TEXT_V2 = "CREATE TABLE 'tbl_account' (acc_id INTEGER PRIMARY KEY AUTOINCREMENT, acc_name TEXT, acc_bank TEXT, acc_no TEXT, acc_phone TEXT);";

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDb) {
		sqliteDb.execSQL(DB_STRUCTURE_CREATE_TEXT_V1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDb, int oldVersion, int newVersion) {
		Log.e("DB Upgrade", "From "+oldVersion + " to " + newVersion);
		
		if(oldVersion == 1) {
			//rename tbl_account to tmp_tbl_account
			sqliteDb.execSQL("ALTER TABLE tbl_account RENAME TO temp_tbl_account;");
			
			//create table new version
			sqliteDb.execSQL(DB_STRUCTURE_CREATE_TEXT_V2);
			
			//copy data old->new
			String cols = "acc_id, acc_name, acc_bank, acc_no";
			String migrateSql = String.format("INSERT INTO tbl_account (%s) SELECT %s from temp_tbl_account", cols, cols);
			sqliteDb.execSQL(migrateSql);
			
			//drop tmp_tbl_account table
			sqliteDb.execSQL("DROP TABLE temp_tbl_account");
			
		}
		
	}
	
	public long addAccount(AccountModel accountModel) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("acc_name", accountModel.name);
		values.put("acc_no", accountModel.accountNo);
		values.put("acc_bank", accountModel.bank);
		long id = db.insert("tbl_account", null, values);
		db.close();
		return id;
	}
	
	public ArrayList<AccountModel> getAccountAll() {
		SQLiteDatabase db = getWritableDatabase();
		String cols[] = new String[]{"acc_id", "acc_name", "acc_bank", "acc_no"};
		Cursor cursor = db.query("tbl_account", cols, null, null, null, null, null);
		ArrayList<AccountModel> results = new ArrayList<AccountModel>();
		while(cursor.moveToNext()) {
			AccountModel accModel = new AccountModel();
			accModel.id = cursor.getInt(0);
			accModel.name = cursor.getString(1);
			accModel.bank = cursor.getString(2);
			accModel.accountNo = cursor.getString(3);
			results.add(accModel);
		}
		cursor.close();
		db.close();
		return results;
	}
	
	public void editAccount(AccountModel accModel) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("acc_name", accModel.name);
		values.put("acc_bank", accModel.bank);
		values.put("acc_no", accModel.accountNo);
		String whereClause =  "acc_id = ?";
		String args[] = new String[]{Integer.toString(accModel.id)};
		db.update("tbl_account", values, whereClause, args);
		db.close();
	}
	
	public int deleteAccount(int accId) {
		SQLiteDatabase db = getWritableDatabase();
		String whereClause = "acc_id=?";
		String args[] = new String[]{Integer.toString(accId)};
		int affectedRow = db.delete("tbl_account", whereClause, args);
		db.close();
		return affectedRow;
	}
	
	public static List<String> GetColumns(SQLiteDatabase db, String tableName) {
	    List<String> ar = null;
	    Cursor c = null;
	    try {
	        c = db.rawQuery("select * from " + tableName + " limit 1", null);
	        if (c != null) {
	            ar = new ArrayList<String>(Arrays.asList(c.getColumnNames()));
	        }
	    } catch (Exception e) {
	        Log.v(tableName, e.getMessage(), e);
	        e.printStackTrace();
	    } finally {
	        if (c != null)
	            c.close();
	    }
	    return ar;
	}
	
	public static String join(List<String> list, String delim) {
	    StringBuilder buf = new StringBuilder();
	    int num = list.size();
	    for (int i = 0; i < num; i++) {
	        if (i != 0)
	            buf.append(delim);
	        buf.append((String) list.get(i));
	    }
	    return buf.toString();
	}
}
