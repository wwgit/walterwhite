package handy.tools.interfaces.bean;



/** 
* @ClassName: BeanMapFacade 
* @Description: TODO(what to do) 
* @author walterwhite
* @date 2017年1月13日 下午2:12:21 
*  
*/
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

	
	/** 
	* @Title: loadBeanInfo 
	* @Description: TODO(what to do) 
	* @param   
	* @return void   
	* @throws 
	*/
	private void loadBeanInfo() {	
		
		this.getBeanData().setDefaultUniqueCode();	
		String uniqCode = loadBeanUniqCode(this.getBeanData().getCurrentFilePath());
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
	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanMapFacade#loadBeans(java.lang.String)
	 */
	public synchronized void loadBeans(String filePath) {		
		initBeanParser();
		this.getBeanData().setCurrentFilePath(filePath);
		loadBeanTemplate(this.getBeanData().getCurrentFilePath());
		loadBeanInfo();
		this.getBeanData().setBeanObjects();
	}
	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanMapFacade#loadBeans(java.lang.String[])
	 */
	public synchronized void loadBeans(String ...filePaths) {
		if(null == this.getBeanParser()) {
			initBeanParser();
		}
		for(int i = 0; i < filePaths.length; i++) {
			this.getBeanData().setCurrentFilePath(filePaths[i]);
			loadBeanTemplate(this.getBeanData().getCurrentFilePath());
			loadBeanInfo();
			this.getBeanData().setBeanObjects();
		}
	}
	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanMapFacade#lazyLoadBeans(java.lang.String[])
	 */
	public synchronized void lazyLoadBeans(String ...filePaths) {
		if(null == this.getBeanParser()) {
			initBeanParser();
		}
		for(int i = 0; i < filePaths.length; i++) {
			this.getBeanData().setCurrentFilePath(filePaths[i]);
			loadBeanTemplate(this.getBeanData().getCurrentFilePath());
			loadBeanInfo();
		}
	}

	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanMapFacade#lazyLoadBeans(java.lang.String)
	 */
	public synchronized void lazyLoadBeans(String filePath) {
		initBeanParser();
		this.getBeanData().setCurrentFilePath(filePath);
		loadBeanTemplate(this.getBeanData().getCurrentFilePath());
		loadBeanInfo();	
	}
	
	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanMapFacade#getBean(java.lang.String)
	 */
	public Object getBean(String beanId) {
		return this.getBeanData().getBean(beanId);
	}

	/* (non-Javadoc)
	 * @see handy.tools.interfaces.bean.IBeanMapFacade#getBean(java.lang.String, java.lang.String)
	 */
	public Object getBean(String beanId, String filePath) {
		return this.getBeanData().getBean(beanId, filePath);
	}
	
	
}
