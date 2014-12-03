package ejb;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.FinalUser;
import entity.Message;
import ejb.IDirectoryManager;

@Path("method")
public class RESTDelegator implements IDirectoryManager {

	@EJB
	private MailBoxManagerBean mailboxManager;

	/*
	 * @Path("countryCount")
	 * 
	 * @Produces(MediaType.APPLICATION_XML) public String countryCount() throws
	 * TransactionRollbackException { return
	 * "<country_count>"+bean.countryCount()+"</country_count>"; }
	 */

	@GET
	@Path("/{userName}/newMessages")
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Message> readAUserNewMessages(
			@PathParam("userName") String userName) throws NullPointerException {
		return mailboxManager.readAUserNewMessages(userName);
	}

	@GET
	@Path("/test")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() throws NullPointerException {
		return "hello";
	}

	@GET
	@Path("/{userName}/allMessages")
	@Produces(MediaType.TEXT_PLAIN)
	public Collection<Message> readAUserAllMessages(
			@PathParam("userName") String userName) throws NullPointerException {
		return mailboxManager.readAUserAllMessages(userName);
	}

	@DELETE
	@Path("/message/{Id}")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean deleteAUserMessage(@PathParam("Id") int messageId)
			throws NullPointerException {
		return mailboxManager.deleteAUserMessage(messageId);
	}

	@DELETE
	@Path("/{userName}/message")
	@Produces(MediaType.TEXT_PLAIN)
	public boolean deleteAUserReadMessages(
			@PathParam("userName") String userName) throws NullPointerException {
		return mailboxManager.deleteAUserReadMessages(userName);
	}

	@POST
	@Path("/messages/{userName}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public boolean sendAMessageToABox(@FormParam("subject") String subject,
			@FormParam("body") String body,
			@FormParam("receiverName") String receiverName,
			@FormParam("senderName") String senderName)
			throws NullPointerException {
		return mailboxManager.sendAMessageToABox(subject, body, receiverName,
				senderName);
	}

	@POST
	@Path("/{userName}/news")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public boolean sendNews(@FormParam("subject") String subject,
			@FormParam("body") String body,
			@FormParam("senderName") String senderName)
			throws NullPointerException {
		return mailboxManager.sendNews(subject, body, senderName);

	}

	@Override
	public boolean addUser(String userName, String password) {
		// TODO Stub de la méthode généré automatiquement
		return false;
	}

	@Override
	public boolean removeUser(String userName) {
		// TODO Stub de la méthode généré automatiquement
		return false;
	}

	@Override
	public Collection<FinalUser> lookupAllUsers() {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public FinalUser getUser(String userName) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public FinalUser getUserById(int userId) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	@GET
	@Produces("text/plain")
	public String lookupAUserRights(String userName) {
		// TODO Stub de la méthode généré automatiquement
		return null;
	}

	@Override
	public boolean updateAUserRights(String userName, boolean readRight,
			boolean writeRight) {
		// TODO Stub de la méthode généré automatiquement
		return false;
	}

	@Override
	public boolean checkPassword(String userName, String password) {
		// TODO Stub de la méthode généré automatiquement
		return false;
	}

}
