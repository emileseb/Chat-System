package utilisateur;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import baseDeDonnees.LocalDB;
import conversation.Historique;
import conversation.Message;

public class Utilisateur {
	private String pseudo;	
	private Id idUtilisateur;
	private boolean actif;
	private String adresseIp;
	private String adresseBroadcast;
    private ArrayList<Utilisateur> listeUtilisateurs;
    private ArrayList<Historique> listeHistoriques;
    private LocalDB database;
	
    //creation de l'utilisateur sur le poste
	public Utilisateur() {
		this.pseudo = "";
		this.idUtilisateur = new Id();
		this.actif = true;
		this.adresseIp = "";	
		this.adresseBroadcast = "";	
		try {
			//recupere la premiere interface reseau
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()) {
				NetworkInterface interfaceReseau = e.nextElement();
				if (interfaceReseau.getDisplayName().contains("eth4")){
					this.adresseBroadcast = interfaceReseau.getInterfaceAddresses().get(1).getBroadcast().getHostAddress();
					this.adresseIp = interfaceReseau.getInterfaceAddresses().get(1).getAddress().getHostAddress();					
				}
			}
		}
		catch(SocketException e){
			System.out.println("Pas d'adresse ip valide");
		}
        this.database = new LocalDB(this);
        //recupere la liste des utilisateurs avec qui on a un historique
		this.listeUtilisateurs = this.database.getUsers();
		this.listeHistoriques = new ArrayList<Historique>();
		// ajout des historiques des utilisateurs avec qui on a parle
		for (Utilisateur user : this.listeUtilisateurs) {
			this.listeHistoriques.add(this.database.getHistorique(user));
		}
	}

	//creation d'utilisateurs pour la liste utilisateurs
	public Utilisateur(String pseudo, Id idUtilisateur, String adresseIp, boolean actif) {
		this.pseudo = pseudo;
		this.idUtilisateur = idUtilisateur;
		this.actif = actif;
		this.adresseIp = adresseIp;
		this.adresseBroadcast = "";
		this.listeUtilisateurs = new ArrayList<Utilisateur>();
		this.listeHistoriques = new ArrayList<Historique>();
	}

	//pour test
	public Utilisateur(String pseudo, Id idUtilisateur, String adresseIp, String adresseBroadcast) {
		this.pseudo = pseudo;
		this.idUtilisateur = idUtilisateur;
		this.actif = true;
		this.adresseIp = adresseIp;
		this.adresseBroadcast = adresseBroadcast;
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

	public String getAdresseBroadcast() {
		return this.adresseBroadcast;
	}
	
	public boolean getActif() {
		return this.actif;
	}
	
	public LocalDB getDatabase() {
		return this.database;
	}
	
	public void setActif(boolean actif) {
		this.actif = actif;
	}
	
	public void setAdresseIp(String ip) {
		this.adresseIp = ip;
	}
	
	public void changerPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	//liste utilisateur

    public ArrayList<Utilisateur> getListeUtilisateurs() {
        return listeUtilisateurs;
    }
    
    public ArrayList<Utilisateur> getListeUtilisateursActifs(){
    	ArrayList<Utilisateur> utilisateursActifs = new ArrayList<Utilisateur>();
        for(Utilisateur user : listeUtilisateurs) {
            if (user.getActif()) {
            	utilisateursActifs.add(user);
            }
        }
        return utilisateursActifs;
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
            if (iter.next().getPseudo().equals(pseudo)) {
                result = true;
            }
        }
        return result;
    }

    public void changerPseudo(String pseudo, Id idAgent) {
	    trouveClient(idAgent).changerPseudo(pseudo);
    }

    public void changeActif(boolean actif, Id idAgent) {
        trouveClient(idAgent).setActif(actif);
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
        
        // ajout de la conversation dans la base de donnees
        this.database.sauvegarderHistorique(new Historique(idPartenaire, conversation));
    }
    
    public ArrayList<Utilisateur> getUtilisateursHistorique(){
    	ArrayList<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
    	for (Historique hist : listeHistoriques) {
    		utilisateurs.add(trouveClient(hist.getIdPartenaire()));
    	}
    	return utilisateurs;
    }
    
	public String toString() {
		return ("Pseudo : " + this.pseudo + ", Id : " + this.idUtilisateur.getValue() + ", Actif : " + this.actif + ", Adresse Ip : " + this.adresseIp);
	}

    public boolean equals(Utilisateur user) {
        return this.getId().equals(user.getId());
    }
}
