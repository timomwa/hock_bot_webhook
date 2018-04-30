package org.hock_bot.cron;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import org.apache.http.Consts;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;

import org.hock_bot.ejb.ConfigurationEJBI;
import org.hock_bot.ejb.MenuMapEJBI;
import org.hock_bot.ejb.UpdateEJBI;
import org.hock_bot.ejb.VehicleMakeEJBI;
import org.hock_bot.ejb.VehicleModelEJBI;
import org.hock_bot.model.Chat;
import org.hock_bot.model.MenuMap;
import org.hock_bot.model.Message;
import org.hock_bot.model.Status;
import org.hock_bot.model.Update;
import org.hock_bot.model.VehicleMake;
import org.hock_bot.model.VehicleModel;
import org.hock_bot.util.TerminalColorCodes;
import org.json.JSONArray;
import org.json.JSONObject;


@Singleton
@Startup
public class UpdateProcessorCron {
	
	@EJB
	private ConfigurationEJBI configEJB;
	
	@EJB
	private UpdateEJBI updateEJB;
	
	@EJB
	private MenuMapEJBI menuMapEJB;
	
	@EJB
	private VehicleModelEJBI vehiceModelEJB;
	
	@EJB
	private VehicleMakeEJBI vehiceMakelEJB;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private final Integer NOT_RUNNING = 0;
	private final Integer RUNNING = 1;
	private AtomicInteger status = new AtomicInteger(0);
	private ScheduleExpression expression;
	private String TIMER_NAME = getClass().getCanonicalName().concat("Timer");
	
	@Resource
	private TimerService timerService;
	
	@PostConstruct
	public void initialize(){
		createTimer();
	}
	
	
	
