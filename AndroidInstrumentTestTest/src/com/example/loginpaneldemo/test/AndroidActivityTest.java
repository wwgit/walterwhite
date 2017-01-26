/**   
* @Title: AndroidActivityTest.java 
* @Package com.example.loginpaneldemo.test 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月26日 下午8:30:18 
* @version V1.0   
*/
package com.example.loginpaneldemo.test;

import com.example.loginpaneldemo.MainActivity;
import com.example.loginpaneldemo.R;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
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
		this.testTarget = getActivity();
		
	}
	public void testMain() {  
//	     assertTrue(true);
		Button btn = (Button) this.testTarget.findViewById(R.id.subBtn);
		mInst.runOnMainSync(new ButtonClick(btn));  
	                
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
			button.performClick();
		}
		
	}
	

}
