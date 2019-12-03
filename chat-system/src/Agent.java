import java.util.ArrayList;
import java.util.Iterator;

public class Agent {
    private Utilisateur me;

    //getters


    public Utilisateur getMe() {
        return me;
    }

    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------utilisateur---------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
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


    //------------------------------------------------------------------------------------------------------------------
    //----------------------------------------------Historique---------------------------------------------------------
    //------------------------------------------------------------------------------------------------------------------
    //attributs
    private ArrayList<Historique> mesHistoriques;

    public ArrayList<Historique> getMesHistoriques() {
        return mesHistoriques;
    }

    //méthodes
    public boolean HistoriqueExiste(Id idPartenaire){
        boolean result = false;
        Iterator<Historique> iter = mesHistoriques.iterator();
        while (iter.hasNext() && !result) {
            if (iter.next().getIdPartenaire().equals(idPartenaire))
                result = true;
        }
        return result;
    }

    public ArrayList<Message> getHistoriqueDe(Id idPartenaire){
        ArrayList<Message> listeMessage = new ArrayList<Message>();
        Iterator<Historique> iter = mesHistoriques.iterator();
        while (iter.hasNext()) {
            Historique hist = iter.next();
            if (hist.getIdPartenaire().equals(idPartenaire)) {
                return hist.getHistorique();
            }
        }
        return listeMessage;
    }

    public void MettreAJourHistorique(ArrayList<Message> conversation, Id idPartenaire) {
        boolean pastrouve = true;
        Iterator<Historique> iter = mesHistoriques.iterator();
        while (iter.hasNext() && pastrouve) {
            Historique hist = iter.next();
            if (hist.getIdPartenaire().equals(idPartenaire)) {
                hist.ajouterMessage(conversation);
                pastrouve = false;
            }
        }
        if (pastrouve)
            mesHistoriques.add(new Historique(idPartenaire, conversation));
    }

    //-------------------------------------------Constructor------------------------------------------------------------
    public Agent() {
        this.me = new Utilisateur(/*TODO*/"TODO",new Id(),true)
        Utilisateur user1 = new Utilisateur("emile", new Id(1), true);
        Utilisateur user2 = new Utilisateur("thomas", new Id(2), true);
        Utilisateur user3 = new Utilisateur("morvan", new Id(), true);
        this.listeUtilisateurs = new ArrayList<Utilisateur>();
        this.listeUtilisateurs.add(user1);
        this.listeUtilisateurs.add(user2);
        this.listeUtilisateurs.add(user3);
    }

}
