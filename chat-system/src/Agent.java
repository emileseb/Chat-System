import java.util.ArrayList;

public class Agent {

    //utilisateur
    private ArrayList<Utilisateur> listeUtilisateurs;

    public boolean clientExiste(Id idAgent){
        boolean result = false;
        for (Utilisateur utilisateur : listeUtilisateurs) {
            if (utilisateur.getId().equals(idAgent)) {
                result = true;
                break;
            }
        }
        return result;
    }

    //historique


    public static void main(String[] args) {
		// TODO Auto-generated method stub
        Utilisateur jj = new Utilisateur("JJ", new Id(), true);
        Utilisateur thomas = new Utilisateur("Thomas", new Id(), true);
        ArrayList<Message> msglist = new ArrayList<Message>();
        System.out.println(thomas.getAdresseIp());
	}

}
