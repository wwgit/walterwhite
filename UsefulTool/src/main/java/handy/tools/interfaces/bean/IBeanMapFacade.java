package handy.tools.interfaces.bean;


/** 
* @ClassName: IBeanMapFacade 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017��1��9�� ����2:16:46 
*  
*/
public interface IBeanMapFacade {
	
	public Object getBean(String beanId);
	public Object getBean(String beanId, String filePath);
	
	public void loadBeans(String filePath);
	public void lazyLoadBeans(String filePath);
	
	public void loadBeans(String ...filePaths);
	public void lazyLoadBeans(String ...filePaths);

}
