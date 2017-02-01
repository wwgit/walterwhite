/**   
* @Title: ServiceDemo.java 
* @Package com.example.servicedemo 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月31日 上午11:26:58 
* @version V1.0   
*/
package com.example.servicedemo;

import com.aidls.AidlDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/** 
 * @ClassName: ServiceDemo 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月31日 上午11:26:58 
 *  
 */
public class MyServiceDemo extends Service {

	private AidlDemo mFirstAidlDemo;
	
	public MyServiceDemo() {
		Log.i("MyServiceDemo", "initializing Serivce - MyServiceDemo");
		mFirstAidlDemo = new MyAidlImpl();
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		Log.i("ServiceDemo", "service is binding !");
//		return (IBinder)new MyInnerAidlImpl();
		return (IBinder) mFirstAidlDemo;
	}
	
	@Override
	public void onCreate() {
		Log.i("ServiceDemo", "service is creating !");
	}
	
	@Override
	public void onStart(Intent intent, int startId) {  
	        Log.i("ServiceDemo", "ExampleService-onStart");  
	} 
	
	@Override
	public void onDestroy() {
		Log.i("ServiceDemo", "ExampleService-onDestroy");
		super.onDestroy();
	}
	
	public class MyInnerAidlImpl extends AidlDemo.Stub {

		public MyInnerAidlImpl() {
			Log.i("MyInnerAidlImpl", "initializing AidlDemo.Stub - MyInnerAidlImpl");
		}
		
		/* (non-Javadoc)
		 * @see com.aidls.AidlDemo#basicTypes(long, boolean, float, double, java.lang.String)
		 */
		@Override
		public void basicTypes(long aLong, boolean aBoolean, float aFloat,
				double aDouble, String aString) throws RemoteException {
			
			Log.i("ServiceDemo", "printing long from service binder: " + aLong);
			Log.i("ServiceDemo", "printing String from service binder: " + aString);
			
		}

	}

}
