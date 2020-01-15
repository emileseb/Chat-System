package application;
import baseDeDonnees.LocalDB;
import clavardage.ClavardageManager;
import conversation.Historique;
import conversation.Message;
import interfaceGraphique.Controleur;
import utilisateur.*;

import java.util.ArrayList;

public class Application {

	public static void main(String[] args) {
		Utilisateur user = new Utilisateur();
		user.getDatabase().clearDatabase();
		
		Controleur control = new Controleur(user);
		
		ClavardageManager clav = new ClavardageManager(user, control);
		
	}

}
