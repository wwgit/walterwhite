/**   
* @Title: TestActivity.java 
* @Package com.example.aidlclient 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月31日 下午3:20:27 
* @version V1.0   
*/
package com.example.aidlclient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

/** 
 * @ClassName: TestActivity 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月31日 下午3:20:27 
 *  
 */
public class TestActivity extends Activity {
	
	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.client = new Client();
		bindService(client.intent, this.client, Context.BIND_AUTO_CREATE);
		
	}
	

}
