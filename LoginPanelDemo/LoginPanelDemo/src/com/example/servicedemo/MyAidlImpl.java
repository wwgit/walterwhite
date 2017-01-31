/**   
* @Title: MyAidlImpl.java 
* @Package com.example.servicedemo 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月31日 下午10:04:31 
* @version V1.0   
*/
package com.example.servicedemo;

import android.os.RemoteException;
import android.util.Log;

import com.aidls.AidlDemo;

/** 
 * @ClassName: MyAidlImpl 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月31日 下午10:04:31 
 *  
 */
public class MyAidlImpl extends AidlDemo.Stub {

	public MyAidlImpl() {
		Log.i("MyAidlImpl", "initializing AidlDemo.Stub - MyAidlImpl");
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
