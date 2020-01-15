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
		Utilisateur serverGuy = new Utilisateur("servguy", new Id(1111111111), "127.0.0.1", true);
		Utilisateur client1 = new Utilisateur("Thomas", new Id(222222222), "127.0.0.1", true);
		Utilisateur client2 = new Utilisateur("Théo", new Id(333333333), "127.0.0.1", true);
		serverGuy.getListeUtilisateurs().add(client1);
		serverGuy.getListeUtilisateurs().add(client2);
		
        ArrayList<Message> msg = new ArrayList<>();
        msg.add(new Message(serverGuy,client1,"bonjour"));
        msg.add(new Message(client1,serverGuy,"salut à toi"));

        Historique historique = new Historique(client1.getId(),msg);
        
		LocalDB database = new LocalDB(serverGuy);
		database.sauvegarderConversation(historique);
		
		Historique hist = database.getConversation(client1);
		System.out.println(hist.toString());
		
		Controleur control = new Controleur(serverGuy, database);
		
		ClavardageManager clav = new ClavardageManager(serverGuy, control);
		
	}

}
