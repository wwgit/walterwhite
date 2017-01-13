/**   
* @Title: MethodArgs.java 
* @Package handy.tools.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017��1��10�� ����7:56:38 
* @version V1.0   
*/
package agents.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/** 
* @ClassName: MethodArgs 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:15:13 
*  
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodArgs {
	String argTypes() default "java.lang.String,java.lang.String";
	int howManyArgs() default 2;
	boolean containsBasicTypes() default false;
}
