import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Id {
    private long value;

    public Id() {
        try {
            Enumeration<NetworkInterface> networkInterface = NetworkInterface.getNetworkInterfaces();
            NetworkInterface network = networkInterface.nextElement();
            byte[] mac = network.getHardwareAddress();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
            }
            System.out.println();
            this.value = Long.parseLong(sb.toString(), 16);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    public Id(long valeur) {
    	this.value = valeur;
    }
    
    public long getValue () {
        return this.value;
    }

    public boolean equals(Id id) {
        return (this.value == id.value);
    }
    
}
