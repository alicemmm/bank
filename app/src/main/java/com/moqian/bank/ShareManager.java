package com.moqian.bank;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareManager {
	private Context context;
	public static String NAME ="Share";
	SharedPreferences refPreferences;
	Editor editor ;
	public ShareManager(Context context){
		this.context = context;
		 refPreferences = context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
		 editor = refPreferences.edit();
	}
	public void setData(String key,Object value){
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
			
		}else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		}else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
			
		}else if (value instanceof Long) {
			editor.putLong(key, (Long) value);
			
		}else if (value instanceof String) {
			editor.putString(key, (String) value);
		}
		editor.commit();
		
		
	}
	public String getStringData(String key,String defalut){
		return refPreferences.getString(key, defalut);
	}
	public Object getData(String key,Object defaultValue){
		Map<String, Object> map = (Map<String, Object>) refPreferences.getAll();
		if (map.containsKey(key)) {
			Set<Entry<String, Object>> set = map.entrySet();
			Iterator<Entry<String, Object>> iterator = set.iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				entry.getKey();
				if (entry.getKey().equals(key)) {
					Object value = entry.getValue();
					return value;
				}
				
			}
			
			
		}else{
			return defaultValue;
		}
		return defaultValue;
		
	}

}
