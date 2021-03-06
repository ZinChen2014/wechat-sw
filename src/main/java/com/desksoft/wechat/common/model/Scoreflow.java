package com.desksoft.wechat.common.model;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.desksoft.wechat.common.model.base.BaseRefeeflow;
import com.desksoft.wechat.service.StrSQLService;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Scoreflow extends BaseRefeeflow<Scoreflow> {
	
	public static final Scoreflow dao = new Scoreflow();
	
	public Page<Scoreflow> getScoreFlowPaginateList(int pageNumber, int pageSize,Date startDate,Date endDate,String openID) {
		String SQL = "from scoreflow where isdelete = 1 ";
		SQL = StrSQLService.getSQLByDatePorid(startDate, endDate, SQL);
		if(StringUtils.isNotBlank(openID)&&!("default".equalsIgnoreCase(openID))){
			SQL += " and openID like '%" + openID + "%'" ;
		}
		SQL +=" order by id asc";
		return paginate(pageNumber, pageSize, "select *", SQL);
	}
	
	//  `userID` int(11) NOT NULL COMMENT 'userID用户表ID',
	  //`openID` varchar(32) NOT NULL COMMENT '微信号。该积分消费时的绑定微信号(不能为空）',
	  //type` int(4) NOT NULL DEFAULT '1' COMMENT 'type=1时添加积分type=2时减少积分',
	  //`Score` int(11) DEFAULT NULL COMMENT '积分消费值(>0）',

	
	public boolean addScoreFlow(String openID,int userID,int type,int Score){
		return new Scoreflow().set("openID", openID).set("userID", userID)
				.set("updateTime", new Date()).set("createTime",  new Date())
				.set("type", type).set("Score", Score).save();
	}
}
