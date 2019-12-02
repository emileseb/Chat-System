import java.util.ArrayList;

public class Agent {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Utilisateur jj = new Utilisateur("JJ", new Id(), true);
        Utilisateur thomas = new Utilisateur("Thomas", new Id(), true);
        ArrayList<Message> msglist = new ArrayList<Message>();
        System.out.println(thomas.getAdresseIp());

	}

}
