/**   
* @Title: Table.java 
* @Package store.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月22日 下午2:34:20 
* @version V1.0   
*/
package store.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @ClassName: Table 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月22日 下午2:34:20 
 *  
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {
	
	public String dbName() default "";
	
	public String tableName();
	
	public String dbClazName() default "com.mysql.jdbc.Driver";
	
	public String charSet() default "uft8";
	
	public int autoIncr() default 1;
	
	/** 
	* @Title: fields 
	* @Description: TODO(what to do) 
	* "field1,field2,field3,field4,field5"
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String fields() default "";
	
	/** 
	* @Title: fieldsTypes 
	* @Description: TODO(what to do)
	* "INT(10),VARCHAR(255),VARCHAR(255),VARCHAR(255),VARCHAR(255)" 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String fieldsTypes() default "";

	/** 
	* @Title: allowNull 
	* @Description: TODO(what to do) 
	* "YES,YES,YES,YES,YES"
	* 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String allowNull() default "";
	
	/** 
	* @Title: isAutoIncr 
	* @Description: TODO(what to do) 
	* "YES,NO,NO,NO,NO"
	* 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String isAutoIncr() default "";
	
	
	/** 
	* @Title: uniqKeyFields 
	* @Description: TODO(unique Key not supported yet) 
	* @param @return  
	* @return String   
	* @throws 
	*/
	public String uniqKeyFields() default "";

}
