package interfaceGraphique;
import utilisateur.*;

import java.util.ArrayList;

import baseDeDonnees.LocalDB;
import clavardage.ClavardageManager;
import clavardage.Session;
import communicationInformation.*;
import conversation.Message;

public class Controleur {	
	
	FenetreAccueil fenetreAccueil;
	FenetrePrincipale fenetrePrincipale;
	
	private Utilisateur modele;
	private Notifieur notifieur;
	private Rafraichisseur rafraichisseur;
	private LocalDB database;
	
	
	public Controleur(Utilisateur modele, LocalDB db) {
		this.modele = modele;
		this.notifieur = new Notifieur(modele);
		this.rafraichisseur = new Rafraichisseur(modele, this);
		//demande les informations des utilisateurs pour remplir la liste utilisateurs dans rafraichisseur
		notifieur.demandeInformation();
		this.database = db;
		this.fenetreAccueil = new FenetreAccueil(this);
	}
	
	public void verifierPseudoAccueil(String pseudo) {
		if (pseudo.length() != 0) {
			if (modele.pseudoPris(pseudo) || pseudo.length() > 30) {
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
			if (modele.pseudoPris(pseudo) || pseudo.length() > 30) {
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
			fenetrePrincipale.afficherHistoriques();
		}
	}
	
	public void actualisationHistorique(Utilisateur user) {
		if (fenetrePrincipale != null) {
			if (fenetrePrincipale.utilisateurSelectionne != null) {
				if (fenetrePrincipale.utilisateurSelectionne.equals(user)) {
					fenetrePrincipale.afficherHistorique(user);
				}
			}
		}		
	}
	
	public void receptionUtilisateurInactif() {
		if (fenetrePrincipale != null) {
			if (fenetrePrincipale.utilisateurSelectionne != null) {
				fenetrePrincipale.clearRightPanel();
			}
			fenetrePrincipale.afficherClavardeurs();
		}		
	}
	
	public void receptionDebutClavardage(Utilisateur user) {
		actualisationUtilisateurs();
		if (fenetrePrincipale != null) {
			if (fenetrePrincipale.utilisateurSelectionne != null) {
				if (fenetrePrincipale.utilisateurSelectionne.equals(user)) {
					fenetrePrincipale.affichageDebutClavardage();
				}
			}
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
				if (msg.getContenu().equals(ClavardageManager.messageFin)) {
					fenetrePrincipale.affichageFinClavardage();
				}else {
					fenetrePrincipale.afficherMessage(msg);					
				}
			}
		}
	}
	
	public void fermetureApp() {
		System.out.println("Fermeture application");
		rafraichisseur.close();
		ArrayList<Session> sessions = new ArrayList<Session>(ClavardageManager.getListeSessions());
		for (Session sess : sessions) {
			ClavardageManager.envoyerMessage(sess.getLui(), ClavardageManager.messageFin);
		}
		ClavardageManager.close();
		database.close();
		notifieur.notifierAgentInActif();
	}
}
