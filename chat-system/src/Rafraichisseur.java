import java.io.IOException;
import java.net.*;

// Cette classe permet de rafraichir la page. Elle reçoit des messages UDP d'un utilisateur pour actualiser ses infos 
public class Rafraichisseur extends Thread {

	Utilisateur utilisateur;
	
	boolean actif;
	
	public Rafraichisseur(Utilisateur utilisateur){
		this.utilisateur = utilisateur;
		this.actif = true;
	}
	
	public void run() {
		try {
			DatagramSocket udpSocket= new DatagramSocket(1234);
			byte[] buffer = new byte[256];
			while(this.actif) {
				
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
				//attend de recevoir un message (changement Actif ou changement Pseudo)
				try {
					udpSocket.receive(inPacket);
					String messageRecu = new String(inPacket.getData(), 0, inPacket.getLength());
					
					//messages recus sous la forme info/Id ou info/pseudo/Id ou info/pseudo/Id/adresseIp
					//info = 0 : demandeInfo, info = 1 : envoiInfo, info = 2 : notifChangementInActif
					//info = 3 : notifChangementPseudo
					String[] messageFormate = messageRecu.split("/");
					switch (messageFormate[0]) {
						case "0":
							System.out.println("Demande d'infos recue");
							//envoie ses infos a l'utilisateur qui a envoye le message
							InetAddress clientAddress = inPacket.getAddress();
							String reponse = new String("1/" + utilisateur.getId() + "/" + utilisateur.getPseudo() + "/" + utilisateur.getAdresseIp());
							DatagramPacket outPacket = new DatagramPacket(reponse.getBytes(), reponse.length(),clientAddress, 1234);
							udpSocket.send(outPacket);
							break;
						case "1":
							// reception des infos
							System.out.println("Reception des infos");
							utilisateur.getListeUtilisateurs().add(new Utilisateur(messageFormate[2], new Id(messageFormate[1]), messageFormate[3]));
							break;
						case "2":
							System.out.println("Reception utilisateur inactif");
							utilisateur.changeActif(false, new Id(messageFormate[1]));
							break;
						case "3":
							System.out.println("Reception changement pseudo");
							utilisateur.changerPseudo(messageFormate[2], new Id(messageFormate[1]));
							break;
						default:
							System.out.println("Mauvais format de message");
							break;
					}
				}
				
				catch(IOException e) {
					System.out.println("IO Exception reception message udp");
				}
			}
			//fin du rafraichisseur = fin de l'appli
			udpSocket.close();
		}
		catch(SocketException e2) {
			System.out.println("Udp socket failure");
		}
	}
	
	public void close() {
		this.actif = false;
	}
	
}
