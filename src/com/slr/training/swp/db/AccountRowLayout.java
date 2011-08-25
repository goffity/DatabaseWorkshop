package com.slr.training.swp.db;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class AccountRowLayout extends LinearLayout implements OnClickListener{
	
	TextView tvName;
	TextView tvAccountNo;
	TextView tvBank;
	Button btEdit;
	Button btDelete;
	
	
	private AccountModel accountModel;
	
	public AccountRowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater layoutInflater = 
			(LayoutInflater) 
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.account_row_layout, this);
		tvName = (TextView) findViewById(R.id.arl_tv_name);
		tvAccountNo = (TextView) findViewById(R.id.arl_tv_accno);
		tvBank = (TextView) findViewById(R.id.arl_tv_bank);
		btEdit = (Button) findViewById(R.id.arl_bt_edit);
		btDelete = (Button) findViewById(R.id.arl_bt_delete);
		
		btEdit.setOnClickListener(this);
		btDelete.setOnClickListener(this);
		
	}

	public AccountRowLayout(Context context) {
		super(context);
		
		LayoutInflater layoutInflater = 
			(LayoutInflater) 
			context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.account_row_layout, this);
		tvName = (TextView) findViewById(R.id.arl_tv_name);
		tvAccountNo = (TextView) findViewById(R.id.arl_tv_accno);
		tvBank = (TextView) findViewById(R.id.arl_tv_bank);
		btEdit = (Button) findViewById(R.id.arl_bt_edit);
		btDelete = (Button) findViewById(R.id.arl_bt_delete);

		btEdit.setOnClickListener(this);
		btDelete.setOnClickListener(this);
	}
	
	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
		tvName.setText(this.accountModel.name);
		tvAccountNo.setText(this.accountModel.accountNo);
		tvBank.setText(this.accountModel.bank);
	}

//	@Override
	public void onClick(View v) {
		
		switch(v.getId()) {
		case R.id.arl_bt_edit:
			Intent intent = new Intent(getContext(), AddEditAccountActivity.class);
			intent.putExtra(AddEditAccountActivity.EXTRA_MODE, AddEditAccountActivity.MODE_EDIT);
			intent.putExtra(AddEditAccountActivity.EXTRA_ACCOUNT_MODEL, this.accountModel);
			getContext().startActivity(intent);
			break;
		case R.id.arl_bt_delete:
			DatabaseHelper dbHelper = new DatabaseHelper(getContext());
			dbHelper.deleteAccount(this.accountModel.id);
			TableLayout tl = (TableLayout) getParent().getParent();
			tl.removeView((TableRow)getParent());
			break;
			default:
		}
	}
	
	

}
