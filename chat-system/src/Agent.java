import java.util.ArrayList;
import java.util.Iterator;

public class Agent {
	
	//utilisateur
	public ArrayList<Utilisateur> listeUtilisateurs;
	
	public boolean clientExiste(Id idAgent){
		boolean result = false;
		Iterator<Utilisateur> iter = listeUtilisateurs.iterator();
		while(iter.hasNext() && !result) {
			if (iter.next().getId().equals(idAgent)) {
				result = true;
			}
		}
		return result;
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
		boolean result = false;
		Iterator<Utilisateur> iter = listeUtilisateurs.iterator();
		while(iter.hasNext() && !result) {
			Utilisateur user = iter.next();
			if (user.getId().equals(idAgent)) {
				user.changerPseudo(pseudo);
				result = true;
			}
		}
	}

	public void changeActif(boolean actif, Id idAgent) {
		boolean result = false;
		Iterator<Utilisateur> iter = listeUtilisateurs.iterator();
		while(iter.hasNext() && !result) {
			Utilisateur user = iter.next();
			if (user.getId().equals(idAgent)) {
				user.setActif(actif);
				result = true;
			}
		}
	}
	
	//historique
	
	public Agent() {
		Utilisateur user1 = new Utilisateur("emile", new Id(1), true);
		Utilisateur user2 = new Utilisateur("thomas", new Id(2), true);
		Utilisateur user3 = new Utilisateur("morvan", new Id(), true);
		this.listeUtilisateurs = new ArrayList<Utilisateur>();
		this.listeUtilisateurs.add(user1);
		this.listeUtilisateurs.add(user2);
		this.listeUtilisateurs.add(user3);
	}

}
