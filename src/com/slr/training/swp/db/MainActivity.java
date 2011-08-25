package com.slr.training.swp.db;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class MainActivity extends Activity implements OnClickListener{

	TableLayout tlTable;
	Button btAddAccount;

	DatabaseHelper dbHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		tlTable = (TableLayout) findViewById(R.id.main_tl_table);
		btAddAccount = (Button) findViewById(R.id.main_bt_add);

		dbHelper = new DatabaseHelper(this);
		
		btAddAccount.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();
		ArrayList<AccountModel> accountList = dbHelper.getAccountAll();
		tlTable.removeAllViews();
		for (AccountModel accModel : accountList) {
			TableRow tr = new TableRow(this);
			LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			tr.setLayoutParams(layoutParams);
			tr.setBackgroundDrawable(getResources().getDrawable(R.drawable.row_bg_shape));

			AccountRowLayout arl = new AccountRowLayout(this);
			arl.setAccountModel(accModel);
			tr.addView(arl);
			tlTable.addView(tr);
		}
	}

//	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, AddEditAccountActivity.class);
		intent.putExtra(AddEditAccountActivity.EXTRA_MODE, AddEditAccountActivity.MODE_ADD);
		startActivity(intent);
	}
}