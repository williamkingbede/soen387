package domain.user;

import org.dsrg.soenea.domain.DomainObject;
import org.dsrg.soenea.domain.annotation.ExternalProducer;
import org.dsrg.soenea.domain.annotation.IDFieldType;
import org.dsrg.soenea.domain.annotation.Interface;
import domain.user.UserProxy;

import domain.user.IUser;

@IDFieldType (Long.class)
@Interface (IUser.class)
@ExternalProducer (UserProxy.class)
public class User extends DomainObject<Long> implements IUser{

	private String username;
	private String password;
	private boolean hasChangedPassword = false;
	
	
	public User(String username) {
		this(0, 1, username);
	}
	
	public User(long id, String username) {
		this(id, 1, username);
	}
	
	public User(long id, long version, String username) {
		super(id, version);
		this.username = username;
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
		this.hasChangedPassword = true;
	}

	public boolean hasChangedPassword() {
		return hasChangedPassword;
	}

}
