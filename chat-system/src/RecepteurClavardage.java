import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class RecepteurClavardage extends Thread{
    private static int port = 5678;
    private Utilisateur me;
    private ArrayList<Session> clavadeursListe;
    private boolean continuer;

    public RecepteurClavardage (Utilisateur me){
        super();
        this.me = me;
        this.clavadeursListe = new ArrayList<Session>();
        this.continuer = true;
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
                for (Session s : this.clavadeursListe)
                    if (!s.isActive())
                        this.clavadeursListe.remove(s);
                i = 0;
            }else{
                i++;
            }
            System.out.println("waiting for clavateur to clavarde ...");
            // Récupération du socket associé
            this.clavadeursListe.add(new Session(servSock.accept(), me));
        }
    }

    public void arreter(){this.continuer = false;}
}
