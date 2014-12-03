package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import entity.Message;
import entity.Box;

@Remote
public interface IMailBoxManager {


	public Collection<Message> readAUserNewMessages(String userName); //Voir les messages non lus d'un utilisateur

	public Collection<Message> readAUserAllMessages(String receiverName); // Voir tous les messages d'un utilisateur

	public Collection<Message> readNewMessages(String userName); //Lire les nouveaux messages

	public boolean deleteAUserMessage(int messageId); //Supprimer le message d'un utilisateur

	public boolean deleteAUserReadMessages(String userName); //Supprimer les messages lus d'un utilisateur

	public boolean sendAMessageToABox(String subject, String body,
			String receiverName, String senderName); //Envoyer un message

	public boolean addMailBox(String userName); //Creer une boite mail

	public boolean removeUserMailBox(String userName); //Supprimer une boite mail

	public Message getMessageById(int messageId);

	public Box getBoxByUserId(int userId);

	public boolean sendNews(String subject, String body, String senderName); //ENvoyer une news
}
