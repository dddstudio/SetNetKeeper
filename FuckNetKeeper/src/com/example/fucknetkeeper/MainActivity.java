package com.example.fucknetkeeper;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	
	
	
	private EditText userName;
	private EditText password;
	private EditText ip;
	private SharedPreferences preferences;
	private EditText addre;
	private EditText real;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().hide();
		userName = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.password);
		ip = (EditText) findViewById(R.id.ip);
		addre = (EditText) findViewById(R.id.address);
		real = (EditText) findViewById(R.id.real);
		
		preferences = getSharedPreferences("date", MODE_PRIVATE);
		
		String p_userName = preferences.getString("userName", "");
		String p_password = preferences.getString("password", "");
		String p_ip = preferences.getString("ip", "");
		userName.setText(p_userName);
		password.setText(p_password);
		ip.setText(p_ip);
		
		Button button = (Button) findViewById(R.id.setRoute);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if ("".equals(userName.getText().toString())) {
					Toast.makeText(getApplicationContext(), "请输入账号",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("".equals(password.getText().toString())) {
					Toast.makeText(getApplicationContext(), "请输入密码",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if ("".equals(ip.getText().toString())) {
					Toast.makeText(getApplicationContext(), "请输入ip",
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				String ipStr=ip.getText().toString();
				String accStr=userName.getText().toString();
				CXKUsername cxkUsername = new CXKUsername(accStr);
				accStr=cxkUsername.Realusername();
				real.setText(accStr);
				String pswStr=password.getText().toString();
				String address = "http://";
				address+=ipStr+"/userRpm/PPPoECfgRpm.htm?wantype=2&VnetPap=0&acc=";
				//accStr="%0D%0A"+Uri.encode(accStr);    //因为账号已经换行了
				address+=Uri.encode(accStr)+"&psw=";
				address+=pswStr+"&linktype=2&Save=%B1%A3+%B4%E6";
				
				addre.setText(address);
				final Uri  uri = Uri.parse(address);
				AlertDialog.Builder builder = new Builder(MainActivity.this);
				builder.setTitle("提示");
				builder.setMessage("确认打开浏览器设置路由器吗？");
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent  intent = new  Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});
	}
	
	
	protected void onStop() {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("userName", userName.getText().toString());
		editor.putString("password", password.getText().toString());
		editor.putString("ip", ip.getText().toString());
		editor.commit();
		
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.s) {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("使用本软件默认接受一切风险，出现任何损失与本人无关");
			builder.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
