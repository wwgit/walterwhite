/**   
* @Title: AnnoParser.java 
* @Package store.annotations 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月22日 下午3:21:56 
* @version V1.0   
*/
package store.annotations;

import handy.tools.helpers.TypeHelper;

import java.lang.reflect.Field;
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
		} else {
			throw new Exception("only support mysql for now !");
		}
		
		String[] tableFields = tableAnno.fields().split(",");
		String[] fieldsTypes = tableAnno.fieldsTypes().split(",");
		
		if(null == tableFields || tableFields.length < 1) {
			
			StringBuilder sb = new StringBuilder();
			tableFields = new String[refFields.length];
			fieldsTypes = new String[refFields.length];
			for(int i = 0; i < refFields.length; i++) {
				
				if(refFields[i].isAnnotationPresent(TableField.class)) {
					TableField tf = refFields[i].getDeclaredAnnotation(TableField.class);
					tableFields[i] = tf.fieldName();
					if(tf.fieldLength() > 0) {
						sb.append(TypeHelper.getMysqlTypeDesc(tf.fieldType()));
						sb.append("(");
						sb.append(tf.fieldLength());
						sb.append(")");
						fieldsTypes[i] = sb.toString();
						sb.delete(0, sb.length());
					}
				}
				
			}
			
		} else {
			if(null == fieldsTypes || fieldsTypes.length < 1)
				throw new Exception("fields number and fields Type number does Not match !");
		}

		createSql.setUsedFields(tableFields);
		createSql.setFieldsTypes(fieldsTypes);
		
		return createSql;
	}

}
