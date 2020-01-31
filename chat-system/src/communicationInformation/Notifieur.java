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

	public void envoiBroadcast(String message){
		try {
			DatagramSocket udpSocket = new DatagramSocket();
			try {
				InetAddress adresseBroadcast = InetAddress.getByName(this.utilisateur.getAdresseBroadcast());
				DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), adresseBroadcast, 2222);
				udpSocket.send(outPacket);
				udpSocket.close();
			}
			catch(IOException e1) {
				System.out.println("Probleme envoi message broadcast");
			}
		}
		catch(SocketException e2) {
			System.out.println("Udp socket failure");
		}
	}

	public void demandeInformation(){
		//message envoye sous la forme info, demande les infos des utilisateurs pour remplir liste utilisateur
		String message = new String("0/" + this.utilisateur.getId());
		envoiBroadcast(message);
	}
	
	public void envoiInformation() {
		//message envoye sous la forme info/Id/pseudo/adresseIp
		String message = new String("1/" + this.utilisateur.getId() + "/" + this.utilisateur.getPseudo() + "/" + this.utilisateur.getAdresseIp() + "/" + utilisateur.getActif());
		envoiBroadcast(message);
	}
	
	public void notifierAgentInActif(){
		//message envoye sous la forme info/Id
		String message = new String("2/" + this.utilisateur.getId());
		envoiBroadcast(message);
	}

	public void notifierChangementPseudo(){
		//message envoye sous la forme info/Id/pseudo
		String message = new String("3/" + this.utilisateur.getId() + "/" + this.utilisateur.getPseudo());
		envoiBroadcast(message);
	}

}
