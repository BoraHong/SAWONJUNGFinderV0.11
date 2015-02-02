package com.nhncorp.student.sawonjungfinder;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nhncorp.student.sawonjungfinder.bluetooth.BlueToothEnabler;
import com.nhncorp.student.sawonjungfinder.constants.Constants;
import com.nhncorp.student.sawonjungfinder.database.DbOpenHelper;
import com.nhncorp.student.sawonjungfinder.finder.FinderActivity;
import com.nhncorp.student.sawonjungfinder.registration.RegistrationActivity;

public class MainActivity extends Activity {

	private BlueToothEnabler bluetooth;
	// private BeaconManagerActivity beaconManager;
	private TextView deviceNameText;
	private ImageButton finderBtn;
	private ImageButton registrationBtn;
	private ImageButton timeBtn;
	private ImageButton devOnOffBtn;

	private DbOpenHelper mDbOpenHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		getView();
		bluetooth = new BlueToothEnabler();
		boolean isBluetooth = bluetooth.enableBlueTooth();
		if (isBluetooth) {
			Toast.makeText(this, "블루투스가 작동 되고 있습니다.", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "해당 단말은 블루투스를 지원하지 않습니다.", Toast.LENGTH_LONG)
					.show();
		}
		// beaconManager = new BeaconManagerActivity();
		// beaconManager.setCentralManager(getApplicationContext());
		dbMake();
		deviceConfirm(0); // nothing
		deviceNameText.setText(Constants.DEVICE_NAME);
		addListener();
	}

	private void dbMake() {
		mDbOpenHelper = new DbOpenHelper(this);
		mDbOpenHelper.open();
		mDbOpenHelper.insertColumn(1, "기기를 등록해 주세요", "기기를 등록해 주세요", "0", "0");

	}

	private void deviceConfirm(int sel) { // 1: time 2: dev

		String deviceState;
		String alarmState;

		Cursor mCursor = mDbOpenHelper.getAll();
		mCursor.moveToFirst();
		System.out.println(mCursor.getString(mCursor.getColumnIndex("name")));// test
		System.out.println(mCursor.getString(mCursor
				.getColumnIndex("macaddress")));// test
		Constants.DEVICE_NAME = mCursor.getString(mCursor
				.getColumnIndex("name"));
		Constants.DEVICE_ADDRESS = mCursor.getString(mCursor
				.getColumnIndex("macaddress"));
		alarmState = mCursor.getString(mCursor.getColumnIndex("alarmstate"));
		deviceState = mCursor.getString(mCursor.getColumnIndex("devicestate"));

		if (sel == 2) { // dev

			if (deviceState.equals("0")) {
				mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
						Constants.DEVICE_ADDRESS, alarmState, "1");
				devOnOffBtn.setImageResource(R.drawable.power_orange);
				System.out.println("po"); // ///////////////////////
			} else if (deviceState.equals("1")) {
				mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
						Constants.DEVICE_ADDRESS, alarmState, "0");
				devOnOffBtn.setImageResource(R.drawable.power_black);
				System.out.println("pb"); // ///////////////////////
			}
		} else if (sel == 1) { // time

			if (alarmState.equals("0")) {
				mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
						Constants.DEVICE_ADDRESS, "1", deviceState);
				timeBtn.setImageResource(R.drawable.time_orange);
				System.out.println("to"); // ///////////////////////
			} else if (alarmState.equals("1")) {
				mDbOpenHelper.updateColumn(1, Constants.DEVICE_NAME,
						Constants.DEVICE_ADDRESS, "0", deviceState);
				timeBtn.setImageResource(R.drawable.time_black);
				System.out.println("tb"); // ///////////////////////
			}
		} else if (sel == 0) {
			if (alarmState.equals("0")) {
				timeBtn.setImageResource(R.drawable.time_black);
				System.out.println("tb"); // ///////////////////////
			} else if (alarmState.equals("1")) {
				timeBtn.setImageResource(R.drawable.time_orange);
				System.out.println("to"); // ///////////////////////
			}
			if (deviceState.equals("0")) {
				devOnOffBtn.setImageResource(R.drawable.power_black);
				System.out.println("pb"); // ///////////////////////
			} else if (deviceState.equals("1")) {
				devOnOffBtn.setImageResource(R.drawable.power_orange);
				System.out.println("po"); // ///////////////////////
			}

		}

	}

	private void addListener() {
		finderBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						FinderActivity.class);
				// intent.putExtra("distance",
				// beaconManager.getBeaconDistance());
				startActivity(intent);

			}
		});

		registrationBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						RegistrationActivity.class);
				startActivity(intent);
				MainActivity.this.finish();

			}
		});
		timeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deviceConfirm(1); // time

			}
		});
		devOnOffBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				deviceConfirm(2); // dev

			}
		});

	}

	private void getView() {
		deviceNameText = (TextView) findViewById(R.id.deviceNameText);
		finderBtn = (ImageButton) findViewById(R.id.finderBtn);
		registrationBtn = (ImageButton) findViewById(R.id.registrationBtn);
		timeBtn = (ImageButton) findViewById(R.id.timeBtn);
		devOnOffBtn = (ImageButton) findViewById(R.id.devOnOffBtn);
	}

}
