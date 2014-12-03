package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.naming.InitialContext;

import ejb.IDirectoryManager;

import entity.FinalUser;

public class AdminClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			InitialContext ic = new InitialContext();
			IDirectoryManager db = (IDirectoryManager) ic
					.lookup("ejb.IDirectoryManager");

			// Menu

			boolean exit = false;
			Collection<FinalUser> users = new ArrayList<FinalUser>();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			String userName;
			String password;

			do {
				System.out.println("** MENU **");
				System.out.println("1. Add user");
				System.out.println("2. List of all users");
				System.out.println("3. Delete user");
				System.out.println("4. Look a user rights");
				System.out.println("5. Update user rights");

				System.out.println("0. Quit");

				switch (Integer.parseInt(in.readLine())) {

				// Ajout user
				case 1:

					System.out.println("User name :");
					userName = in.readLine();

					if (db.getUser(userName) != null)
						System.out.println("User already exists");

					else {
						System.out.println("Password :");
						password = in.readLine();
						db.addUser(userName, password);
						System.out.println("User added");
						System.out.println();
					}

					break;

				// Voir tous les users
				case 2:
					System.out.println("*****REGISTERED USERS*****");
					System.out.println("**************************\n");
					
					users = db.lookupAllUsers();
					
					Iterator<FinalUser> e = users.iterator();
					while(e.hasNext())
						System.out.print(e.next());
					
					System.out.println();
					
					/*for (FinalUser finalUser : users) {
						System.out.println("Id : " + finalUser.getId()
								+ "  User Name : " + finalUser.getuserName());
					}*/
					break;

				// Supprimer user
				case 3:
					System.out.println("User name : ");
					userName = in.readLine();

					if (db.getUser(userName) == null)
						System.out.println("User doesn't exist");
					else {
						db.removeUser(userName);
						System.out.println("User deleted");
						System.out.println();
					}

					break;

				// Voir droits
				case 4:
					System.out.println("User name :");
					userName = in.readLine();

					if (db.getUser(userName) == null)
						System.out.println("This user doesn't exist");

					else
						System.out.println(db.lookupAUserRights(userName));
					break;

				// Modification des droits
				case 5:
					System.out.println("User name :");
					userName = in.readLine();

					if (db.getUser(userName) == null)
						System.out.println("This user doesn't exist");

					else {

						System.out.println("Choose rights :");
						System.out
								.println("0.None   1.Read   2.Write   3.Read/Write");

						switch (Integer.parseInt(in.readLine())) {

						case 0:
							db.updateAUserRights(userName, false, false);
							break;

						case 1:
							db.updateAUserRights(userName, true, false);
							break;

						case 2:
							db.updateAUserRights(userName, false, true);
							break;

						case 3:
							db.updateAUserRights(userName, true, true);
							break;

						default:
							System.out.println("Incorrect choice");
							break;
						}
						System.out.println("Change successfully");
						System.out.println();
					}

					break;

				// EXIT
				case 0:
					exit = true;
					break;
				}
			} while (!exit);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
