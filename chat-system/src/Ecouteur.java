import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

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
        boolean continuer = true;
        String input;
        Message rcvMsg;
        try {
            while (continuer) {
                input = in.readLine();
                if (input == null) {
                    continuer = false;
                }else {
                    rcvMsg = new Message(currentSession.getMoi().trouveClient(currentSession.getSonId()),currentSession.getMoi(),input);
                    this.currentSession.getConversation().add(rcvMsg);
                    System.out.println(rcvMsg);
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