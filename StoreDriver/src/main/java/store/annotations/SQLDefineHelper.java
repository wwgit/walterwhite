/**   
* @Title: AnnoParser.java 
* @Package store.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月22日 下午3:21:56 
* @version V1.0   
*/
package store.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import store.db.sql.beans.definitions.CreateTableSQL;
import store.db.sql.beans.definitions.MySqlCreateSQL;

/** 
 * @ClassName: AnnoParser 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月22日 下午3:21:56 
 *  
 */
public abstract class SQLDefineHelper {
	
	public static CreateTableSQL getCreateTableSQL(Object tableBean) throws Exception {
		
		Table tableAnno = tableBean.getClass().getAnnotation(Table.class);
		Field[] refFields = tableBean.getClass().getDeclaredFields();
		
		if(null == tableAnno)
			throw new Exception("No Table Annotation found !");
		
		String tableName = tableAnno.tableName();
		if(null == tableName || tableName.equals(""))
			throw new Exception("Has No Table Name !");
		
		String dbClazName = tableAnno.dbClazName();
		
		CreateTableSQL createSql = null;
		if(dbClazName.contains("mysql")) {
			createSql = new MySqlCreateSQL();
		}
		
		String[] tableFields = tableAnno.fields().split(",");
		String[] fieldsTypes = tableAnno.fieldsTypes().split(",");
		
		if(null == tableFields || tableFields.length < 1) {
			tableFields = new String[refFields.length];
			fieldsTypes = new String[refFields.length];
			
			
		} else {
			if(null == fieldsTypes || fieldsTypes.length < 1)
				throw new Exception("fields number and fields Type number does Not match !");
		}

		
		
		return createSql;
	}

}
