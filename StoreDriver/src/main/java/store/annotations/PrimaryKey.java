/**   
* @Title: PrimaryKey.java 
* @Package store.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月22日 下午3:01:48 
* @version V1.0   
*/
package store.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: PrimaryKey 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月22日 下午3:01:48 
 *  
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrimaryKey {
	
	public String keyName() default "";
	
	/** 
	* @Title: primaryKeyFields 
	* @Description: TODO(fields that are used as Primary Key) 
	* "field1,field2,field3,field4,field5"
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String primaryKeyFields();

}
