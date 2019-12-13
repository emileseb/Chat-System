import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClavardageManager extends Thread{
    private static int port = 5678;
    private Utilisateur me;
    private static ArrayList<Session> listeClavardage = new ArrayList<>();
    private boolean continuer;


    ClavardageManager (Utilisateur me){
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

    static void demandeClavardage(Utilisateur moi, Id id){
        InetAddress serverAdress;
        try {
            serverAdress = InetAddress.getByName(moi.trouveClient(id).getAdresseIp());
            synchronized (listeClavardage) {
                listeClavardage.add(new Session(new Socket(serverAdress, port), moi));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void supprimerSession(Session sess){
        synchronized (listeClavardage) {
            listeClavardage.remove(sess);
        }
    }
}