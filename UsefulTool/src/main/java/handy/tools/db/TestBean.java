package handy.tools.db;

import handy.tools.annotations.AnnoFieldTest;
import handy.tools.annotations.AnnoMethodTest;
import handy.tools.annotations.AnnoTypeTest;
import handy.tools.annotations.AnnoTypeTest.EnumData;

@AnnoTypeTest(testString = "overrides default value",
			  testInt=1,
			  testClazz=TestBean.class, testEnum=EnumData.ENUM3)
public class TestBean {
	
	//@AnnoFieldTest
	private DbConfig config;
	
	@AnnoFieldTest
	private String testAnno;
	

	@AnnoMethodTest
	public DbConfig getConfig() {
		return config;
	}

	public void setConfig(DbConfig config) {
		this.config = config;
	}

	@AnnoMethodTest
	public String getTestAnno() {
		return testAnno;
	}

	public void setTestAnno(String testAnno) {
		this.testAnno = testAnno;
	}
	
	
	

}
