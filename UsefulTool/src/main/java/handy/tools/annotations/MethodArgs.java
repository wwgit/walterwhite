/**   
* @Title: MethodArgs.java 
* @Package handy.tools.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月10日 下午7:56:38 
* @version V1.0   
*/
package handy.tools.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: MethodArgs 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月10日 下午7:56:38 
 *  
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodArgs {
	String argTypes() default "java.lang.String,java.lang.String";
	int howManyArgs() default 2;
	boolean containsBasicTypes() default false;
}
