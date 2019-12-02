import java.net.*;
import java.util.Enumeration;

public class Utilisateur {
	private String pseudo;	
	private Id idUtilisateur;
	private boolean actif;
	private String adresseIp;
	
	public Utilisateur(String pseudo, Id idUtilisateur, boolean actif) {
		this.pseudo = pseudo;
		this.idUtilisateur = idUtilisateur;
		this.actif = actif;
		this.adresseIp = "";
		
		try {
			//recupere la premiere interface reseau
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			Enumeration<InetAddress> i = e.nextElement().getInetAddresses();
			//recupere l'adresse ip locale
			while (i.hasMoreElements()){
				InetAddress a = i.nextElement();
				if (a.isSiteLocalAddress()){
					this.adresseIp = a.getHostAddress();
				}
			}
		}
		catch(SocketException e){
			System.out.println("Pas d'adresse ip valide");
		}
	}
	
	public String getPseudo() {
		return this.pseudo;
	}
	
	public Id getId() {
		return this.idUtilisateur;
	}
	
	public String getAdresseIp() {
		return this.adresseIp;
	}
	
	public boolean getActif() {
		return this.actif;
	}
	
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	
	public void changePseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
