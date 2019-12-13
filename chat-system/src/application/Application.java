package application;
import javax.swing.plaf.multi.MultiToolTipUI;
import clavardage.*;

import clavardage.ClavardageManager;
import utilisateur.Id;
import utilisateur.Utilisateur;

public class Application {

	public static void main(String[] args) {
	    //Creation of me
        Utilisateur serverGuy = new Utilisateur("servguy", new Id(1111111111), "127.0.0.1");
        Utilisateur client1 = new Utilisateur("Thomas", new Id(222222222), "127.0.0.1");
        Utilisateur client2 = new Utilisateur("Théo", new Id(333333333), "127.0.0.1");

        serverGuy.getListeUtilisateurs().add(client1);
        serverGuy.getListeUtilisateurs().add(client2);

		ClavardageManager com = new ClavardageManager(serverGuy);
	}

}
