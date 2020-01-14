package conversation;
import java.util.ArrayList;

import utilisateur.Id;

public class Historique {
    private Id idPartenaire;
    private ArrayList<Message> historique;

    //Constructor
    public Historique(Id idPartenaire, ArrayList<Message> historique){
        this.idPartenaire = idPartenaire;
        this.historique= new ArrayList<>();
        this.ajouterMessage(historique);
    }

    public void ajouterMessage(ArrayList<Message> conversation){
        this.historique.addAll(conversation);
    }

    //Getters
    public Id getIdPartenaire() { return this.idPartenaire; }
    public ArrayList<Message> getHistorique() { return this.historique; }

    @Override
    public String toString() {
        StringBuilder retour = new StringBuilder();
        for (Message m : historique ) {
            retour.append(m.toString()).append("\n");
        }
        return retour.toString();
    }

}
