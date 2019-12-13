import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class ClavardageManager extends Thread{
    private static int port = 5678;
    private Utilisateur me;
    private static ArrayList<Session> listeClavardage = new ArrayList<Session>();
    private boolean continuer;


    public ClavardageManager (Utilisateur me){
        super();
        this.me = me;
        this.continuer = true;
        this.start();
    }

    public void run() {
        try {
            System.out.println(me.getAdresseIp());
            // Création du serveur Socket
            ServerSocket servSock = new ServerSocket(port);
            int i = 0;
            while (this.continuer) {
                System.out.println("waiting for clavardeur to clavarde ...");
                // Récupération du socket associé
                Session sess = new Session(servSock.accept(), me);
                synchronized (listeClavardage) {
                    listeClavardage.add(sess);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demandeClavardage(Utilisateur moi, Id id){
        InetAddress serverAdress = null;
        try {
            serverAdress = InetAddress.getByName(moi.trouveClient(id).getAdresseIp());
            synchronized (listeClavardage) {
                listeClavardage.add(new Session(new Socket(serverAdress, port), moi));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerSession(Session sess){
        synchronized (listeClavardage) {
            listeClavardage.remove(sess);
        }
    }
}