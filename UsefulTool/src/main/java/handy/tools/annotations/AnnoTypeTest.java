package handy.tools.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AnnoTypeTest {
	
	public String testString() default "string default testing";
	public Class<?> testClazz() default int.class; 
	public int testInt() default 65535;
	long testLong() default 65539;
	public enum EnumData{ENUM1,ENUM2,ENUM3};
	public boolean testBoolean() default true;
	public EnumData testEnum() default EnumData.ENUM1; 
	
}
