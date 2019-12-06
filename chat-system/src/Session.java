import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Session extends Thread {
    private Socket Sock_fd;
    private BufferedReader in;
    private PrintWriter out;
    private boolean active;
    private ArrayList<Message> conversation;
    private Utilisateur me;
    private Id you;
    private Scanner scan;
    private Ecouteur oreille;

    Session(Socket sock_fd, Utilisateur me) throws IOException {
        super();
        this.active = true;
        this.Sock_fd = sock_fd;
        this.in = new BufferedReader(new InputStreamReader(Sock_fd.getInputStream()));
        this.out = new PrintWriter(Sock_fd.getOutputStream(), true);
        this.conversation = new ArrayList<Message>();
        this.me = me;
        this.scan = new Scanner(System.in);
        this.start();
    }

    public boolean isActive() {
        return active;
    }

    public void run() {
        try {
            this.initializeSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.clavardage();
        this.closeSession();
    }


    private void initializeSession() throws IOException {
        //send my identity
        this.out.println(me.getId().toString());
        String input = in.readLine();
        this.you = new Id(input);
        System.out.println("Etablissement du clavardage avec " + this.you);
        oreille = new Ecouteur(Sock_fd, me, conversation);
    }

    private void closeSession (){
        System.out.println("Closing");
        me.mettreAJourHistorique(conversation,you);
        try {
            Sock_fd.close();
            this.active = false;
        } catch (IOException e) {
            System.out.println("Unable to close Socket");
        }
    }

    private void clavardage(){
        boolean continuer = true;
        String scanned = "";
        while(continuer) {
            scanned = scan.nextLine();
            if (scanned.equals("quit")) {
                continuer = false;
            } else {
                this.out.println(scanned);
            }
        }
    }
}