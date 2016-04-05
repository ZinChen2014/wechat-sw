package com.desksoft.wechat.common.model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.desksoft.wechat.common.model.base.BaseUser;
import com.desksoft.wechat.common.utils.PasswordUtil;
import com.desksoft.wechat.common.utils.RandomUtil;
import com.jfinal.kit.StrKit;

@SuppressWarnings("serial")
public class User extends BaseUser<User> {
	
	public static final User dao = new User();
	
	public static final int IS_DELETE_NO = 0;
	public static final int IS_DELETE_YES = 1;
	
	
	/**
	 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
/*	public Page<User> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select *", "from user order by id asc");
	}*/
	
	public Map<String, Object> checkLoginByUser(User user){
		Map<String, Object> map = new HashMap<String, Object>();
		User exist = getUserByUserName(user.getUsername());
		if(exist==null){
			map.put("flag", false);
			map.put("msg", "该用户不存在！");
		}else{
			String oldPassword = exist.getPassword(); // 原密码
			String oldRadomKey = exist.getRemark(); // 原来的随机窜
			Boolean result = PasswordUtil.isPasswordCorrect(oldPassword, user.getPassword(), oldRadomKey);
			if(result){
				map.put("flag", true);
				map.put("msg", "登录成功！");
			}else{
				map.put("flag", false);
				map.put("msg", "密码输入错误");
			}
		}
		
		return map;
	}
	
	public Map<String, Object> updatePassword(String username,String password,String newPwd){
		Map<String, Object> map = new HashMap<String, Object>();
		User exist = getUserByUserName(username);
		String oldRadomKey = exist.getRemark(); // 原来的随机窜
		Boolean result = PasswordUtil.isPasswordCorrect(exist.getPassword(), password, oldRadomKey);
		if(!result){
			map.put("flag", false);
			map.put("msg", "密码输入错误");
		}else{
			String newPassword = PasswordUtil.getEncyptedOperPassword(newPwd, oldRadomKey);
			if(exist.set("password", newPassword).update()){
				map.put("flag", true);
			}else{
				map.put("flag", false);
				map.put("msg", "修改密码失败");
			}
		}
		return map;
	}

	
	/**
	 * 获得登录用户 type=2
	 * @param user_name
	 * @return
	 */
	public User getUserByUserName(String user_name){
		return dao.findFirst("select * from user where username=? and isdelete=1 and type=2", user_name);
	}
	
	/**
	 * type=1位关注的微信用户
	 * @param openId
	 * @return
	 */
	public User getUserByOpenId(String openid){
		return dao.findFirst("select * from user where openid=? and isdelete=1 and type=1", openid);
	}
	
	/**
	 * type=1位关注的微信用户
	 * @param tjCode 推荐码
	 * @return
	 */
	public User getUserBytjCode(String tjCode){
		return dao.findFirst("select * from user where remark=? and isdelete=1 and type=1", tjCode);
	}
	
	
	/**
	 * 判断是否为有用的推荐码
	 * @param tjCode  openId
	 * @return
	 */
	public boolean isValidTjCode(String tjCode,String openId){
		User refeer = User.dao.getUserBytjCode(tjCode);//判断推荐码不是本人的
		if(refeer!=null&&(!openId.equals(refeer.getOpenid()))){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean addScoreByTjCode(String tjCode,int amount){
		User refeer = User.dao.getUserBytjCode(tjCode);
		if(refeer!=null){
			int score = refeer.getScore()==null?0:refeer.getScore()+amount;
			return refeer.set("score", score).set("updateTime", new Date()).update();
		}
		return false;
	}
	
	
	
	public boolean addScoreByOpenId(String openId,int amount){
		if(amount==0){
			return false;
		}
		User user = User.dao.getUserByOpenId(openId);
		if(user!=null){
			int score = user.getScore()==null?0:user.getScore()+amount;
			if(score>=0){
				return user.set("score", score).set("updateTime", new Date()).update();
			}else{
				return user.set("score", 0).set("updateTime", new Date()).update();
			}
			
		}
		return false;
	}
	
	
	public boolean deleteById(Object idValue){
		return dao.findById(idValue).set("isDelete", IS_DELETE_NO).update();
	}
	
	public boolean addMsgUser(String openID,String wxName){
		boolean flag = true;
		User exis = dao.findFirst("select * from user where openid=? and isdelete=1 and type=1", openID);
		if(exis==null){
			User user = new User();
			String remark = createRemark();
			user.set("type", 1).set("openid", openID).set("createTime", new Date())
			.set("remark", remark).set("updateTime", new Date());
			if(StrKit.notBlank(wxName)){
				user.set("wxName", wxName);
			}	
			flag = user.save();
		}else{
			flag = exis.set("isFollow", 1).set("wxName", wxName).set("updateTime", new Date()).update();
		}
		return flag;
	}
	
	/**
	 * 找出不重复的8位推荐码
	 * @return
	 */
	private String createRemark(){
		String remark = RandomUtil.getRandomString(8);
		List<User> list =dao.find("select * from user where remark=? and type=1", remark);
		if(list!=null&&list.size()>0){
			remark = createRemark();
		}
		return remark;
		
	}
	
	public boolean cancelFollow(String openID){
		User exis =User.dao.findFirst("select * from user where openid=?", openID);
		if(exis!=null){
			boolean flag = exis.set("isFollow", 0).set("updateTime", new Date()).update();
			if(!flag){
				System.out.println("update user  function is cancelFollow fail:"+exis.getId());
				return false;
			}
		}
		return true;
	} 
	
}
