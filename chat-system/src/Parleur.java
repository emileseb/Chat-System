import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Parleur extends Thread {
    private PrintWriter out;
    private Scanner scan;
    private Session currentSession;

    Parleur(Session sess) throws IOException {
        super();
        this.currentSession = sess;
        this.out = new PrintWriter(sess.getSock().getOutputStream(), true);
        this.scan = new Scanner(System.in);
        //send my identity
        this.out.println(sess.getMoi().getId().toString());
        this.start();
    }

    public void run(){
        boolean continuer = true;
        String scanned;
        Message sentMsg;
        while (continuer) {
            scanned = this.scan.nextLine();
            if (scanned.equals("quit")) {
                currentSession.fermerSession();
                continuer = false;
            } else {
                sentMsg = new Message(currentSession.getMoi(), currentSession.getMoi().trouveClient(currentSession.getSonId()), scanned);
                currentSession.getConversation().add(sentMsg);
                this.out.println(sentMsg.getContenu());
                System.out.println(sentMsg);
            }
        }
        System.out.println("Fermeture de la bouche ...");
    }
}