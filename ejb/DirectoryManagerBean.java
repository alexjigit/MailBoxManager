package ejb;

import java.util.Collection;

import javax.ejb.Stateless;

import javax.naming.InitialContext;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import entity.FinalUser;

@Stateless
public class DirectoryManagerBean implements IDirectoryManager {

	@PersistenceContext(unitName = "MicroProjetTestEJB")
	private EntityManager em;
  
	private IMailBoxManager mailBoxManager;

	public DirectoryManagerBean() {
		try {
			InitialContext ic = new InitialContext();
			mailBoxManager = (IMailBoxManager) ic.lookup("ejb.IMailBoxManager");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean addUser(String userName, String password) {

		try {
			FinalUser user = new FinalUser(userName, password);
			em.persist(user);
			em.flush();
			em.refresh(user);
			mailBoxManager.addMailBox(userName);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public boolean removeUser(String userName) {
		try {
			FinalUser user = getUser(userName);
			mailBoxManager.removeUserMailBox(userName);
			em.remove(user);
			em.flush();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<FinalUser> lookupAllUsers() {
		try {
			Query q = em.createQuery("SELECT u from FinalUser u",
					FinalUser.class);
			Collection<FinalUser> users = q.getResultList();
			return users;
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public String lookupAUserRights(String userName) {
		FinalUser user = getUser(userName);

		if (user != null)
			return ("write : " + user.getWriteAccess() + "   read : " + user
					.getReadAccess());
		else
			return ("This user doesn't exist");
	}

	@Override
	public boolean updateAUserRights(String userName, boolean readRight,
			boolean writeRight) {

		FinalUser user = getUser(userName);
		user.setReadAccess(readRight);
		user.setWriteAccess(writeRight);
mailBoxManager.sendAMessageToABox("Changement de vos droits",
				"Vos nouveaux droits sur la NewsBox\n\n Read " + readRight + "\n Write "+ writeRight,
				userName,"Admin");
		em.flush();
		return true;
	}

	@Override
	public FinalUser getUser(String userName) {
		try {
			Query q = em.createQuery(
					"SELECT u from FinalUser u WHERE u.userName = :userName",
					FinalUser.class);
			q.setParameter("userName", userName);
			return (FinalUser) q.getSingleResult();
		}

		catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public FinalUser getUserById(int userId) {
		try {
			Query q = em.createQuery(
					"SELECT u from FinalUser u WHERE u.idFinalUser = :userId",
					FinalUser.class);
			q.setParameter("userId", userId);
			return (FinalUser) q.getSingleResult();
		}

		catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean checkPassword(String userName, String password) {
		FinalUser user = getUser(userName);

		if (user != null && password.equals(user.getPassword()))
			return true;

		else
			return false;

	}
}
