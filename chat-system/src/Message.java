import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {

	private Utilisateur auteur;
	private Utilisateur destinataire;
	private String contenu;
	private LocalDateTime date;
	
	public Message(Utilisateur auteur, Utilisateur destinataire, String contenu) {
		this.auteur = auteur;
		this.destinataire = destinataire;
		this.contenu = contenu;
		this.date = LocalDateTime.now();
	}
	
	public Utilisateur getAuteur() {
		return this.auteur;
	}

	public Utilisateur getDestinataire() {
		return this.destinataire;
	}
	
	public String getDate() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");;
		return date.format(formatter);
	}

	public String getContenu() {return this.contenu;}

	@Override
	public String toString() {
		return ("Auteur - " + this.auteur.getPseudo() + ", Destinataire - " + this.destinataire.getPseudo() + ", Date - " +
	getDate() + " : " + this.contenu);
	}
	
}
