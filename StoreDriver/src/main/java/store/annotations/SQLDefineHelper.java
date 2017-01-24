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
	
	@SuppressWarnings("null")
	public static CreateTableSQL getCreateTableSQL(Object tableBean) throws Exception {
		
		Table tableAnno = null;
		if(tableBean.getClass().isAnnotationPresent(Table.class)) {
			tableAnno = tableBean.getClass().getAnnotation(Table.class);
		} else {
			throw new Exception("No Table Annotation found !");
		}
		
		
		
		CreateTableSQL createSql = null;
		String dbClazName = tableAnno.dbClazName();
		if(dbClazName.contains("mysql")) {
			createSql = new MySqlCreateSQL();
		} else {
			throw new Exception("only support mysql for now !");
		}
		
		String dbName = tableAnno.dbName();
		if(null != dbName || dbName.equals("")) {
			createSql.setDbName(dbName);
		}
		
		String tableName = tableAnno.tableName();
		if(null == tableName || tableName.equals("")) {
			throw new Exception("Has No Table Name !");
		} else {
			createSql.setTableName(tableName);
		}	
		
		String[] tableFields = tableAnno.fields().split(",");
		String[] fieldsTypes = tableAnno.fieldsTypes().split(",");
		String[] allowNull = tableAnno.allowNull().split(",");
		String[] isAutoIncr = tableAnno.isAutoIncr().split(",");
		if(null != tableFields) {
			if(null == fieldsTypes) throw new Exception("fields types are null !");
			if(null != allowNull) {
				createSql.setIsFieldNull(allowNull);
			}
			if(null != isAutoIncr) {
				createSql.setIsAutoIncr(isAutoIncr);
			}
			createSql.setUsedFields(tableFields);
			createSql.setFieldsTypes(fieldsTypes);
			
			return createSql;
		}
		
		if(null == tableFields || tableFields.length < 1) {
			
			Field[] refFields = tableBean.getClass().getDeclaredFields();	
			StringBuilder sb = new StringBuilder();
			tableFields = new String[refFields.length];
			fieldsTypes = new String[refFields.length];
			allowNull = new String[refFields.length];
			isAutoIncr = new String[refFields.length];
			
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
					} else {
						fieldsTypes[i] = TypeHelper.getMysqlTypeDesc(tf.fieldType());
					}
					
					if(tf.allowNull()) {
						allowNull[i] = "YES";
					} else {
						allowNull[i] = "NO";
					}
					if(tf.isAutoIncr()) isAutoIncr[i] = "YES";
				}
				
			}
			
		} 

		createSql.setUsedFields(tableFields);
		createSql.setFieldsTypes(fieldsTypes);
		createSql.setIsFieldNull(allowNull);
		createSql.setIsAutoIncr(isAutoIncr);
		
		return createSql;
	}

}
