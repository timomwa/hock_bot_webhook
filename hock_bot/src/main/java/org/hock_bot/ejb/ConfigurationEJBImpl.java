package org.hock_bot.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hock_bot.dao.ConfigurationDAOI;
import org.hock_bot.model.Configuration;

@Stateless
@Remote
public class ConfigurationEJBImpl implements ConfigurationEJBI {
	
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Inject
	private ConfigurationDAOI configurationDAO;

	@Override
	public String getOrCreateConfigValue(String cfgName, String defaultVal) {
		Configuration config = getConfigByKey(cfgName);
		if(config==null){
			try {
				config = createConfig(cfgName, defaultVal);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return defaultVal;
			}
		}
		return config.getCfgVal();
	}

	@Override
	public Configuration createConfig(String cfgName, String defaultVal) throws Exception{
		Configuration config = new Configuration();
		config.setCfgName(cfgName);
		config.setCfgVal(defaultVal);
		return configurationDAO.save(config);
	}

	@Override
	public Configuration getConfigByKey(String cfgName) {
		if(cfgName==null || cfgName.trim().isEmpty() )
			return null;
		return configurationDAO.findBy("cfgName", cfgName);
	}
	
	@Override
	public Boolean getBooleanConfigByKey(String key, boolean defVal) {
		Configuration config = getConfigByKey(key);
		if(config==null)
			return defVal;
		return config.getCfgVal().equalsIgnoreCase("true");
	}
	
	@Override
	public Boolean getOrCreateBooleanConfigByKey(String key, boolean defVal) {
		Configuration config = getConfigByKey(key);
		if(config==null){
			config = new Configuration();
			config.setCfgName(key);
			config.setCfgVal(defVal  ? "true": "false");
			try {
				config = configurationDAO.save(config);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return config.getCfgVal().equalsIgnoreCase("true");
	}

	@Override
	public Integer getOrCreateIntegerConfigByKey(String key, Integer defVal) {
		Configuration config = getConfigByKey(key);
		if(config==null){
			config = new Configuration();
			config.setCfgName(key);
			config.setCfgVal( String.valueOf(defVal) );
			try {
				config = configurationDAO.save(config);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return Integer.valueOf(  config.getCfgVal() );
	}

	@Override
	public void updateBooleanConfiguration(String cfgKey, boolean value) {
		configurationDAO.updateConfiguration(cfgKey, String.valueOf(value));
	}

	

}
