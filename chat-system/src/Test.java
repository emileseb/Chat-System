import clavardage.*;
import utilisateur.*;

public class Test {
    public static void main(String[] args) {
		Utilisateur serverGuy = new Utilisateur("servguy", new Id(1111111111), "127.0.0.1");
		Utilisateur client1 = new Utilisateur("Thomas", new Id(222222222), "127.0.0.1");
		Utilisateur client2 = new Utilisateur("Théo", new Id(333333333), "127.0.0.1");
		client1.getListeUtilisateurs().add(serverGuy);
		client1.getListeUtilisateurs().add(client2);

		ClavardageManager.demandeClavardage(client1, new Id(1111111111));
	}
}
