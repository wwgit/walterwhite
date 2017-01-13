package handy.tools.interfaces.bean;




/** 
* @ClassName: BeanFactory 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:12:05 
*  
*/
public abstract class BeanFactory {
	
	private IBeanMapFacade beanFacade;

	public IBeanMapFacade getBeanFacade() {
		return beanFacade;
	}

	public void setBeanFacade(IBeanMapFacade beanFacade) {
		this.beanFacade = beanFacade;
	}

	public Object getBean(String beanId) {
		return this.getBeanFacade().getBean(beanId);
	}
	
	public Object getBean(String beanId, String filePath) {
		return this.getBeanFacade().getBean(beanId, filePath);
	}
	
	public void loadBeans(String filePath) {
		this.getBeanFacade().loadBeans(filePath);
	}
	
	public void lazyLoadBeans(String filePath) {
		this.getBeanFacade().lazyLoadBeans(filePath);
	}
	
	public void loadBeans(String ...filePaths) {
		this.getBeanFacade().loadBeans(filePaths);
	}
	public void lazyLoadBeans(String ...filePaths) {
		this.getBeanFacade().lazyLoadBeans(filePaths);
	}
	
}
