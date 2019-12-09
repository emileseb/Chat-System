import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

	private Utilisateur auteur;
	private Utilisateur destinataire;
	private String contenu;
	private Date date;
	
	public Message(Utilisateur auteur, Utilisateur destinataire, String contenu) {
		this.auteur = auteur;
		this.destinataire = destinataire;
		this.contenu = contenu;
		this.date = new Date();
	}
	
	public Utilisateur getAuteur() {
		return this.auteur;
	}

	public Utilisateur getDestinataire() {
		return this.destinataire;
	}
	
	public String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy");
		return format.format(this.date);
	}

	public String getContenu() {return this.contenu;}

	@Override
	public String toString() {
		return ("Auteur - " + this.auteur.getPseudo() + ", Destinataire - " + this.destinataire.getPseudo() + ", Date - " +
	getDate() + " : " + this.contenu);
	}
	
}
