package ejb;

import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Box;
import entity.FinalUser;
import entity.Message;

@WebService
@Stateless
public class MailBoxManagerBean implements IMailBoxManager {

	@PersistenceContext(unitName = "MicroProjetTestEJB")
	private EntityManager em;
	private IDirectoryManager directoryManager;

	public MailBoxManagerBean() {
		try {

			InitialContext ic = new InitialContext();
			directoryManager = (IDirectoryManager) ic
					.lookup("ejb.IDirectoryManager");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@WebMethod
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Message> readAUserNewMessages(String userName) {

		try {

			Query q1 = em
					.createQuery("select m from Message m where m.alreadyRead = false and m.receiverName = :userName");
			q1.setParameter("userName", userName);
			Collection<Message> results = q1.getResultList();

			return results;
		} catch (NoResultException e) {
			return null;
		}
	}

	@WebMethod
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Message> readAUserAllMessages(String receiverName) {

		try {
			Query q1 = em
					.createQuery(
							"select m from Message m where m.receiverName = :receiverName ",
							Message.class);
			q1.setParameter("receiverName", receiverName);
			Collection<Message> results = q1.getResultList();

			return results;
		} catch (NoResultException e) {
			return null;
		}
	}

	@WebMethod
	@Override
	public boolean deleteAUserMessage(int messageId) {

		try {

			Message message = getMessageById(messageId);

			if (message != null) {
				em.remove(message);
				em.flush();
				return true;
			} else
				return false;

		} catch (NoResultException e) {
			return false;
		}
	}

	@WebMethod
	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteAUserReadMessages(String userName) {

		try {
			Query q1 = em
					.createQuery("select m from Message m where m.receiverName = :userName and m.alreadyRead = true");
			q1.setParameter("userName", userName);

			Collection<Message> results = q1.getResultList();

			if (results.size() != 0) {

				for (Message message : results) {

					em.remove(message);
					em.flush();

				}
				return true;
			}

			else {

				return false;
			}

		} catch (NoResultException e) {
			return false;
		}
	}

	
	@WebMethod
	@Override
	public boolean sendAMessageToABox(String subject, String body,
			String receiverName, String senderName) {

		FinalUser user = directoryManager.getUser(receiverName);

		if (user != null) {
			int userId = directoryManager.getUser(receiverName).getId();
			Box box = getBoxByUserId(userId);

			if (box != null) {
				Message message = new Message(senderName, receiverName,
						subject, body, box);

				em.persist(message);
				em.flush();
				em.refresh(message);
				// System.out.println(subject+body);
				return true;
			}

			else
				return false;

		} else

			return false;
	}

	// SEND NEWS
	@SuppressWarnings("unchecked")
	@Override
	public boolean sendNews(String subject, String body, String senderName) {
		/*
		 * TODO Message message = new Message(senderName, receiverName, subject,
		 * body); em.persist(message);
		 */
		try {

			FinalUser senderUser = directoryManager.getUser(senderName);
			if (senderUser.getWriteAccess()) {

				Query query = em.createQuery("SELECT m from Box  m", Box.class);
				Collection<Box> Boxs = (Collection<Box>) query.getResultList();

				for (Box box : Boxs) {
					FinalUser user = directoryManager.getUserById(box
							.getuserId());

					if (user.getReadAccess()) {
						Message message = new Message(senderName,
								user.getuserName(), subject, body, box);
						em.persist(message);
					}

				}

				return true;
			}

			else
				return false;
		} catch (NoResultException e) {
			return false;
		}

	}

	@Override
	public boolean addMailBox(String userName) {

		FinalUser user = directoryManager.getUser(userName);
		int userId = user.getId();

		Box box = new Box(userName + "Box", userId);
		em.persist(box);
		em.flush();
		em.refresh(box);
		/*
		 * Box box = new Box(boxName,userId); System.out.println(userId);
		 * em.persist(box); em.flush(); em.refresh(box);
		 */
		return true;
	}

	@Override
	public boolean removeUserMailBox(String userName) {

		try {
			int userId = directoryManager.getUser(userName).getId();
			Query q = em.createQuery(
					"select b from Box b where b.userId = :userId", Box.class);
			q.setParameter("userId", userId);
			Box box = (Box) q.getSingleResult();

			if (box != null) {
				em.remove(box);
				em.flush();
				return true;
			} else
				return false;
		} catch (NoResultException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Message> readNewMessages(String userName) {

		try {

			Query q1 = em
					.createQuery("select m from Message m where m.alreadyRead = false and m.receiverName = :userName");
			q1.setParameter("userName", userName);
			Collection<Message> results = q1.getResultList();
			for (Message message : results)
				message.setIsRead();
			return results;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Message getMessageById(int messageId) {

		try {
			Query q = em.createQuery(
					"SELECT m from Message m WHERE m.messageId = :messageId",
					Message.class);
			q.setParameter("messageId", messageId);
			return (Message) q.getSingleResult();
		}

		catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Box getBoxByUserId(int userId) {

		try {
			Query q = em.createQuery(
					"SELECT b from Box b WHERE b.userId = :userId", Box.class);
			q.setParameter("userId", userId);
			return (Box) q.getSingleResult();
		}

		catch (NoResultException e) {
			return null;
		}
	}

}
