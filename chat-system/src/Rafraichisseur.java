
public class Rafraichisseur extends Thread {

	Agent agent;
	
	public Rafraichisseur(Agent agent){
		this.agent = agent;
	}
	
	public void run() {
		agent.changerPseudo("louis", new Id(1));
	}
	
}
