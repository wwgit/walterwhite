package handy.tools.db;

import handy.tools.annotations.AnnoFieldTest;
import handy.tools.annotations.AnnoMethodTest;
import handy.tools.annotations.AnnoTypeTest;
import handy.tools.annotations.AnnoTypeTest.EnumData;
import handy.tools.annotations.MethodArgs;
import handy.tools.helpers.FundationHelper;

@AnnoTypeTest(testString = "overrides default value",
			  testInt=1,
			  testClazz=TestBean.class, testEnum=EnumData.ENUM3)
public class TestBean extends FundationHelper {
	
	//@AnnoFieldTest
	private Testing config;
	
	@AnnoFieldTest
	private String testAnno;
	

	@AnnoMethodTest
	public Testing getConfig() {
		return config;
	}

	public void setConfig(Testing config) {
		this.config = config;
	}

	@AnnoMethodTest
	public String getTestAnno() {
		return testAnno;
	}

	@MethodArgs
	public void setTestAnno(String testAnno) {
		this.testAnno = testAnno;
	}
	
	
	

}
