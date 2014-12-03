package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import entity.FinalUser;

@Remote
public interface IDirectoryManager {
	public boolean addUser(String userName, String password);

	public boolean removeUser(String userName);

	public Collection<FinalUser> lookupAllUsers();

	public FinalUser getUser(String userName);

	public FinalUser getUserById(int userId);

	public String lookupAUserRights(String userName);

	public boolean updateAUserRights(String userName, boolean readRight,
			boolean writeRight);

	public boolean checkPassword(String userName, String password);
}
