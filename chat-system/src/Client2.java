import clavardage.*;
import utilisateur.*;

public class Client2 {
    public static void main(String[] args) {
        Utilisateur serverGuy = new Utilisateur("servguy", new Id(1111111111), "127.0.0.1");
        Utilisateur client1 = new Utilisateur("Thomas", new Id(222222222), "127.0.0.1");
        Utilisateur client2 = new Utilisateur("Théo", new Id(333333333), "127.0.0.1");
        serverGuy.getListeUtilisateurs().add(client1);
        serverGuy.getListeUtilisateurs().add(client2);

        ClavardageManager com = new ClavardageManager(serverGuy);
        /*Utilisateur serverGuy = new Utilisateur("servguy", new Id(1111111111), "127.0.0.1");
        Utilisateur client1 = new Utilisateur("Thomas", new Id(222222222), "127.0.0.1");
        Utilisateur client2 = new Utilisateur("Théo", new Id(333333333), "127.0.0.1");

        client2.getListeUtilisateurs().add(serverGuy);
        client2.getListeUtilisateurs().add(client1);

        ClavardageManager.demandeClavardage(client2, new Id(1111111111));*/
    }
}
