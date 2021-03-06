package forum.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import forum.dao.UserDao;
import forum.dao.base.BaseDao;
import forum.model.Shield;
import forum.model.ShieldSearchResult;
import forum.model.User;
import forum.service.UserService;
import forum.service.base.BaseServiceImpl;
import forum.util.StringUtil;

/**
 * User服务
 * @author skywalker
 *
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	private UserDao userDao;
	
	@Resource(name = "userDao")
	@Override
	protected void setBaseDao(BaseDao<User> baseDao) {
		this.userDao = (UserDao) baseDao;
		super.baseDao = baseDao;
	}
	
	@Override
	public boolean isUserExists(String username) {
		User user = new User();
		user.setUsername(username);
		return userDao.find(user) != null;
	}
	
	@Override
	public User login(String username, String password) {
		String encoded = StringUtil.md5(password);
		User user = new User();
		user.setUsername(username);
		user.setPassword(encoded);
		return userDao.find(user);
	}

	@Override
	public User checkAuthority(String username) {
		User user = new User();
		user.setUsername(username);
		user.setAdmin(false);
		user = userDao.find(user);
		return user;
	}

	@Override
	public List<Integer> getSectionIds(int userid) {
		return userDao.getSectionIds(userid);
	}
	
	@Override
	public List<Integer> getTopSectionIds(int userId) {
		return userDao.getTopSectionIds(userId);
	}

	@Override
	public void shield(int uid, int sid, int days) {
		Calendar endTime = Calendar.getInstance();
		endTime.add(Calendar.DAY_OF_MONTH, days);
		userDao.shield(uid, sid, endTime.getTime());
	}
	
	@Override
	public int isManager(int userId, int sectionId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(us.id) from user_section us where us.uid = ").append(userId)
			.append(" and us.sid in (select s1.id from section s1 where s1.id = ").append(sectionId)
			.append(" union select s2.id from section s2 where s2.id = (select pid from section where id = ").append(sectionId)
			.append("))");
		return userDao.queryCount(sql.toString());
	}
	
	@Override
	public List<Shield> getShieldSections(int userId) {
		return userDao.getShieldSections(userId);
	}
	
	@Override
	public List<ShieldSearchResult> shieldSearchResult(String username,
			List<Integer> sectionIds) {
		return userDao.shieldSearchResult(username, sectionIds);
	}
	
	@Override
	public void unshield(String username, int sid) {
		String sql = "delete from user_shield where sid = " + sid + 
					" and uid = (select id from user where username = '" + username + "')";
		userDao.batchUpdate(sql);
	}
	
	@Override
	public User checkEmail(String email, String username) {
		User user = new User(username, null, email);
		return userDao.find(user);
	}

}
