package interfaceGraphique;
import utilisateur.*;

public class Controleur {	
	
	Utilisateur modele;
	Fenetre vue;
	
	public Controleur(Utilisateur modele, Fenetre vue) {
		this.modele = modele;
		this.vue = vue;
	}
	
	public void verifierPseudo(String pseudo) {
		if (pseudo.length() != 0) {
			if (modele.pseudoPris(pseudo)) {
				this.vue.erreurPseudo();
			}else {
				modele.changerPseudo(pseudo);
				vue.toHomePage();
			}
		}
	}
	
}
