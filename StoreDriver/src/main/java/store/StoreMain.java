package store;

import handy.tools.factorties.PropertiesBeanFactory;
import handy.tools.factorties.XmlBeanFactory;
import handy.tools.interfaces.bean.BeanFactory;
import store.db.sql.beans.DbConfig;
import store.db.sql.beans.definitions.WhereDefinition;

public class StoreMain {

	public static void main(String[] args) {

		
		String[] where = "field-a= or, field-b= and, field-c> or, field-d> or, field-e<>".split(",");
		WhereDefinition wd = new WhereDefinition();
		wd.setWhereConditions(where);
//		System.out.println(wd.generateWhereConditions());
		
		BeanFactory xmlBeanCreator = new XmlBeanFactory("example8.xml");
		//xmlBeanCreator.loadBeans("example8.xml");
//		BeanFactory propBeanCreator = new PropertiesBeanFactory("configs/db_config.properties");
		long startTime = System.currentTimeMillis();
//		xmlBeanCreator.lazyLoadBeans("example7.xml","example8.xml");
		
		for(int i = 0; i < 10000; i++) {
			//System.out.println(i);
			//System.out.println(xmlBeanCreator.getBean("TestBean","example8.xml"));
			DbConfig refBeanChk3 = (DbConfig) xmlBeanCreator.getBean("DbConfig");
			//DbConfig propChk2 = (DbConfig) propBeanCreator.getBean("dbConfig1");
			//System.out.println("propChk:" + propChk2.getPassword());
			//System.out.println("refBeanChk3:" + refBeanChk3.getConfig().getUrl());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("time " + (endTime - startTime) + "ms");	
		
	}

}
