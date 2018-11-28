package domain.user;

import java.sql.SQLException;

import org.dsrg.soenea.domain.DomainObjectCreationException;
import org.dsrg.soenea.domain.MapperException;
import org.dsrg.soenea.domain.proxy.DomainObjectProxy;

import domain.user.mapper.UserInputMapper;

public class UserProxy extends DomainObjectProxy<Long, User> implements IUser{

	public UserProxy(Long id) {
		super(id);
	}

	@Override
	protected User getFromMapper(Long id) throws MapperException,
			DomainObjectCreationException {
		try {
			return UserInputMapper.find(getId());
		} catch (SQLException e) {
			throw new MapperException(e);
		}
	}

	public String getPassword() {
		return getInnerObject().getPassword();
	}

	public String getUsername() {
		return getInnerObject().getUsername();
	}

	public void setPassword(String password) {
		getInnerObject().setPassword(password);
	}

	public void setUsername(String username) {
		getInnerObject().setUsername(username);
	}
	
	public boolean hasChangedPassword() {
		return getInnerObject().hasChangedPassword();
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof UserProxy) return getId().equals(((UserProxy)obj).getId());
		if (obj instanceof User) return getId().equals(((User)obj).getId());
		return false;		
	}
	
}
