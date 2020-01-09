package clavardage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import conversation.Message;
import interfaceGraphique.Controleur;
import utilisateur.Id;
import utilisateur.Utilisateur;

public class ClavardageManager extends Thread{
    private static int port = 5678;
    private Utilisateur me;
    private static ArrayList<Session> listeClavardage = new ArrayList<>();
    private boolean continuer;
    public static Controleur controleur;

    public ClavardageManager(Utilisateur me, Controleur controleur){
        super();
        this.me = me;
        this.continuer = true;
        this.controleur = controleur;
        this.start();
    }

    public void run() {
        try {
            System.out.println(me.getAdresseIp());
            // Création du serveur Socket
            ServerSocket servSock = new ServerSocket(port);
            while (this.continuer) {
                System.out.println("waiting for clavardeur to clavarde ...");
                // Récupération du socket associé
                Session sess = new Session(servSock.accept(), me);
                synchronized (listeClavardage) {
                    listeClavardage.add(sess);
                    ClavardageManager.controleur.receptionConnexion();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demandeClavardage(Utilisateur moi, Utilisateur lui){
        InetAddress serverAdress;
        try {
            serverAdress = InetAddress.getByName(lui.getAdresseIp());
            synchronized (listeClavardage) {
                listeClavardage.add(new Session(new Socket(serverAdress, port), moi, lui));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void envoyerMessage(Utilisateur lui, String msg) {
    	trouveSession(lui.getId()).envoyerMessage(msg);
    }

    public static void supprimerSession(Session sess){
        synchronized (listeClavardage) {
            listeClavardage.remove(sess);
        }
    }
    
    public static Session trouveSession(Id idAgent){
        Session sess;
        Iterator<Session> iter = listeClavardage.iterator();
        while(iter.hasNext()) {
            sess = iter.next();
            if (sess.getSonId().equals(idAgent)) {
                return sess;
            }
        }
        return null;
    }
    
    public static ArrayList<Session> getListeSessions() {
    	return listeClavardage;
    }
}