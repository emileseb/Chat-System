import clavardage.*;
import utilisateur.*;

public class Test {
    public static void main(String[] args) {
		Utilisateur serverGuy = new Utilisateur("servguy", new Id(1), "10.1.5.16");
		Utilisateur client1 = new Utilisateur("client", new Id(2), "10.1.5.99");
		client1.getListeUtilisateurs().add(serverGuy);

		ClavardageManager.demandeClavardage(client1, serverGuy.getId());
	}
}
