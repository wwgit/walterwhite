/**   
* @Title: Client.java 
* @Package com.example.aidlclient 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月31日 下午12:46:01 
* @version V1.0   
*/
package com.example.aidlclient;

import com.aidls.AidlDemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/** 
 * @ClassName: Client 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月31日 下午12:46:01 
 *  
 */
public class Client implements ServiceConnection {

	public AidlDemo firstAidl;
	public Intent intent;
	
	public Client() {
		intent = new Intent();
		intent.setAction("com.aidls.AidlDemo");
	}
	
	/* (non-Javadoc)
	 * @see android.content.ServiceConnection#onServiceConnected(android.content.ComponentName, android.os.IBinder)
	 */
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		try {
			Log.v("onServiceConnected: getting remoted IBinder:",
								service.getInterfaceDescriptor());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		firstAidl = AidlDemo.Stub.asInterface(service);
		try {
			firstAidl.basicTypes(1, true, 1, 1.1, "aidl demo !");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see android.content.ServiceConnection#onServiceDisconnected(android.content.ComponentName)
	 */
	@Override
	public void onServiceDisconnected(ComponentName name) {
		Log.v("onServiceDisconnected called", "");
		firstAidl = null;
	}
	
}
