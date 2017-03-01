package com.ailk.o2p.portal.bo;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
public class DefaultVlueUtils implements Serializable{
	private static final long serialVersionUID = 2408927208072972593L;

	public static final String DEFAULT_VALUE_FOR_EXP = "2037-12-31"; 
	public static final Integer DEFAULT_VALUE_FOR_REDUCERES = 0;
	public static final Integer DEFAULT_VALUE_FOR_PRIORITY = 500;
	
	/**
	 * 
	 * @param num 
	 *        0       获取当前时间(yyyy-MM-dd)
	 *        -1   获取当前时间(yyyy-MM-dd HH:mm:ss)
	 *        -2    获取当前时间戳 
	 * @return
	 */
	public static String getNowDate(Integer num) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (num == -2) {
			return c.getTimeInMillis() + "";
		}

		StringBuffer sb = new StringBuffer();
		sb.append(c.get(Calendar.YEAR) ).append(num).append("-")
				.append((c.get(Calendar.MONTH) + 1)<10?"0"+(c.get(Calendar.MONTH) + 1):(c.get(Calendar.MONTH) + 1)).append("-")
				.append(c.get(Calendar.DAY_OF_MONTH)<10?"0"+c.get(Calendar.DAY_OF_MONTH):c.get(Calendar.DAY_OF_MONTH));
		if (num == -1) {
			sb.append(" ").append(c.get(Calendar.HOUR_OF_DAY)).append(":")
					.append(c.get(Calendar.MINUTE)).append(":")
					.append(c.get(Calendar.SECOND));
		}
		return sb.toString();
	}
	
}
