import java.util.ArrayList;

public class Historique {
    private Id idPartenaire;
    private ArrayList<Message> historique;

    //Constructor
    public Historique(Id idPartenaire, ArrayList<Message> historique){
        this.idPartenaire = idPartenaire;
        this.historique= new ArrayList<Message>();
        this.ajouterMessage(historique);
    }

    public void ajouterMessage(ArrayList<Message> conversation){
        for (Message msg : conversation) {
            this.historique.add(msg);
        }
    }

    //Getters
    public Id getIdPartenaire() { return this.idPartenaire; }
    public ArrayList<Message> getHistorique() { return this.historique; }

    @Override
    public String toString() {
        String retour = "";
        for (Message m : historique ) {
            retour += m.toString() + "\n";
        }
        return retour;
    }

}
