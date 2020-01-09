package clavardage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import conversation.Message;
import utilisateur.Id;
import utilisateur.Utilisateur;

public class Session{
    private Parleur bouche;
    private Ecouteur oreille;
    private ArrayList<Message> conversation;
    private Utilisateur moi;
    private Utilisateur lui;
    private Socket sock;
    private Scanner scan;
    private PrintWriter out;

    // pour celui qui demande la connexion, il connait le destinataire
    public Session(Socket sock, Utilisateur me, Utilisateur lui) throws IOException {
        this.scan = new Scanner(System.in);
        this.moi = me;
        this.lui = lui;
        this.sock = sock;
        this.conversation = new ArrayList<>();
        this.oreille = new Ecouteur(this,true);
        this.out = new PrintWriter(sock.getOutputStream(), true);
        //send my identity
        this.out.println(moi.getId().toString());
        System.out.println("Etablissement du clavardage avec " + lui.getId());
    }
    
    // pour celui qui recoit la connexion, il ne connait pas le demandeur
    public Session(Socket sock, Utilisateur me) throws IOException {
        this.scan = new Scanner(System.in);
        this.moi = me;
        this.sock = sock;
        this.conversation = new ArrayList<>();
        this.oreille = new Ecouteur(this,false);
        this.out = new PrintWriter(sock.getOutputStream(), true);
        System.out.println("Etablissement du clavardage avec " + lui.getId());
    }

    public Socket getSock() {
        return sock;
    }

    public Scanner getScan() {
        return scan;
    }

    public Utilisateur getMoi() {
        return moi;
    }

    public Utilisateur getLui() {
        return lui;
    }
    
    public ArrayList<Message> getConversation() {
        return conversation;
    }

    public Id getSonId() {
        return lui.getId();
    }
    
    public void setLui(Id id) {
    	lui = moi.trouveClient(id);
    }
    
    public void envoyerMessage(String msg) {
    	Message sentMsg = new Message(moi, lui, msg);
        conversation.add(sentMsg);
        this.out.println(msg);
    }

    public void fermerSession(){
        moi.mettreAJourHistorique(conversation,lui.getId());
        oreille.interrupt();
        try {
            sock.close();
        } catch (IOException e) {
            System.out.println("Unable to close Socket");
        }
        System.out.println("Historique : \n" + moi.getHistoriqueDe(lui.getId()));
        ClavardageManager.supprimerSession(this);
    }
}