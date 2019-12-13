package application;
import clavardage.*;

import clavardage.ClavardageManager;
import utilisateur.*;
import interfaceGraphique.*;

public class Application {

	public static void main(String[] args) {
		Utilisateur user1 = new Utilisateur("joe", new Id(1111), "192.168.1.1");
		Utilisateur user2 = new Utilisateur("paulo", new Id(22), "192.168.1.2");
		user1.getListeUtilisateurs().add(user2);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Fenetre fenetre = new Fenetre(user1);
				}
			});

	}

}
