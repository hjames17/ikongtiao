package com.wetrack.ikongtiao.notification.services.messages;

import com.wetrack.ikongtiao.notification.services.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class JPushMessage implements Message {
	private String title;
	private String content;
	private int id;
	private List<String> aliasList;
	private List<String> tagList;
	private Map<String, String> extras;
	
	public JPushMessage(){
		super();
		//ios的通知声音需要指定，否则ios的推送发不出声音
		extras = new HashMap<String, String>();
//		Map<String, String> ios = new HashMap<String, String>();
//		ios.put("sound", "alert.caf");
//		extras.put("ios", ios);
	}
	
	public void addAlias(String alias){
		if(aliasList == null){
			aliasList = new ArrayList<String>();
		}
		
		if(!aliasList.contains(alias)){
			aliasList.add(alias);
		}
	}
	
	public void addTag(String tag){
		if(tagList == null){
			tagList = new ArrayList<String>();
		}
		
		if(!tagList.contains(tag)){
			tagList.add(tag);
		}
	}
}
