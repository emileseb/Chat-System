package communicationInformation;

import java.io.IOException;
import java.net.*;
import java.net.SocketException;

import utilisateur.Utilisateur;

// Cette classe permet de notifier aux autres agents de l'activité ou du changement de pseudo d'un utilisateur
public class Notifieur{
	Utilisateur utilisateur;	
	
	public Notifieur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void envoiBroadcast(String message) throws IOException {
		try {
			DatagramSocket udpSocket = new DatagramSocket();
			InetAddress adresseBroadcast = InetAddress.getByName(this.utilisateur.getAdresseBroadcast());
			DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), adresseBroadcast, 1234);
			udpSocket.send(outPacket);
			udpSocket.close();
		}
		catch(SocketException e2) {
			System.out.println("Udp socket failure");
		}
	}

	public void demandeInformation() throws IOException {
		//message envoye sous la forme info, demande les infos des utilisateurs pour remplir liste utilisateur
		String message = new String("0/" + this.utilisateur.getId());
		envoiBroadcast(message);
	}
	
	public void envoiInformation() throws IOException {
		//message envoye sous la forme info/Id/pseudo/adresseIp
		String message = new String("1/" + this.utilisateur.getId() + "/" + this.utilisateur.getPseudo() + "/" + this.utilisateur.getAdresseIp());
		envoiBroadcast(message);
	}
	
	public void notifierAgentInActif() throws IOException {
		//message envoye sous la forme info/Id
		String message = new String("2/" + this.utilisateur.getId());
		envoiBroadcast(message);
	}

	public void notifierChangementPseudo() throws IOException {
		//message envoye sous la forme info/Id/pseudo
		String message = new String("3/" + this.utilisateur.getPseudo() + "/" + this.utilisateur.getId());
		envoiBroadcast(message);
	}

}
