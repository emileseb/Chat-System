import java.util.ArrayList;

public class Historique {
    private ArrayList<Message> list_message;

    public Historique(ArrayList<Message> list_msg){
        this.list_message = new ArrayList<Message>();
        this.ajouterMessage(list_msg);
    }

    public void ajouterMessage(ArrayList<Message> conversation){
        for (Message msg : conversation) {
            this.list_message.add(msg);
        }
    }
    public ArrayList<Message> getHistorique(){
        return list_message;
    }

    @Override
    public String toString() {
        String retour = "";
        for (Message m : list_message ) {
            retour += m.toString() + "\n";
        }
        return retour;
    }
}
