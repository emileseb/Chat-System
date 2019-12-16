package clavardage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import conversation.*;

public class Parleur extends Thread {
    private PrintWriter out;
    private Session currentSession;

    Parleur(Session sess) throws IOException {
        super();
        this.currentSession = sess;
        this.out = new PrintWriter(sess.getSock().getOutputStream(), true);
        //send my identity
        this.out.println(sess.getMoi().getId().toString());
        this.start();
    }

    public void run(){
        String scanned;
        Message sentMsg;
        while (!this.isInterrupted()) {
            scanned = currentSession.getScan().nextLine();
            if (!this.isInterrupted()) {
                if (scanned.equals("quit")) {
                    this.out.println("quit");
                    currentSession.fermerSession();
                } else {
                    sentMsg = new Message(currentSession.getMoi(), currentSession.getMoi().trouveClient(currentSession.getSonId()), scanned);
                    currentSession.getConversation().add(sentMsg);
                    this.out.println(sentMsg.getContenu());
                    System.out.println(sentMsg);
                }
            }
        }
        System.out.println("bouche : Fin");
    }
}