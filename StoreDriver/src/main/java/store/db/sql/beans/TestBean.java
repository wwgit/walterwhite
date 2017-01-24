/**   
* @Title: TestBean.java 
* @Package store.db.sql.beans 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月24日 下午7:26:56 
* @version V1.0   
*/
package store.db.sql.beans;

/** 
 * @ClassName: TestBean 
 * @Description: TODO(what to do) 
 * @author walterwhite
 * @date 2017年1月24日 下午7:26:56 
 *  
 */
public class TestBean {
	
	private DbConfig config;
	private int id;
	private double doubleId;
	private String[] strArray;
	private Object[] objArray;
	private int[] intArray;

	public DbConfig getConfig() {
		return config;
	}

	public void setConfig(DbConfig config) {
		this.config = config;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDoubleId() {
		return doubleId;
	}

	public void setDoubleId(double doubleId) {
		this.doubleId = doubleId;
	}

	public String[] getStrArray() {
		return strArray;
	}

	public void setStrArray(String[] strArray) {
		this.strArray = strArray;
	}

	public Object[] getObjArray() {
		return objArray;
	}

	public void setObjArray(Object[] objArray) {
		this.objArray = objArray;
	}

	public int[] getIntArray() {
		return intArray;
	}

	public void setIntArray(int[] intArray) {
		this.intArray = intArray;
	}
	

}
