package tmall.util;

import java.sql.Timestamp;
import java.util.Date;

public class DateUtil {
	//将实体类中的Date转换为数据库中的datetime
	public static Timestamp dForT(Date d) {
		if(d == null) {
			return null;
		}
		return new Timestamp(d.getTime());
	}
	//将数据库中的datetime类型的字段转换为实体类中的Date
	public static Date tForD(Timestamp t) {
		if(t == null) {
			return null;
		}
		return new Date(t.getTime());
	}
}
