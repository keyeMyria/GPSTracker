package test.gpstracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static final String FORMAT_DATETIME = "yyyy/MM/dd HH:mm:ss";

	
	public static Date string2date(String value) {
	      SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);  
	      try {  
	          return sdf.parse(value);  
	      } catch (ParseException e) {  
	          return null;  
	      }
	  } 
	
	public static String date2string(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}
	
}
