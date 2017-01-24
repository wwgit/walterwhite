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
import java.util.ArrayList;
import java.util.List;

import store.db.sql.beans.definitions.CreateTableSQL;
import store.db.sql.beans.definitions.MySqlCreateSQL;
import store.db.sql.beans.definitions.constraints.Constraint;
import store.db.sql.beans.definitions.constraints.ForeignKey;
import store.db.sql.beans.definitions.constraints.PrimaryKey;

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
			tableAnno = tableBean.getClass().getDeclaredAnnotation(Table.class);
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
		
		PrimaryKeyAnno pka = null;
		List<Constraint> constrs = new ArrayList<Constraint>();
		if(tableBean.getClass().isAnnotationPresent(PrimaryKeyAnno.class)) {
			pka = tableBean.getClass().getDeclaredAnnotation(PrimaryKeyAnno.class);
			PrimaryKey pk = new PrimaryKey();
			pk.setConstrName(pka.keyName());
			pk.setConstrFields(pka.primaryKeyFields().split(","));
			constrs.add(pk);
		}
		
		ForeignKeyAnno fka = null;
		if(tableBean.getClass().isAnnotationPresent(ForeignKeyAnno.class)) {
			fka = tableBean.getClass().getDeclaredAnnotation(ForeignKeyAnno.class);
			ForeignKey fk = new ForeignKey();
			fk.setConstrName(fka.keyName());
			fk.setConstrFields(fka.foreignKeyFields().split(","));
			fk.setRefTableName(fka.refTableName());
			fk.setRefTableFields(fka.refTableFields().split(","));
			constrs.add(fk);
		}
		
		createSql.setConstraints(constrs);
		
		String[] tableFields = null;
		if(false == tableAnno.fields().equals("")) {
			tableFields = tableAnno.fields().split(",");
		}
		
		String[] fieldsTypes = null;
		if(false == tableAnno.fieldsTypes().equals("")) {
			fieldsTypes = tableAnno.fieldsTypes().split(",");
		}
		
		String[] allowNull = null;
		if(false == tableAnno.allowNull().equals("")) {
			allowNull = tableAnno.allowNull().split(",");
		}
		
		String[] isAutoIncr = null;
		if(false == tableAnno.isAutoIncr().equals("")) {
			isAutoIncr = tableAnno.isAutoIncr().split(",");
		}
		
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
