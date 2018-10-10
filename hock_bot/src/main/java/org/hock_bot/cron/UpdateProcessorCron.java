package org.hock_bot.cron;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
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
import org.hock_bot.ejb.CallbackQueryEJBI;
import org.hock_bot.ejb.ConfigurationEJBI;
import org.hock_bot.ejb.MenuMapEJBI;
import org.hock_bot.ejb.UpdateEJBI;
import org.hock_bot.ejb.VehicleMakeEJBI;
import org.hock_bot.ejb.VehicleModelEJBI;
import org.hock_bot.model.CallbackQuery;
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
	
	@EJB
	private CallbackQueryEJBI callBackQueryEJB;
	
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
			
			
			String TELEGRAM_EDIT_MESSAGE_URL = configEJB.getOrCreateConfigValue(ConfigurationEJBI.TELEGRAM_EDIT_MESSAGE_URL, "#{TELEGRAM_API_BASE_URL}/bot#{TELEGRAM_ACCESS_TOKEN}/editMessageText");
			TELEGRAM_EDIT_MESSAGE_URL = TELEGRAM_EDIT_MESSAGE_URL.replaceAll("\\#\\{TELEGRAM_API_BASE_URL\\}", TELEGRAM_API_BASE_URL);
			TELEGRAM_EDIT_MESSAGE_URL = TELEGRAM_EDIT_MESSAGE_URL.replaceAll("\\#\\{TELEGRAM_ACCESS_TOKEN\\}", TELEGRAM_ACCESS_TOKEN);
			
			
			int start = 0;
			int size = 500;
			List<VehicleModel> vehicleModels = vehiceModelEJB.getByMakeName("honda",start,size); 
			
			for(Update update : updates){
				
				try{
					
					Message message  = update.getMessage();
					CallbackQuery callbackQuery = update.getCallbackQuery();
					
					JSONObject jsob  = new JSONObject();
					JSONArray inlineKeyboardButtonRow = new JSONArray();
					JSONArray inline_keyboard = new JSONArray();
					int rowCounter = 0;
					
					String url = null;
					
					if(callbackQuery!=null){
						url = TELEGRAM_EDIT_MESSAGE_URL;
						Message message_C = callbackQuery.getMessage();
						Chat chat_C = message_C.getChat();
						String data = callbackQuery.getData();
						
						if(data.contains("modelName")){
							JSONObject dataJSON = new JSONObject(data);
							String model = dataJSON.getString("modelName");
							
							jsob.put("chat_id", chat_C.getChatId());
							jsob.put("message_id", message_C.getMessageId());
							jsob.put("text", "Which year is your Honda "+model+" ?");
							
							for(int i = 1980; i<2020; i++){
								
								JSONObject keyboardButton  = new JSONObject();
								keyboardButton.put("text",  String.valueOf(i)  );
								JSONObject callBackData = new JSONObject();
								callBackData.put("year", i);
								callBackData.put("model", model);
								keyboardButton.put("callback_data", callBackData.toString() );
								inlineKeyboardButtonRow.put( keyboardButton );
								
								
								if((rowCounter)%5==0){
									inline_keyboard.put( inlineKeyboardButtonRow );
									inlineKeyboardButtonRow = new JSONArray();
								}
								
								rowCounter++;
								
							}
							
							inline_keyboard.put( inlineKeyboardButtonRow );
							
						}else if(data.contains("year")){
							
							JSONObject callbackData = new JSONObject(data);
							String model = callbackData.getString("model");
							int year = callbackData.getInt("year");
							
							jsob.put("chat_id", chat_C.getChatId());
							jsob.put("message_id", message_C.getMessageId());
							String message_resp = "Standard service: "+year+" Honda ".concat( model ).concat(";\n");
							message_resp = message_resp.concat( "--------------------------------------------------\n\n" );
							message_resp = message_resp.concat( "Engine Oil   - Kes 2,500 \n" );
							message_resp = message_resp.concat( "Oil filter   - Kes   500 \n" );
							message_resp = message_resp.concat( "Cabin filter - Kes 1,500 \n" );
							message_resp = message_resp.concat( "Total		  - Kes 4,500 \n\n" );
							message_resp = message_resp.concat( "*These are just estimates. Recommended service interval is 10,000kms or 3 months. Otherwise, refer to owner's manual*\n" );
							jsob.put("text", message_resp);
							
						}else if(data.contains("interested")){
							
							
							if(data.contains("YES")){
								
								String[] a = {"Myself", "Someone Else"};
								List<String> meOrSomeoneElse = Arrays.asList(a);
								
								jsob.put("chat_id", chat_C.getChatId());
								jsob.put("message_id", message_C.getMessageId());
								jsob.put("text", "Would you like to nominate yourself or someone else?");
								
								for(String myselfSomeone : meOrSomeoneElse){
								
									JSONObject keyboardButton  = new JSONObject();
									keyboardButton.put("text", myselfSomeone);
									JSONObject callBackData = new JSONObject();
									callBackData.put("myselfSomeone", myselfSomeone);
									keyboardButton.put("callback_data", callBackData.toString());
									
									inlineKeyboardButtonRow = new JSONArray();
									inlineKeyboardButtonRow.put( keyboardButton );
									inline_keyboard.put( inlineKeyboardButtonRow );
									
								}
								
								JSONObject reply_markup  = new JSONObject();
								
								reply_markup.put("inline_keyboard", inline_keyboard);
								reply_markup.put("resize_keyboard", true);
								reply_markup.put("one_time_keyboard", true);
								reply_markup.put("selective", false);
								jsob.put("reply_markup", reply_markup);
							}
							
						}
						
					}else if(message!=null){
						
						url = TELEGRAM_SEND_MESSAGE_URL;
						
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
						
						
						
						
						jsob.put("chat_id", chat.getChatId());
						jsob.put("text", respText);
						jsob.put("reply_to_message_id", message.getMessageId());
						jsob.put("method", "sendmessage");
						
						logger.info(" sourceMsg ::: "+sourceMsg);
						
						if(sourceMsg!=null && sourceMsg.equalsIgnoreCase("/nomination")){
							
							String[] a = {"YES", "NO"};
							List<String> yesandNo = Arrays.asList(a);
							
							for(String yesNo : yesandNo){
							
								JSONObject keyboardButton  = new JSONObject();
								keyboardButton.put("text", yesNo);
								JSONObject callBackData = new JSONObject();
								callBackData.put("interested", yesNo);
								keyboardButton.put("callback_data", callBackData.toString());
								
								inlineKeyboardButtonRow = new JSONArray();
								inlineKeyboardButtonRow.put( keyboardButton );
								inline_keyboard.put( inlineKeyboardButtonRow );
								
							}
							
							JSONObject reply_markup  = new JSONObject();
							reply_markup.put("inline_keyboard", inline_keyboard);
							reply_markup.put("resize_keyboard", true);
							reply_markup.put("one_time_keyboard", true);
							reply_markup.put("selective", false);
							jsob.put("text", "Dear member. Time has come to hand in the reighns of this excellent to group to new officials. It is with this regard, we need to hold an AGM to elect new office bearers. Would you like to nominate yourself or someone for an official position?");
							jsob.put("reply_markup", reply_markup);
							
						}else if(sourceMsg!=null && sourceMsg.equalsIgnoreCase("/service")){
							
							for(VehicleModel model : vehicleModels){
								
								JSONObject keyboardButton  = new JSONObject();
								keyboardButton.put("text", model.getName());
								JSONObject callBackData = new JSONObject();
								callBackData.put("modelId", model.getId());
								callBackData.put("modelName", model.getName());
								keyboardButton.put("callback_data", callBackData.toString());
								inlineKeyboardButtonRow.put( keyboardButton );
								
								
								if((rowCounter)%3==0){
									inline_keyboard.put( inlineKeyboardButtonRow );
									inlineKeyboardButtonRow = new JSONArray();
								}
								
								rowCounter++;
							}
							
							inline_keyboard.put( inlineKeyboardButtonRow );
							
							
							
						}else if(sourceMsg!=null && sourceMsg.equalsIgnoreCase("/faq")){
							JSONObject replyKeyboardRemove = new JSONObject();
							replyKeyboardRemove.put("remove_keyboard", true);
							replyKeyboardRemove.put("selective", false);
							jsob.put("reply_markup", replyKeyboardRemove);
						}
					}
					
					
					/*
					if(inline_keyboard!=null && inline_keyboard.length()>0){
						reply_markup.put("inline_keyboard", inline_keyboard);
						reply_markup.put("resize_keyboard", true);
						reply_markup.put("one_time_keyboard", true);
						reply_markup.put("selective", false);
						jsob.put("reply_markup", reply_markup);
					}*/
					
					logger.info("  xxyy req url-> ["+url+"] >>>> ::: "+jsob.toString());
					Content content = Request.Post(url).bodyString(jsob.toString() ,ContentType.create("application/json", Consts.UTF_8.name())).execute().returnContent();
					String response = content.asString();
					
					logger.info(" xxxyyy <<<< ::: "+response);
					
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
