package communicationInformation;
import java.io.IOException;
import java.net.*;

import interfaceGraphique.Controleur;
import utilisateur.Id;
import utilisateur.Utilisateur;

// Cette classe permet de rafraichir la page. Elle reçoit des messages UDP d'un utilisateur pour actualiser ses infos 
public class Rafraichisseur extends Thread {

	private Utilisateur utilisateur;
	
	private Controleur controleur;
	
	boolean actif;
	
	public Rafraichisseur(Utilisateur utilisateur, Controleur controleur){
		this.utilisateur = utilisateur;
		this.actif = true;
		this.controleur = controleur;
		start();
	}
	
	public void run() {
		try {
			DatagramSocket udpSocket = new DatagramSocket(2222);
			byte[] buffer = new byte[256];
			while(this.actif) {
				DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
				//attend de recevoir un message (changement Actif ou changement Pseudo)
				try {
					udpSocket.receive(inPacket);
					String messageRecu = new String(inPacket.getData(), 0, inPacket.getLength());
					//messages recus sous la forme info/Id ou info/Id/pseudo ou info/Id/pseudo/adresseIp
					//info = 0 : demandeInfo, info = 1 : envoiInfo, info = 2 : notifChangementInActif
					//info = 3 : notifChangementPseudo
					String[] messageFormate = messageRecu.split("/");
					
					//message broadcast donc si on reçoit un message venant de nous même on ne le traite pas
					if (!utilisateur.getId().equals(new Id(messageFormate[1]))) {
						traitementMessage(messageFormate, inPacket, udpSocket);
					}
				}
				
				catch(IOException e) {
					System.out.println("IO Exception reception message udp");
				}
			}
			//fin du rafraichisseur = fin de l'appli
			udpSocket.close();
			System.out.println("Fermeture rafraichisseur");
		}
		catch(SocketException e2) {
			System.out.println("Rafraichisseur Udp socket failure");
		}
	}
	
	public void close() {
		this.actif = false;
	}
	
	public void traitementMessage(String[] messageFormate, DatagramPacket inPacket, DatagramSocket udpSocket) throws IOException{
		switch (messageFormate[0]) {
			case "0":
				System.out.println("Demande d'infos recue");
				System.out.println("Envoi des infos");
				//envoie ses infos a l'utilisateur qui a envoye le message
				InetAddress clientAddress = inPacket.getAddress();
				String reponse = new String("1/" + utilisateur.getId() + "/" + utilisateur.getPseudo() + "/" + utilisateur.getAdresseIp());
				DatagramPacket outPacket = new DatagramPacket(reponse.getBytes(), reponse.length(),clientAddress, 2222);
				udpSocket.send(outPacket);
				break;
			case "1":
				// reception des infos
				System.out.println("Reception des infos");
				System.out.println("Ajout utilisateur dans liste utilisateurs");
				System.out.println(new Utilisateur(messageFormate[2], new Id(messageFormate[1]), messageFormate[3], true));
				if (utilisateur.clientExiste(new Id(messageFormate[1]))) {
					utilisateur.changeActif(true, new Id(messageFormate[1]));
					utilisateur.changerPseudo(messageFormate[2], new Id(messageFormate[1]));
					utilisateur.setAdresseIp(messageFormate[3]);
				}else {
					utilisateur.getListeUtilisateurs().add(new Utilisateur(messageFormate[2], new Id(messageFormate[1]), messageFormate[3], true));
				}
				controleur.actualisationUtilisateurs();
				break;
			case "2":
				System.out.println("Reception utilisateur inactif");
				utilisateur.changeActif(false, new Id(messageFormate[1]));
				controleur.receptionUtilisateurInactif();
				break;
			case "3":
				System.out.println("Reception changement pseudo");
				utilisateur.changerPseudo(messageFormate[2], new Id(messageFormate[1]));
				controleur.receptionChangementPseudo(new Id(messageFormate[1]));
				break;
			default:
				System.out.println("Mauvais format de message");
				break;
		}
	}
	
}
