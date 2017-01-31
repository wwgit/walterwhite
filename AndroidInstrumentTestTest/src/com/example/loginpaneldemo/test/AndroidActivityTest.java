/**   
* @Title: AndroidActivityTest.java 
* @Package com.example.loginpaneldemo.test 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月26日 下午8:30:18 
* @version V1.0   
*/
package com.example.loginpaneldemo.test;

import com.example.aidlclient.Client;
import com.example.aidlclient.TestActivity;
import com.example.loginpaneldemo.MainActivity;
import com.example.loginpaneldemo.R;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.Button;

/** 
 * @ClassName: AndroidActivityTest 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月26日 下午8:30:18 
 *  
 */
public class AndroidActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

	private Activity testTarget;
	private Instrumentation mInst;
	
	public AndroidActivityTest() throws ClassNotFoundException {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
	    mInst = getInstrumentation();
	    
//	         关闭待测应用的触控模式，以便向下拉框发送按键消息  
//	          这个操作必须在getActivity()之前调用
		setActivityInitialTouchMode(false);
		testTarget = getActivity();
		
	}
	public void testMain() {  
//	     assertTrue(true);
		System.out.println("testing main");
		Log.v("instrumentTest:", "testMain");
		
		Intent intent = new Intent();
		intent.setClassName("com.example.aidlclient", TestActivity.class.getName());
//		intent.setClassName("com.example.loginpaneldemo", MainActivity.class.getName());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mInst.startActivitySync(intent);
		
//		Button btn = (Button)testTarget.findViewById(R.id.subBtn);
//		mInst.runOnMainSync(new ButtonClick(btn)); 
//		CallRemoteService remoteCall = new CallRemoteService(testTarget);
//		mInst.runOnMainSync(remoteCall);
	                
	}
	
	private class CallRemoteService implements Runnable {
		
		private Client client;
		private Activity target;
		
		public CallRemoteService(Activity testTarget) {
			target = testTarget;
			client = new Client();
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {		
			target.bindService(client.intent, this.client, Context.BIND_AUTO_CREATE);
			try {
				this.client.firstAidl.basicTypes(1, true, 1, 1, "asdfasdf");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
	}

	private class ButtonClick implements Runnable {

		Button button;
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		
		ButtonClick(Button b) {
			button = b;
		}
		
		@Override
		public void run() {
			Log.d("Instrument Test:", "performing button click");
			for(int i = 0; i < 1000; i++) {
				Log.d(this.getClass().getSimpleName(), "performing button click");
				button.performClick();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	

}
