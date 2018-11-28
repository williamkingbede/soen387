package domain.user;

import java.util.List;

import org.dsrg.soenea.domain.interf.IDomainObject;

public interface IUser extends IDomainObject<Long>{
	
	public String getUsername();
	public String getPassword();
	public void setUsername(String username);
	public void setPassword(String password);
	public boolean hasChangedPassword();
	

}
