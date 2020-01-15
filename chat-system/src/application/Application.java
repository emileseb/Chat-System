package application;
import clavardage.ClavardageManager;
import interfaceGraphique.Controleur;
import utilisateur.*;

public class Application {

	public static void main(String[] args) {
		Utilisateur user = new Utilisateur();
		
		Controleur control = new Controleur(user);
		
		ClavardageManager clav = new ClavardageManager(user, control);
		
	}

}
