package application;
import clavardage.ClavardageManager;
import interfaceGraphique.Controleur;
import utilisateur.*;

public class Application {

	public static void main(String[] args) {
		Utilisateur serverGuy = new Utilisateur("servguy", new Id(1), "192.168.1.40");
		Utilisateur client1 = new Utilisateur("client", new Id(2), "192.168.1.40");
		serverGuy.getListeUtilisateurs().add(client1);

		
		Controleur controleur = new Controleur(serverGuy);
		
		ClavardageManager com = new ClavardageManager(serverGuy, controleur);
	}

}
