import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Ecouteur extends Thread {
    private Socket clientSock_fd;
    private BufferedReader in;
    private PrintWriter out;
    private boolean active;
    private ArrayList<Message> conversation;
    private Utilisateur me;
    private Id you;
    private Scanner scan;

    Ecouteur(Socket sock_fd, Utilisateur me) throws IOException {
        super();
        this.active = true;
        this.clientSock_fd = sock_fd;
        this.in = new BufferedReader(new InputStreamReader(clientSock_fd.getInputStream()));
        this.out = new PrintWriter(clientSock_fd.getOutputStream(), true);
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
}
