package handy.tools.interfaces.bean;

public abstract class BeanMapFacade extends BeanCommons implements IBeanMapFacade {

	private IBeanInfoMapParser beanParser;
	
	private IBeanDataMap beanData;
	
	protected abstract void initBeanParser(String filePath);
	protected abstract void initBeanParser();
	protected abstract void loadBeanTemplate(String filePath);

	public IBeanInfoMapParser getBeanParser() {
		return beanParser;
	}

	public void setBeanParser(IBeanInfoMapParser beanParser) {
		this.beanParser = beanParser;
	}

	public IBeanDataMap getBeanData() {
		return beanData;
	}

	public void setBeanData(IBeanDataMap beanData) {
		this.beanData = beanData;
	}
	
	private void loadBeanInfo(String filePath) {	
		
		String uniqCode = loadBeanUniqCode(filePath);
		this.getBeanData().setCurrentFilePath(filePath);
		this.getBeanData().setDefaultUniqueCode(filePath);
		this.getBeanData().setBeansClazz(
							this.getBeanParser().setBeansClazz(uniqCode));
		this.getBeanData().setBeanPropertyClazz();
		this.getBeanData().setBeanPropertyRefBeanId(
				         	this.getBeanParser().BeansPropertiesRefBeanIds(uniqCode));
		this.getBeanData().setBeanPropertyValues(
							this.getBeanParser().BeansPropertiesValues(uniqCode));
		this.getBeanData().setCurrFileBeanIds(
							this.getBeanParser().setCurrFileBeanIds(uniqCode));
		
	}
	
	public synchronized void loadBeans(String filePath) {		
		initBeanParser(filePath);
		loadBeanTemplate(filePath);
		loadBeanInfo(filePath);
		this.getBeanData().setBeanObjects(filePath);
	}
	
	public synchronized void loadBeans(String ...filePaths) {
		if(null == this.getBeanParser()) {
			initBeanParser();
		}
		for(int i = 0; i < filePaths.length; i++) {
			loadBeanTemplate(filePaths[i]);
			loadBeanInfo(filePaths[i]);
			this.getBeanData().setBeanObjects(filePaths[i]);
		}
	}
	
	public synchronized void lazyLoadBeans(String ...filePaths) {
		if(null == this.getBeanParser()) {
			initBeanParser();
		}
		for(int i = 0; i < filePaths.length; i++) {
			loadBeanTemplate(filePaths[i]);
			loadBeanInfo(filePaths[i]);
		}
	}

	public synchronized void lazyLoadBeans(String filePath) {
		initBeanParser(filePath);
		loadBeanTemplate(filePath);
		loadBeanInfo(filePath);		
	}
	
	public Object getBean(String beanId) {
		return this.getBeanData().getBean(beanId);
	}

	public Object getBean(String beanId, String filePath) {
		return this.getBeanData().getBean(beanId, filePath);
	}
	
	
}