	private void cancelTimer(Timer timer){
		try{
			timer.cancel();
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}
	
	
	
	/**
	 * Handles timeouts at a higher level.
	 * 
	 * Does the processing overriden at doProcess();
	 * @param timer
	 */
	@Timeout
	private void timeout(Timer timer){

		try{
			
			cancelTimer(timer);
			
			if(!isRunning()){
				
				setRunning();
				
				try{
					doProcess();
				}catch(Exception e){
					logger.error(e.getMessage(), e);
				}
				
				setNotRunning();
			}
			
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}finally{
			createTimer();
		}
	}
	
	private boolean isRunning(){
		return status.intValue()==RUNNING;
	}
	
	private  void setNotRunning(){
		status.set(NOT_RUNNING);
	}
	
	private void setRunning(){
		status.set(RUNNING);
	}

	private void createTimer() {

		try{
		 	
			TimerConfig timerConf = new TimerConfig();
			timerConf.setInfo(TIMER_NAME);
			timerConf.setPersistent(false);
			
			if(expression==null){
				expression  = new ScheduleExpression();
			}
		 
			String second_expression = configEJB.getOrCreateConfigValue(ConfigurationEJBI.CRON_EXPR_PROCESS_UPDATES_SECOND, "*/5");
			String minute_expression = configEJB.getOrCreateConfigValue(ConfigurationEJBI.CRON_EXPR_PROCESS_UPDATES_MINUTE, "*");
			String hour_expression = configEJB.getOrCreateConfigValue(ConfigurationEJBI.CRON_EXPR_PROCESS_UPDATES_HOUR, "*");
			String day_expression = configEJB.getOrCreateConfigValue(ConfigurationEJBI.CRON_EXPR_PROCESS_UPDATES_DAY, "*");
			String month_expression = configEJB.getOrCreateConfigValue(ConfigurationEJBI.CRON_EXPR_PROCESS_UPDATES_MONTH, "*");
			String year_expression = configEJB.getOrCreateConfigValue(ConfigurationEJBI.CRON_EXPR_PROCESS_UPDATES_YEAR, "*");
			 
			if(second_expression!=null && !second_expression.isEmpty())
				expression = expression.second(second_expression);
			if(second_expression!=null && !second_expression.isEmpty())
				expression = expression.minute(minute_expression);
			if(hour_expression!=null && !hour_expression.isEmpty())
				expression = expression.hour(hour_expression);
			if(day_expression!=null && !day_expression.isEmpty())
				expression = expression.dayOfMonth(month_expression);
			if(year_expression!=null && !year_expression.isEmpty())
				expression = expression.dayOfMonth(year_expression);
		
			timerService.createCalendarTimer(expression, timerConf);
			
			/*logger.info(TerminalColorCodes.ANSI_RED_BACKGROUND+TerminalColorCodes.ANSI_WHITE
					+TIMER_NAME+" --> [ "
					.concat( second_expression)
					.concat( " ")
					.concat( minute_expression)
					.concat( " ")
					.concat( hour_expression)
					.concat( " ")
					.concat( day_expression)
					.concat( " ")
					.concat( month_expression)
					.concat( " ")
					.concat( year_expression)
					.concat( " ]")
					+TerminalColorCodes.ANSI_RESET);*/
		
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

	private void doProcess() {
		
		try{
			
			List<Update>  updates =  updateEJB.listUnprocessed(5);
			
			String TELEGRAM_API_BASE_URL = configEJB.getOrCreateConfigValue(ConfigurationEJBI.TELEGRAM_API_BASE_URL, "https://api.telegram.org");
			String TELEGRAM_ACCESS_TOKEN = configEJB.getOrCreateConfigValue(ConfigurationEJBI.TELEGRAM_ACCESS_TOKEN, "474326849:AAGx7g7sBcDWk_UrGenR0hdEdraG75cLVzk");
			String TELEGRAM_SEND_MESSAGE_URL = configEJB.getOrCreateConfigValue(ConfigurationEJBI.TELEGRAM_SEND_MESSAGE_URL, "#{TELEGRAM_API_BASE_URL}/bot#{TELEGRAM_ACCESS_TOKEN}/sendMessage");
			TELEGRAM_SEND_MESSAGE_URL = TELEGRAM_SEND_MESSAGE_URL.replaceAll("\\#\\{TELEGRAM_API_BASE_URL\\}", TELEGRAM_API_BASE_URL);
			TELEGRAM_SEND_MESSAGE_URL = TELEGRAM_SEND_MESSAGE_URL.replaceAll("\\#\\{TELEGRAM_ACCESS_TOKEN\\}", TELEGRAM_ACCESS_TOKEN);
			String botName = configEJB.getOrCreateConfigValue(ConfigurationEJBI.BOT_NAME, "honda_owners_club_bot");
			
			int start = 0;
			int size = 500;
			List<VehicleModel> vehicleModels = vehiceModelEJB.getByMakeName("honda",start,size); 
			
			for(Update update : updates){
				
				try{
					
					Message message  = update.getMessage();
					Chat chat = message.getChat();
					updateEJB.updateStatus(Status.PROCESSING, update.getId());
					String sourceMsg = message.getText();
					if(sourceMsg!=null && sourceMsg!=null){
						if(sourceMsg.trim().contains("@".concat(botName))){
							sourceMsg = sourceMsg.replaceAll("@".concat(botName), "").trim().toLowerCase();
						}
					}
					MenuMap responsemap = menuMapEJB.findMenu(sourceMsg);
					String respText = null;
					if(responsemap!=null){
						respText = responsemap.getResponse();
					}
					if(respText==null){
						respText = "Sorry, I don't have a response for that at the moment. Type / to see a list of available options";
					}
					
					JSONObject jsob  = new JSONObject();
					
					jsob.put("chat_id", chat.getChatId());
					jsob.put("text", respText);
					jsob.put("reply_to_message_id", message.getMessageId());
					jsob.put("method", "sendmessage");
					
					if(sourceMsg!=null && sourceMsg.equalsIgnoreCase("/service")){
						
						JSONArray inlineKeyboardButtonRow = new JSONArray();
						JSONArray inline_keyboard = new JSONArray();
						
						int rowCounter = 0;
						for(VehicleModel model : vehicleModels){
							
							JSONObject keyboardButton  = new JSONObject();
							keyboardButton.put("text", model.getName());
							keyboardButton.put("callback_data", "modelId=".concat( String.valueOf( model.getId() ) ).concat("&modelName=".concat(  model.getName() )));
							inlineKeyboardButtonRow.put( keyboardButton );
							
							
							if((rowCounter)%3==0){
								inline_keyboard.put( inlineKeyboardButtonRow );
								inlineKeyboardButtonRow = new JSONArray();
							}
							
							rowCounter++;
						}
						
						inline_keyboard.put( inlineKeyboardButtonRow );
						
						JSONObject reply_markup = new JSONObject();
						reply_markup.put("inline_keyboard", inline_keyboard);
						reply_markup.put("resize_keyboard", true);
						reply_markup.put("one_time_keyboard", true);
						reply_markup.put("selective", false);
						jsob.put("reply_markup", reply_markup);
						
					}
					
					logger.info(" req>> ::: "+jsob.toString());
					Content content = Request.Post(TELEGRAM_SEND_MESSAGE_URL).bodyString(jsob.toString() ,ContentType.create("application/json", Consts.UTF_8.name())).execute().returnContent();
					String response = content.asString();
					
					logger.info(" <<::: "+response);
					
					JSONObject resp = new JSONObject(response);
					
					if(resp.getBoolean("ok")){
						updateEJB.updateStatus(Status.PROCESSED, update.getId());
					}
					
				}catch(Exception e){
					logger.error(e.getMessage());
					try{
						updateEJB.updateStatus(Status.FAILED_TEMPORARILY, update.getId());
					}catch(Exception ex){}
				}finally{
					try{
						updateEJB.increaseRetryCount( update.getId());
					}catch(Exception ex){}
				}
				
			}
			
			
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		
	}

}
