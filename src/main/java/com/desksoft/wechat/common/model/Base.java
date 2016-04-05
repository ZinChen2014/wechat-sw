package com.desksoft.wechat.common.model;

import com.desksoft.wechat.common.model.base.BaseUser;
import com.jfinal.plugin.activerecord.Page;

@SuppressWarnings("serial")
public class Base extends BaseUser<Base> {
	
	public static final Base dao = new Base();
	public static final int IS_DELETE_NO = 0;
	public static final int IS_DELETE_YES = 1;
	
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<Base> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from user order by id asc");
	}
	
	public boolean deleteById(Object idValue){
		return dao.findById(idValue).set("isDelete", IS_DELETE_NO).update();
	}
	
}
