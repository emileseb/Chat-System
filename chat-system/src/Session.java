import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Session{
    private Parleur bouche;
    private Ecouteur oreille;
    private ArrayList<Message> conversation;
    private Utilisateur moi;
    private Id sonId;
    private Socket sock;


    Session(Socket sock, Utilisateur me) throws IOException {
        this.moi = me;
        this.sock = sock;
        this.conversation = new ArrayList<Message>();
        this.bouche = new Parleur(this);
        this.oreille = new Ecouteur(this);
        System.out.println("Etablissement du clavardage avec " + this.sonId);
    }

    public Socket getSock() {
        return sock;
    }

    public void setSonId(Id sonId) {
        this.sonId = sonId;
    }

    public Utilisateur getMoi() {
        return moi;
    }

    public ArrayList<Message> getConversation() {
        return conversation;
    }

    public Id getSonId() {
        return sonId;
    }

    public void fermerSession(){
        moi.mettreAJourHistorique(conversation,sonId);
        oreille.interrupt();
        bouche.interrupt();
        try {
            sock.close();
        } catch (IOException e) {
            System.out.println("Unable to close Socket");
        }

        System.out.println("Historique de la Parleur  : \n" + moi.getHistoriqueDe(new Id(222222222)));
        ClavardageManager.supprimerSession(this);
    }
}