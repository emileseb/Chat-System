import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Ecouteur extends Thread {
    private Socket Sock_fd;
    private BufferedReader in;
    private ArrayList<Message> conversation;
    private Utilisateur me;
    private Id you;

    Ecouteur(Socket sock_fd, Utilisateur me, ArrayList<Message> conv) throws IOException {
        super();
        this.Sock_fd = sock_fd;
        this.in = new BufferedReader(new InputStreamReader(Sock_fd.getInputStream()));
        this.conversation = conv;
        this.me = me;
        this.start();
    }

    public void run() {
        boolean continuer = true;
        String input;
        try {
            while (continuer) {
                input = in.readLine();
                if (input.equals("quit")) {
                    System.out.println(input);
                }else {
                    continuer = false;
                }
            }
        }catch (SocketException e){
            System.out.println("closing oreille");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("oreille closed");
    }
}