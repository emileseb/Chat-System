package interfaceGraphique;
import utilisateur.*;

import java.util.ArrayList;

import communicationInformation.*;
import conversation.Message;

public class Controleur {	
	
	FenetreAccueil fenetreAccueil;
	FenetrePrincipale fenetrePrincipale;
	
	Utilisateur modele;
	Notifieur notifieur;
	
	public Controleur(Utilisateur modele) {
		this.modele = modele;
		this.notifieur = new Notifieur(modele);
		//demande les informations des utilisateurs pour remplir la liste utilisateurs dans rafraichisseur
		notifieur.demandeInformation();
		this.fenetreAccueil = new FenetreAccueil(this);
	}
	
	public void verifierPseudoAccueil(String pseudo) {
		if (pseudo.length() != 0) {
			if (modele.pseudoPris(pseudo)) {
				fenetreAccueil.erreurPseudo();
			}else {
				modele.changerPseudo(pseudo);
				//envoi des infos a tous les utilisateurs
				notifieur.envoiInformation();
				fenetreAccueil.toHomePage();
				fenetrePrincipale = new FenetrePrincipale(this);
			}
		}
	}

	public void verifierPseudo(String pseudo) {
		if (pseudo.length() != 0) {
			if (modele.pseudoPris(pseudo)) {
				fenetrePrincipale.erreurPseudo();
			}else {
				modele.changerPseudo(pseudo);
				//envoi des infos a tous les utilisateurs
				notifieur.envoiInformation();
				fenetrePrincipale.pseudoChange();
			}
		}
	}
	
	public String demandePseudo() {
		return (modele.getPseudo());
	}
	
	public ArrayList<Utilisateur> demandeUtilisateursActifs(){
		return modele.getListeUtilisateurs(); 
	}

	public ArrayList<Utilisateur> demandeUtilisateursHistorique(){
		return modele.getUtilisateursHistorique(); 
	}
	
	public ArrayList<Message> demandeHistoriqueDe(Utilisateur user) {
		return modele.getHistoriqueDe(user.getId());
	}
}
