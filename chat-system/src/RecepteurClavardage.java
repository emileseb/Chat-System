import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class RecepteurClavardage extends Thread{
    private static int port = 5678;
    private Utilisateur me;
    private ArrayList<Session> clavadeursListe;

    public RecepteurClavardage (Utilisateur me){
        super();
        this.me = me;
        clavadeursListe = new ArrayList<>();
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
        /* // Configuration des input et output
        BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));
        PrintWriter out = new PrintWriter(link.getOutputStream(), true);*/
        // Envoi des données
        boolean continuer = true;
        while (continuer) {
            System.out.println("waiting for gadjo to come ...");
            // Récupération du socket associé
            clavadeursListe.add(new Session(servSock.accept()));
        }
    }

}
