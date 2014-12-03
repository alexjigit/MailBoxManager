package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.InitialContext;

import ejb.IDirectoryManager;
import ejb.IMailBoxManager;

import entity.FinalUser;
import entity.Message;

public class MailBoxClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			InitialContext ic = new InitialContext();

			IMailBoxManager mb = (IMailBoxManager) ic
					.lookup("ejb.IMailBoxManager");
			IDirectoryManager db = (IDirectoryManager) ic
					.lookup("ejb.IDirectoryManager");

			// Menu

			boolean exit = false;

			Collection<Message> messages = new ArrayList<Message>();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			Iterator <Message> iterator = null;
			String userName;
			String password;

			do {

				System.out.println("** MENU **");
				System.out.println("1. Read a user new messages");
				System.out.println("2. Read a user all messages");
				System.out.println("3. Delete a user message");
				System.out.println("4. Delete a user read messages");
				System.out.println("5. Send Message");
				System.out.println("6. Add user");
				System.out.println("7. Remove user");
				System.out.println("8. Send news");

				System.out.println("0. Quit");

				switch (Integer.parseInt(in.readLine())) {

				// READ A USER NEW MSG
				case 1:
					System.out.println("User name : ");
					userName = in.readLine();

					if (db.getUser(userName) != null) {
						messages = mb.readAUserNewMessages(userName);
						
						System.out.println("*****NEW MESSAGES OF " + userName + "*****");
						System.out.println("**********************\n");
						
						if (messages.size() != 0) {
							iterator = messages.iterator();
							
							while(iterator.hasNext())
								System.out.print(iterator.next());
							
							System.out.println();
							

						}

						else
							System.out.println("No message");
					} else
						System.out.println("User not registered");

					System.out.println();

					break;

				// READ ALL MSG
				case 2:
					System.out.println("User name : ");
					userName = in.readLine();
					if (db.getUser(userName) != null) {

						messages = mb.readAUserAllMessages(userName);
						
						System.out.println("*****ALL MESSAGES OF " + userName + "*****");
						System.out.println("**********************\n");
						
						if (messages.size() != 0) {

							iterator = messages.iterator();
							while(iterator.hasNext())
								System.out.print(iterator.next());
							
							System.out.println();
							
						} else
							System.out.println("No message");
					} else
						System.out.println("User not registered");

					System.out.println();

					break;

				// DELETE A USER MSG
				case 3:
					System.out.println("Message Id : ");
					int messageId = Integer.parseInt(in.readLine());
					if (mb.deleteAUserMessage(messageId))
						System.out.println("Message deleted");

					else
						System.out.println("Message n°" + messageId
								+ " doesn't exist");

					System.out.println();

					break;

				// DELETE A USER READ MSG
				case 4:
					System.out.println("User name :");
					userName = in.readLine();
					if (db.getUser(userName) != null) {

						if (mb.deleteAUserReadMessages(userName))
							System.out.println("Message deleted");

						else
							System.out.println("No new message");
					} else
						System.out.println("User not registered");

					System.out.println();
					break;

				// SEND MSG
				case 5:
					System.out.println("Sender name :");
					String senderName = in.readLine();
					if (db.getUser(senderName) == null) {
						System.out.println("This user doesn't exist");
						break;
					}

					System.out.println("Password :");
					password = in.readLine();

					if (password.equals(db.getUser(senderName).getPassword())) {
						System.out.println("Receiver name :");
						String receiverName = in.readLine();

						if (db.getUser(receiverName) != null) {
							System.out.println("Subject :");
							String subject = in.readLine();
							System.out.println("Body :");
							String body = in.readLine();

							mb.sendAMessageToABox(subject, body, receiverName,
									senderName);
						} else
							System.out.println("Receiver doesn't exist");
					} else
						System.out.println("Wrong password");
					break;

				// ADD USER
				case 6:
					System.out.println("User name :");
					userName = in.readLine();

					if (db.getUser(userName) != null)
						System.out.println("User already exists");

					else {
						System.out.println("Password :");
						password = in.readLine();
						db.addUser(userName, password);
						System.out.println("User added");
					}

					break;

				// REMOVE USER
				case 7:
					System.out.println("User name : ");
					userName = in.readLine();

					if (db.getUser(userName) == null)
						System.out.println("User doesn't exist");
					else {
						db.removeUser(userName);
						System.out.println("User deleted");
					}

					break;

				// SEND NEWS
				case 8:
					System.out.println("Sender name :");
					senderName = in.readLine();

					if (db.getUser(senderName) == null)
						System.out.println("This user doesn't exist");

					else {

						if (!db.getUser(senderName).getWriteAccess())
							System.out
									.println("This sender cannot send a news");

						else {
							System.out.println("Password :");
							password = in.readLine();

							if (password.equals(db.getUser(senderName)
									.getPassword())) {
								System.out.println("Subject :");
								String subject = in.readLine();

								System.out.println("Body :");
								String body = in.readLine();

								mb.sendNews(subject, body, senderName);
								System.out.println("News sent");

							} else
								System.out.println("Wrong password");
						}
					}
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
