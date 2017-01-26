package com.example.loginpaneldemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button submit = (Button) findViewById(R.id.subBtn);
		submit.setOnClickListener(this);
	}

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.subBtn) {
			EditText acct = (EditText) findViewById(R.id.accountEt);
			Toast message = Toast.makeText(this, "login account:" + acct.getText(), Toast.LENGTH_SHORT);
			message.show();
		}
	}

}
