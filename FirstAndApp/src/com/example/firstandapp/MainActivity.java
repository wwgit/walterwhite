package com.example.firstandapp;

import java.util.ArrayList;
import java.util.List;

import com.example.firstandapp.beans.Student;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/** 
* @ClassName: MainActivity 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月26日 上午10:30:24 
*  
*/
public class MainActivity extends Activity {
	
	private List<Student> students;
	
	public MainActivity() {
		Student jason = new Student();
		jason.setName("jason");
		jason.setAddress("longwu Road 123");
		jason.setGender("male");
		
		Student marry = new Student();
		marry.setName("marry");
		marry.setGender("female");
		marry.setAddress("longwu Road 124");
		
		this.students = new ArrayList<Student>();
		this.students.add(jason);
		this.students.add(marry);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_item);
		
        //通过ID获取listView
        ListView listView = new ListView(this);
        
        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        
		ArrayAdapter<Student> studentAdapter = new ArrayAdapter<Student>(this,
				android.R.layout.simple_list_item_1, students);

        //设置listView的Adapter
        listView.setAdapter(studentAdapter);
        setContentView(listView);
		
	}
	
	
	
}
