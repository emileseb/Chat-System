import java.io.IOException;
import java.net.*;
import java.net.SocketException;

// Cette classe permet de notifier aux autres agents de l'activité ou du changement de pseudo d'un utilisateur
public class Notifieur{
	
	public static void notifierAgentInActif(Utilisateur utilisateur) throws IOException {
		try {
			DatagramSocket udpSocket = new DatagramSocket();
			//message envoye sous la forme info/Id
			//info = 0 : changerActif(true)
			String message = new String("1/" + utilisateur.getId());
			InetAddress adresseBroadcast = InetAddress.getByName(utilisateur.getAdresseBroadcast());
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), adresseBroadcast, 1234);
			udpSocket.send(outPacket);
			udpSocket.close();
		}
		catch(SocketException e2) {
			System.out.println("Udp socket failure");
		}
	}
	

}
