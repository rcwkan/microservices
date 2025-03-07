package app.core.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;

import io.vertx.core.json.JsonArray;

public class CoreUtils {

	public static String hashString(String string) {
		 
		return   DigestUtils.sha256Hex(string);
	}
	
	public static List<String> toStringArray(JsonArray array){
		
		List<String> list = new ArrayList<>();
		
		array.stream().forEach(a -> list.add((String)a));
		
		return list;
		
	}
	

}
