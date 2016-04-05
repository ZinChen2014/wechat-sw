package com.desksoft.wechat.service;

import java.text.DateFormat;
import java.util.Date;

public class StrSQLService {
	
	public static String getSQLByDatePorid(Date startDate,Date endDate,String SQL){
		if(startDate!=null&&endDate!=null){
			int f = startDate.compareTo(endDate);
			if(f>0){
				SQL += " and createTime > '" + DateFormat.getDateInstance().format(startDate)+"'" ;
			}else if(f<0){
				SQL += " and createTime > '" + DateFormat.getDateInstance().format(startDate)+"' and createTime < '" + DateFormat.getDateInstance().format(endDate) +"'" ;
			}else if(f==0){
				SQL += " and date(createTime) = '" + DateFormat.getDateInstance().format(startDate)+"'" ;
			}
		}else{
			if(startDate!=null){
				SQL += " and createTime > '" + DateFormat.getDateInstance().format(startDate)+"'" ;
			}
			if(endDate!=null){
				SQL += " and createTime < '" + DateFormat.getDateInstance().format(endDate) +"'" ;
			}
		}
		return SQL;
	}
}
