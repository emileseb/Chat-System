
public class Application {

	public static void main(String[] args) {
		Agent agent = new Agent();
		agent.changerPseudo("louis", new Id(1));
		System.out.println(agent.listeUtilisateurs);
	}

}
