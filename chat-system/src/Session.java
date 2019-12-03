import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class Session  extends Thread {
    private Socket clientSock_fd;
    private BufferedReader in;
    private PrintWriter out;

    Session(Socket sock_fd) throws IOException {
        super();
        this.clientSock_fd = sock_fd;
        this.in = new BufferedReader(new InputStreamReader(clientSock_fd.getInputStream()));
        this.out = new PrintWriter(clientSock_fd.getOutputStream(), true);
        this.start();
    }

    public void run() {
        System.out.println("Gadjo connection : " + this.getName());
        // out.println(LocalDate.now());
                out.println(LocalDateTime.now());
        try {
            clientSock_fd.close();
        } catch (IOException e) {
            System.out.println("Unable to close Socket");;
        }
        }

}
