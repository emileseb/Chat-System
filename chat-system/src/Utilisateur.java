import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

public class Utilisateur {
	private String pseudo;	
	private Id idUtilisateur;
	private boolean actif;
	private String adresseIp;
    private ArrayList<Utilisateur> listeUtilisateurs;
    private ArrayList<Historique> listeHistoriques;
	
    //creation de l'utilisateur sur le poste
	public Utilisateur() {
		this.pseudo = "";
		this.idUtilisateur = new Id();
		this.actif = true;
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
		
		this.listeUtilisateurs = new ArrayList<Utilisateur>();
		this.listeHistoriques = new ArrayList<Historique>();
	}

	//creation d'utilisateurs pour la liste utilisateurs
	public Utilisateur(String pseudo, Id idUtilisateur, String adresseIp) {
		this.pseudo = pseudo;
		this.idUtilisateur = idUtilisateur;
		this.actif = true;
		this.adresseIp = adresseIp;		
		this.listeUtilisateurs = new ArrayList<Utilisateur>();
		this.listeHistoriques = new ArrayList<Historique>();
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
	
	public void changerPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	//liste utilisateur

    public ArrayList<Utilisateur> getListeUtilisateurs() {
        return listeUtilisateurs;
    }

    public Utilisateur trouveClient(Id idAgent){
        Utilisateur user;
        Iterator<Utilisateur> iter = listeUtilisateurs.iterator();
        while(iter.hasNext()) {
            user = iter.next();
            if (user.getId().equals(idAgent)) {
                return user;
            }
        }
        return null;
    }

    public boolean clientExiste(Id idAgent){
	    return (trouveClient(idAgent) != null);
    }

    public boolean pseudoPris(String pseudo) {
        boolean result = false;
        Iterator<Utilisateur> iter = listeUtilisateurs.iterator();
        while(iter.hasNext() && !result) {
            if (iter.next().getPseudo() == pseudo) {
                result = true;
            }
        }
        return result;
    }

    public void changerPseudo(String pseudo, Id idAgent) {
	    trouveClient(idAgent).changerPseudo(pseudo);
    }

    public void changeActif(boolean actif, Id idAgent) {
        trouveClient(idAgent).changeActif(actif,idAgent);
    }

    //liste historique

    public ArrayList<Historique> getMesHistoriques() {
        return listeHistoriques;
    }

    //méthodes
    public boolean historiqueExiste(Id idPartenaire){
        boolean result = false;
        Iterator<Historique> iter = listeHistoriques.iterator();
        while (iter.hasNext() && !result) {
            if (iter.next().getIdPartenaire().equals(idPartenaire))
                result = true;
        }
        return result;
    }

    public ArrayList<Message> getHistoriqueDe(Id idPartenaire){
        ArrayList<Message> listeMessage = new ArrayList<Message>();
        Iterator<Historique> iter = listeHistoriques.iterator();
        while (iter.hasNext()) {
            Historique hist = iter.next();
            if (hist.getIdPartenaire().equals(idPartenaire)) {
                return hist.getHistorique();
            }
        }
        return listeMessage;
    }

    public void mettreAJourHistorique(ArrayList<Message> conversation, Id idPartenaire) {
        boolean pastrouve = true;
        Iterator<Historique> iter = listeHistoriques.iterator();
        while (iter.hasNext() && pastrouve) {
            Historique hist = iter.next();
            if (hist.getIdPartenaire().equals(idPartenaire)) {
                hist.ajouterMessage(conversation);
                pastrouve = false;
            }
        }
        if (pastrouve)
        	listeHistoriques.add(new Historique(idPartenaire, conversation));
    }

    public void connectWith(Id destinataire){
		//Faut-il créer une classe spéciale pour établir des connections ?
		//Faut ils une grande liste des sessions en cours ? Des sessions dont je suis l'initiateur ? Récepteur ?

	}
    
	public String toString() {
		return ("Pseudo : " + this.pseudo + ", Id : " + this.idUtilisateur.getValue() + ", Actif : " + this.actif + ", Adresse Ip : " + this.adresseIp);
	}

    public boolean equals(Utilisateur user) {
        return this.getId().equals(user.getId());
    }
}
