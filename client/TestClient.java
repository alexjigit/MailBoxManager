package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.naming.InitialContext;

import ejb.IDirectoryManager;
import ejb.IMailBoxManager;

import entity.Message;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			InitialContext ic = new InitialContext();
			IDirectoryManager db = (IDirectoryManager) ic
					.lookup("ejb.IDirectoryManager");

			IMailBoxManager mb = (IMailBoxManager) ic
					.lookup("ejb.IMailBoxManager");

			String userName;
			String password;
			boolean passwordOK = false;
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
		
			
			// LOGIN
			System.out.println("**************************");
			System.out.println("*****AUTHENTIFICATION*****");
			System.out.println("**************************");
			System.out.println();
			do {
				System.out.println("Enter your user name :");
				userName = in.readLine();

				if (db.getUser(userName) != null) {
					System.out.println("Enter your password :");
					password = in.readLine();

					if (db.checkPassword(userName, password))
						passwordOK = true;

					else
						System.out.println("Wrong password");
					System.out.println();
				}

				else
					System.out.println("User not registered");

			} while (!passwordOK);

			System.out.println("Welcome " + userName + "\n\n");
			
			// MENU

			boolean exit = false;
			Collection<Message> messages = new ArrayList<Message>();

			do {
				System.out.println("** MENU **");
				System.out.println("1. Read new messages");
				System.out.println("2. Read all messages");
				System.out.println("3. Delete message");
				System.out.println("4. Send message");
				System.out.println("0. Quit");

				switch (Integer.parseInt(in.readLine())) {

				// Lire nouveaux messages
				case 1:
					messages = mb.readNewMessages(userName);

					if (messages.size() != 0) {
						for (Message message : messages) {
							System.out.println("Id Message :"
									+ message.getmessageId());
							System.out.println("Le " + message.getsendingDate()
									+ "Sender : " + message.getsenderName());
							System.out.println("Subject : "
									+ message.getsubject());
							System.out.println(message.getbody());
							;
							System.out.println();
						}
					} else

						System.out.println("No new message");
					System.out.println();

					break;

				// Lire tous les messages
				case 2:
					messages = mb.readAUserAllMessages(userName);

					if (messages.size() != 0) {
						for (Message message : messages) {
							System.out.println("Id Message :"
									+ message.getmessageId());
							System.out.println("Le " + message.getsendingDate()
									+ "Sender : " + message.getsenderName());
							System.out.println("Subject : "
									+ message.getsubject());
							System.out.println(message.getbody());
							;
							System.out.println();
						}
					} else

						System.out.println("No message");

					System.out.println();

					break;

				// Supprimer message
				case 3:
					System.out.println("Id Message :");

					if (mb.deleteAUserMessage(Integer.parseInt(in.readLine())))
						System.out.println("Message deleted");

					else
						System.out.println("Message n°"
								+ Integer.parseInt(in.readLine())
								+ " doesn't exist");
					break;

				// Envoyer message
				case 4:

					System.out.println("Receiver name :");
					String receiverName = in.readLine();
					System.out.println("Subject :");
					String subject = in.readLine();
					System.out.println("Body :");
					String body = in.readLine();

					if (mb.sendAMessageToABox(subject, body, receiverName,
							userName))
						System.out.println("Message sent");
					else
						System.out.println("This receiver doesn't exist");

					System.out.println();
					break;

				// EXIT
				case 0:
					exit = true;
					break;

				default:
					System.out.println("Incorrect choice");
					break;
				}

			} while (!exit);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
