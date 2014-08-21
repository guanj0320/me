package pf.tools.interfaces;

import java.util.Properties;

public class PropertiesSetterImpl implements PropertiesSetter {
	
	protected Properties props;
	
	public PropertiesSetterImpl() {
		
	}

	public void setProperties(Properties properties) {
		this.props = properties;

	}

}
