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
		
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		    while (e.hasMoreElements()){
		      Enumeration<InetAddress> i = e.nextElement().getInetAddresses();
		      while (i.hasMoreElements()){
		        InetAddress a = i.nextElement();
		        if (a.isSiteLocalAddress()){
		        	this.adresseIp = a.getHostAddress();
		        }
		      }
		    }
		}
		catch(SocketException e){
			
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
