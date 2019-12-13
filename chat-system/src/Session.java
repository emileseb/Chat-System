import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

class Session{
    private Parleur bouche;
    private Ecouteur oreille;
    private ArrayList<Message> conversation;
    private Utilisateur moi;
    private Id sonId;
    private Socket sock;


    Session(Socket sock, Utilisateur me) throws IOException {
        this.moi = me;
        this.sock = sock;
        this.conversation = new ArrayList<>();
        this.bouche = new Parleur(this);
        this.oreille = new Ecouteur(this);
        System.out.println("Etablissement du clavardage avec " + this.sonId);
    }

    Socket getSock() {
        return sock;
    }

    void setSonId(Id sonId) {
        this.sonId = sonId;
    }

    Utilisateur getMoi() {
        return moi;
    }

    ArrayList<Message> getConversation() {
        return conversation;
    }

    Id getSonId() {
        return sonId;
    }

    void fermerSession(){
        moi.mettreAJourHistorique(conversation,sonId);
        oreille.interrupt();
        bouche.interrupt();
        try {
            sock.close();
        } catch (IOException e) {
            System.out.println("Unable to close Socket");
        }

        System.out.println("Historique : \n" + moi.getHistoriqueDe(new Id(222222222)));
        ClavardageManager.supprimerSession(this);
    }
}