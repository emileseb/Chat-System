import java.util.ArrayList;
import java.util.Iterator;

public class Agent {

    private Id id;


    public Id getId() {
        return id;
    }

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



}