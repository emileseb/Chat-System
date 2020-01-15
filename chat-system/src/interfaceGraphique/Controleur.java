package interfaceGraphique;
import utilisateur.*;

import java.util.ArrayList;

import clavardage.ClavardageManager;
import clavardage.Session;
import communicationInformation.*;
import conversation.Message;

public class Controleur {	
	
	FenetreAccueil fenetreAccueil;
	FenetrePrincipale fenetrePrincipale;
	
	private Utilisateur utilisateur;
	private Notifieur notifieur;
	private Rafraichisseur rafraichisseur;
	
	
	public Controleur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
		this.notifieur = new Notifieur(utilisateur);
		this.rafraichisseur = new Rafraichisseur(utilisateur, this);
		//demande les informations des utilisateurs pour remplir la liste utilisateurs dans rafraichisseur
		notifieur.demandeInformation();
		this.fenetreAccueil = new FenetreAccueil(this);
	}
	
	public String demandePseudo() {
		return (utilisateur.getPseudo());
	}
	
	public Utilisateur demandeUtilisateur() {
		return (utilisateur);
	}
	
	public ArrayList<Utilisateur> demandeUtilisateursActifs(){
		return utilisateur.getListeUtilisateursActifs();
	}

	public ArrayList<Utilisateur> demandeUtilisateursHistorique(){
		return utilisateur.getUtilisateursHistorique();
	}
	
	public ArrayList<Message> demandeHistoriqueDe(Utilisateur user) {
		return utilisateur.getHistoriqueDe(user.getId());
	}
	
	public void verifierPseudoAccueil(String pseudo) {
		if (pseudo.length() != 0) {
			if (utilisateur.pseudoPris(pseudo) || pseudo.length() > 30) {
				fenetreAccueil.erreurPseudo();
			}else {
				utilisateur.changerPseudo(pseudo);
				//envoi des infos a tous les utilisateurs
				notifieur.envoiInformation();
				fenetreAccueil.toHomePage();
				fenetrePrincipale = new FenetrePrincipale(this);
			}
		}
	}

	public void verifierPseudo(String pseudo) {
		if (pseudo.length() != 0) {
			if (utilisateur.pseudoPris(pseudo) || pseudo.length() > 30) {
				fenetrePrincipale.erreurPseudo();
			}else {
				utilisateur.changerPseudo(pseudo);
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
	
	public void receptionChangementPseudo(Id id) {
		Utilisateur user = utilisateur.trouveClient(id);
		if (fenetrePrincipale != null) {
			if (fenetrePrincipale.utilisateurSelectionne != null) {
				if (fenetrePrincipale.utilisateurSelectionne.equals(user)) {
					if (ClavardageManager.trouveSession(id) != null) {
						fenetrePrincipale.afficherHistorique(user);
						fenetrePrincipale.afficherConversation(user);
					}
				}
			}
			actualisationUtilisateurs();
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
		utilisateur.getDatabase().sauvegarderUsers();
		utilisateur.getDatabase().close();
		notifieur.notifierAgentInActif();
	}
}
