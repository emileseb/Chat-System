package interfaceGraphique;
import utilisateur.*;

import java.util.ArrayList;

import communicationInformation.*;
import conversation.Message;

public class Controleur {	
	
	FenetreAccueil fenetreAccueil;
	FenetrePrincipale fenetrePrincipale;
	
	private Utilisateur modele;
	private Notifieur notifieur;
	private Rafraichisseur rafraichisseur;
	
	public Controleur(Utilisateur modele) {
		this.modele = modele;
		this.notifieur = new Notifieur(modele);
		this.rafraichisseur = new Rafraichisseur(modele, this);
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
				notifieur.notifierChangementPseudo();
				fenetrePrincipale.pseudoChange();
			}
		}
	}
	
	public void actualisationUtilisateurs() {
		if (fenetrePrincipale != null) {
			fenetrePrincipale.afficherClavardeurs();
		}
	}
	
	public String demandePseudo() {
		return (modele.getPseudo());
	}
	
	public Utilisateur demandeUtilisateur() {
		return (modele);
	}
	
	public ArrayList<Utilisateur> demandeUtilisateursActifs(){
		return modele.getListeUtilisateursActifs();
	}

	public ArrayList<Utilisateur> demandeUtilisateursHistorique(){
		return modele.getUtilisateursHistorique();
	}
	
	public ArrayList<Message> demandeHistoriqueDe(Utilisateur user) {
		return modele.getHistoriqueDe(user.getId());
	}
	
	public void receptionMessage(Message msg) {
		if (fenetrePrincipale.utilisateurSelectionne != null) {
			if (fenetrePrincipale.utilisateurSelectionne.equals(msg.getAuteur())) {
				fenetrePrincipale.afficherMessage(msg);
			}
		}
	}
	
	public void receptionConnexion() {
		fenetrePrincipale.afficherClavardeurs();
	}
	
	public void fermetureApp() {
		notifieur.notifierAgentInActif();
		rafraichisseur.close();
	}
}
