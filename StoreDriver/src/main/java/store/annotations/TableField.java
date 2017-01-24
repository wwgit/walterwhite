/**   
* @Title: TableField.java 
* @Package store.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月22日 下午3:40:14 
* @version V1.0   
*/
package store.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: TableField 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月22日 下午3:40:14 
 *  
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableField {
	
	/** 
	* @Title: fieldName 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String fieldName();
	
	/** 
	* @Title: fieldType 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return Class<?>   
	* @throws 
	*/
	public Class<?> fieldType();
	
	/** 
	* @Title: fieldLength 
	* @Description: TODO(what to do) 
	* @param @return  
	* @return int   
	* @throws 
	*/
	public int fieldLength() default 0;
	
	public boolean allowNull() default true;
	
	public boolean isAutoIncr() default false;

}
