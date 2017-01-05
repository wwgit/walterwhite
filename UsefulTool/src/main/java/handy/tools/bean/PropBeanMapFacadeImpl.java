package handy.tools.bean;

import handy.tools.interfaces.bean.BeanMapFacade;
import handy.tools.interfaces.templates.IPropBeanTempSetter;
import handy.tools.interfaces.templates.IPropBeanTemplate;
import handy.tools.parser.PropertiesBeanParser;

public class PropBeanMapFacadeImpl extends BeanMapFacade implements IPropBeanTemplate {

	public PropBeanMapFacadeImpl(String filePath) {
		setBeanData(new BeanDataMapImpl(filePath));
		loadBeans(filePath);
	}
	
	public PropBeanMapFacadeImpl(String ...filePaths) {
		if(null == getBeanData()) {
			setBeanData(new BeanDataMapImpl());
		}
		for(int i = 0; i < filePaths.length; i++) {
			loadBeans(filePaths[i]);
		}
	}
	
	public PropBeanMapFacadeImpl() {
		setBeanData(new BeanDataMapImpl());
	}
	
	@Override
	protected void initBeanParser(String filePath) {
		if(null == this.getBeanParser()) {
			this.setBeanParser(new PropertiesBeanParser(filePath));
		}
		
	}

	@Override
	protected void initBeanParser() {
		if(null == this.getBeanParser()) {
			this.setBeanParser(new PropertiesBeanParser());
		}
	}

	@Override
	protected void loadBeanTemplate(String filePath) {
		// TODO Auto-generated method stub
		IPropBeanTempSetter setter = (IPropBeanTempSetter) this.getBeanParser();
		setter.setBeanIdTab(BEAN_ID_TAB);
		setter.setClazHeaderSeperator(CLAZ_HEADER_SEPERATOR);
		setter.setClazPropSeperator(CLAZ_PROP_SEPERATOR);
		
		this.getBeanParser().reloadParser(filePath);
	}

}
