package com.example.loginpaneldemo;

import com.aidls.AidlDemo;
import com.example.servicedemo.MyServiceDemo;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	private AidlDemo demo = null;
	
	protected ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			demo = AidlDemo.Stub.asInterface(service);
			try {
				Log.i("RidlDemo", "on Service Connected");
				demo.basicTypes(1, true, 1, 1, "called from MainActivity");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			Log.i("RidlDemo", "on Service Disconnected");
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button submit = (Button) findViewById(R.id.subBtn);
		submit.setOnClickListener(this);
		Intent serIntent = new Intent();
//		serIntent.setClassName("com.example.servicedemo", MyServiceDemo.class.getName());
//		serIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//this.bin
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.subBtn) {
			EditText acct = (EditText) findViewById(R.id.accountEt);
			Toast message = Toast.makeText(this, "starting service", Toast.LENGTH_SHORT);
			Log.i("ServiceDemo", "clicking buttong to start service");
			message.show();
//			Intent serIntent = new Intent(this, MyServiceDemo.class);
			Intent intent = new Intent();
			intent.setPackage("com.example.loginpaneldemo");
//			intent.setClassName("com.example.servicedemo", "MyServiceDemo");
			intent.setAction("com.example.servicedemo.MyServiceDemo");
//			serIntent.setClassName();
//			serIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startService(serIntent);
//			stopService(serIntent);
			bindService(intent, conn, BIND_AUTO_CREATE);
//			unbindService(conn);
			
		}
	}

}
