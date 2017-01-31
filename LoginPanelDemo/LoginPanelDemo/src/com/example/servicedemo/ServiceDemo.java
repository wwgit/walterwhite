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
public class ServiceDemo extends Service {

	private final AidlDemo mFirstAidlDemo = new AidlDemo.Stub() {
		
		@Override
		public void basicTypes(long aLong, boolean aBoolean, float aFloat,
				double aDouble, String aString) throws RemoteException {
			
			Log.i("binding service Demo:", "printing long from service binder: " + aLong);
		}
	};
	
	
	/* (non-Javadoc)
	 * @see android.app.Service#onBind(android.content.Intent)
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return (IBinder) mFirstAidlDemo;
	}
	
	

}
