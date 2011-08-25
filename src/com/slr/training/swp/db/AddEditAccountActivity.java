package com.slr.training.swp.db;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditAccountActivity extends Activity implements OnClickListener{
	
	public static final String EXTRA_MODE = "MODE";
	public static final String EXTRA_ACCOUNT_MODEL = "ACC_MODEL";
	public static final int MODE_ADD = 100;
	public static final int MODE_EDIT = 101;
	
	
	EditText etName;
	EditText etAccountNo;
	EditText etBank;
	Button btSave;
	Button btCancel;
	
	DatabaseHelper dbHelper;
	
	int mode;
	
	AccountModel accountModel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_acc);
		
		mode = getIntent().getExtras().getInt(EXTRA_MODE);
		
		etName = (EditText) findViewById(R.id.aa_et_name);
		etAccountNo = (EditText) findViewById(R.id.aa_et_accno);
		etBank = (EditText) findViewById(R.id.aa_et_bank);
		btSave = (Button) findViewById(R.id.aa_bt_save);
		btCancel = (Button) findViewById(R.id.aa_bt_cancel);

		if(mode == MODE_EDIT) {
			accountModel = (AccountModel) getIntent().getExtras().getSerializable(EXTRA_ACCOUNT_MODEL);
			etName.setText(accountModel.name);
			etAccountNo.setText(accountModel.accountNo);
			etBank.setText(accountModel.bank);
		}
		
		dbHelper = new DatabaseHelper(this);
		
		btSave.setOnClickListener(this);
		btCancel.setOnClickListener(this);
	}

//	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aa_bt_save:
			if(mode == MODE_ADD) {
				addAccountToDB();
				finish();
			}else if(mode == MODE_EDIT){
				editAccountToDB();
				finish();
			}
			break;
		case R.id.aa_bt_cancel:
			finish();
			break;

		default:
			break;
		}
	}
	
	private void addAccountToDB() {
		String name = etName.getText().toString();
		String accNo = etAccountNo.getText().toString();
		String bank = etBank.getText().toString();
		AccountModel accModel = new AccountModel();
		accModel.name = name;
		accModel.accountNo = accNo;
		accModel.bank = bank;
		dbHelper.addAccount(accModel);
	}
	
	private void editAccountToDB() {
		String name = etName.getText().toString();
		String accNo = etAccountNo.getText().toString();
		String bank = etBank.getText().toString();
		accountModel.name = name;
		accountModel.accountNo = accNo;
		accountModel.bank = bank;
		dbHelper.editAccount(accountModel);
	}
}
