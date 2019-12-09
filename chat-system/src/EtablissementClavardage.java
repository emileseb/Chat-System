import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

public class EtablissementClavardage extends Thread{
    private static int port = 5678;
    private Utilisateur me;
    private static ArrayList<Session> clavadeursListe =new ArrayList<Session>();
    private boolean continuer;


    public EtablissementClavardage (Utilisateur me){
        super();
        this.me = me;
        this.continuer = true;
        this.start();
    }
    public void run() {
        try {
            this.accepteur();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void accepteur() throws IOException {
        System.out.println(me.getAdresseIp());
        // Création du serveur Socket
        ServerSocket servSock = new ServerSocket(port);
        int i = 0;
        while (this.continuer) {
            if (i==50){
                synchronized (clavadeursListe) {
                    for (Session s : clavadeursListe)
                        if (!s.isActive())
                            clavadeursListe.remove(s);
                }
                i = 0;
            }else{
                i++;
            }
            System.out.println("waiting for clavateur to clavarde ...");
            // Récupération du socket associé
            Session sess = new Session(servSock.accept(), me);
            synchronized (clavadeursListe) {
                clavadeursListe.add(sess);
            }
        }
    }
    public void arreter(){this.continuer = false;}

    public static void demandeClavardage(Utilisateur moi, Id id){
        InetAddress serverAdress = null;
        try {
            serverAdress = InetAddress.getByName(moi.trouveClient(id).getAdresseIp());
            synchronized (clavadeursListe) {
                clavadeursListe.add(new Session(new Socket(serverAdress, port), moi));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}