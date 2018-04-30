package org.hock_bot.ejb;

import org.hock_bot.model.Configuration;

public interface ConfigurationEJBI {
	
	String OUR_IDENTIIER_TOKEN = "OUR_IDENTIIER_TOKEN";
	public static final String TELEGRAM_API_BASE_URL = "TELEGRAM_API_BASE_URL";
	public static final String TELEGRAM_ACCESS_TOKEN = "TELEGRAM_ACCESS_TOKEN";
	public static final String TELEGRAM_SEND_MESSAGE_URL = "TELEGRAM_SEND_MESSAGE_URL";
	public static final String CRON_EXPR_PROCESS_UPDATES_SECOND = "CRON_EXPR_PROCESS_UPDATES_SECOND";
	public static final String CRON_EXPR_PROCESS_UPDATES_MINUTE = "CRON_EXPR_PROCESS_UPDATES_MINUTE";
	public static final String CRON_EXPR_PROCESS_UPDATES_HOUR = "CRON_EXPR_PROCESS_UPDATES_HOUR";
	public static final String CRON_EXPR_PROCESS_UPDATES_DAY = "CRON_EXPR_PROCESS_UPDATES_DAY";
	public static final String CRON_EXPR_PROCESS_UPDATES_MONTH = "CRON_EXPR_PROCESS_UPDATES_MONTH";
	public static final String CRON_EXPR_PROCESS_UPDATES_YEAR = "CRON_EXPR_PROCESS_UPDATES_YEAR";

	public String getOrCreateConfigValue(String key, String defaultVal);
	
	public Configuration getConfigByKey(String key) ;
	
	public Boolean getBooleanConfigByKey(String key, boolean defVal) ;
	
	public Boolean getOrCreateBooleanConfigByKey(String key, boolean defVal) ;
	
	public Configuration createConfig(String cfgName, String defaultVal) throws Exception;

	public Integer getOrCreateIntegerConfigByKey(String marketSaleDumpSize, Integer defVal);

	public void updateBooleanConfiguration(String cfgKey, boolean value);


}
