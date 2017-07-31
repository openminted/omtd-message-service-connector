package eu.openminted.messageservice.messages;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

/**
 * @author ilsp
 *
 */
public class GSON {

	private Gson gson;
	
	public GSON(){
		gson = new GsonBuilder().create();
	}
	
	public String getJSON(Object o){
		return gson.toJson(o);
	}
}
