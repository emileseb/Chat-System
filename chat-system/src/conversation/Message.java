package conversation;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import utilisateur.Utilisateur;

public class Message {

	private Utilisateur auteur;
	private Utilisateur destinataire;
	private String contenu;
	private String date;
	
	public Message(Utilisateur auteur, Utilisateur destinataire, String contenu) {
		this.auteur = auteur;
		this.destinataire = destinataire;
		this.contenu = contenu;
		LocalDateTime horodatage = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.date = horodatage.format(formatter);
	}

	public Message(Utilisateur auteur, Utilisateur destinataire, String contenu, String date) {
		this.auteur = auteur;
		this.destinataire = destinataire;
		this.contenu = contenu;
		this.date = date;
	}

	
	public Utilisateur getAuteur() {
		return this.auteur;
	}

	public Utilisateur getDestinataire() {
		return this.destinataire;
	}
	
	public String getDate() {
		return this.date;
	}

	public String getContenu() {return this.contenu;}

	@Override
	public String toString() {
		return (this.auteur.getPseudo() + " - " +	getDate() + "\n" + this.contenu);
	}
	
}
