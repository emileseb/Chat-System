import jdk.jshell.execution.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Session extends Thread {
    private Socket clientSock_fd;
    private BufferedReader in;
    private PrintWriter out;
    private boolean active;
    private ArrayList<Message> conversation;
    private Utilisateur me;
    private Id you;

    Session(Socket sock_fd, Utilisateur me) throws IOException {
        super();
        this.active = true;
        this.clientSock_fd = sock_fd;
        this.in = new BufferedReader(new InputStreamReader(clientSock_fd.getInputStream()));
        this.out = new PrintWriter(clientSock_fd.getOutputStream(), true);
        this.start();
        this.conversation = new ArrayList<Message>();
        this.me = me;
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

        //Clavardage

        this.closeSession();
    }


    private void initializeSession() throws IOException {
        //send my identity
        this.out.println(me.getId().toString());
        String input = in.readLine();
        this.you = new Id(input);
        System.out.println("Etablissement du clavardage avec " + this.you);
    }

    private void closeSession (){
        me.mettreAJourHistorique(conversation,you);
        try {
            clientSock_fd.close();
            this.active = false;
        } catch (IOException e) {
            System.out.println("Unable to close Socket");;
        }
    }
}