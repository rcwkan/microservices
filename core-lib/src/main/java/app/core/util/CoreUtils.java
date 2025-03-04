package app.core.util;

import org.apache.commons.codec.digest.DigestUtils;

public class CoreUtils {

	public static String hashString(String string) {
		 
		return   DigestUtils.sha256Hex(string);
	}
	

}
