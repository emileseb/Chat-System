import java.io.IOException;
import java.net.*;

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
					//info = 0 : changerActif(true), info = 1 : changerActif(false), info = 2 : changerPseudo(pseudo)
					//info = 3 : listeUtilisateurs.add(utilisateur)
					String[] messageFormate = messageRecu.split("/");
					switch (messageFormate[0]) {
						case "0":
							utilisateur.changeActif(true, new Id(messageFormate[1]));
							//envoie ses infos a l'utilisateur qui a envoye le message
							InetAddress clientAddress = inPacket.getAddress();
							int clientPort = inPacket.getPort();
							String reponse = new String("3/" + utilisateur.getPseudo() + "/" + utilisateur.getId() + "/" + utilisateur.getAdresseIp());
							DatagramPacket outPacket = new DatagramPacket(reponse.getBytes(), reponse.length(),clientAddress, clientPort);
							udpSocket.send(outPacket);
							break;
						case "1":
							utilisateur.changeActif(false, new Id(messageFormate[1]));
							break;
						case "2":
							utilisateur.changerPseudo(messageFormate[1], new Id(messageFormate[2]));
							break;
						case "3":
							utilisateur.getListeUtilisateurs().add(new Utilisateur(messageFormate[1], new Id(messageFormate[2]), messageFormate[3]));
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
