package application;
import clavardage.ClavardageManager;
import utilisateur.*;

public class Application {

	public static void main(String[] args) {
		Utilisateur serverGuy = new Utilisateur("servguy", new Id(1), "10.1.5.16");
		Utilisateur client1 = new Utilisateur("client", new Id(2), "10.1.5.99");
		serverGuy.getListeUtilisateurs().add(client1);

		ClavardageManager com = new ClavardageManager(serverGuy);
	}

}
