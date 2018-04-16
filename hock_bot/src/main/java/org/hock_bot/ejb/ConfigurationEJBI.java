package org.hock_bot.ejb;

import org.hock_bot.model.Configuration;

public interface ConfigurationEJBI {
	
	String OUR_IDENTIIER_TOKEN = "OUR_IDENTIIER_TOKEN";

	public String getOrCreateConfigValue(String key, String defaultVal);
	
	public Configuration getConfigByKey(String key) ;
	
	public Boolean getBooleanConfigByKey(String key, boolean defVal) ;
	
	public Boolean getOrCreateBooleanConfigByKey(String key, boolean defVal) ;
	
	public Configuration createConfig(String cfgName, String defaultVal) throws Exception;

	public Integer getOrCreateIntegerConfigByKey(String marketSaleDumpSize, Integer defVal);

	public void updateBooleanConfiguration(String cfgKey, boolean value);


}
