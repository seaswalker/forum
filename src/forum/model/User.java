package forum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 * @author skywalker
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 3042370756553127524L;
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String avatar;
	//是否是管理员
	private boolean isAdmin = false;
	//是否是版主
	private boolean isManager = false;
	//哪些板块的版主
	private List<Integer> sections = new ArrayList<Integer>();
	//可见，false就关小黑屋
	private boolean visible = true;
	
	public User(int id) {
		this.id = id;
	}
	
	public User(int id, String password) {
		this.id = id;
		this.password = password;
	}

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User() {}
	
	/**
	 * 拼接cookie字符串
	 * username-password
	 */
	public String getCookieStr() {
		StringBuffer sb = new StringBuffer();
		sb.append(username).append("-").append(password);
		return sb.toString();
	}
	
	public boolean getIsManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public List<Integer> getSections() {
		return sections;
	}
	public void setSections(List<Integer> sections) {
		this.sections = sections;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}