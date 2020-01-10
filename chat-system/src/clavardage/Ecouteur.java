package clavardage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import conversation.*;

import utilisateur.Id;

public class Ecouteur extends Thread {
    private BufferedReader in;
    private Session currentSession;

    // demandeur de connexion, il connait celui avec qui il parle
    // recepteur de connexion, il ne connait pas le demandeur
    public Ecouteur(Session sess, boolean demandeur) throws IOException {
        super();
        this.currentSession = sess;
        this.in = new BufferedReader(new InputStreamReader(sess.getSock().getInputStream()));
        if (!demandeur) {
        	sess.setLui(new Id(in.readLine()));
        }
        this.start();
    }

    public void run() {
        String input;
        Message rcvMsg;
        try {
            while (!this.isInterrupted()) {
                input = in.readLine();
                if (input != null) {
                    rcvMsg = new Message(currentSession.getLui(), currentSession.getMoi(), input);
                    if (input.equals(ClavardageManager.messageFin)){
                        currentSession.fermerSession();
                        ClavardageManager.controleur.receptionMessage(rcvMsg);
                        ClavardageManager.controleur.actualisationUtilisateurs();
                    }else {
                        this.currentSession.getConversation().add(rcvMsg);
                        ClavardageManager.controleur.receptionMessage(rcvMsg);
                    }
                }
            }
        }catch (SocketException e){
        } catch (IOException e) {
            System.out.println("oreille : IO exception raised " + e);
        }
        System.out.println("Fermeture oreille");
    }
}