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

    Ecouteur(Session sess) throws IOException {
        super();
        this.currentSession = sess;
        this.in = new BufferedReader(new InputStreamReader(sess.getSock().getInputStream()));
        sess.setSonId(new Id(in.readLine()));
        this.start();
    }

    public void run() {
        String input;
        Message rcvMsg;
        try {
            while (!this.isInterrupted()) {
                input = in.readLine();
                if (input != null) {
                    if (input.equals("quit")){
                        currentSession.fermerSession();
                    }else {
                        rcvMsg = new Message(currentSession.getMoi().trouveClient(currentSession.getSonId()), currentSession.getMoi(), input);
                        this.currentSession.getConversation().add(rcvMsg);
                        System.out.println(rcvMsg);
                    }
                }
            }
        }catch (SocketException e){
        } catch (IOException e) {
            System.out.println("oreille : IO exception raised " + e);
        }
        System.out.println("oreille : Fin");
    }
}