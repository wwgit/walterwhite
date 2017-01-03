package handy.tools.interfaces.bean;

public interface IBeanMapFacade {
	
	public Object getBean(String beanId);
	public Object getBean(String beanId, String filePath);
	
	public void loadBeans(String filePath);
	public void lazyLoadBeans(String filePath);
	

}
