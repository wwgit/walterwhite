/**   
* @Title: ForeignKey.java 
* @Package store.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月22日 下午3:06:16 
* @version V1.0   
*/
package store.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: ForeignKey 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月22日 下午3:06:16 
 *  
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ForeignKey {
	
	public String keyName() default "";
	
	/** 
	* @Title: foreignKeyFields 
	* @Description: TODO(fields that are used as Foreign Key) 
	* "field1,field2,field3,field4,field5"
	* 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String foreignKeyFields();
	
	/** 
	* @Title: refTableName 
	* @Description: TODO(tableName that foreignKey is referred to) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String refTableName();
	
	/** 
	* @Title: refTableFields 
	* @Description: TODO(fields of ref table)
	* "field1,field2,field3,field4,field5" 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String refTableFields();

}
