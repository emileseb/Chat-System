package interfaceGraphique;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FenetreAccueil {

	private JFrame frame;
	private JTextField fieldEntreePseudo;
	private JLabel labelErreurPseudo;
	private JPanel welcomePage;
	
	private Controleur controleur;

	/**
	 * Create the application.
	 */
	public FenetreAccueil(Controleur controleur) {
		this.controleur = controleur;
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("ChatSystem");
		frame.setResizable(false);
		frame.setBounds(100, 100, 538, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		welcomePage = welcomePage();
		
		frame.getContentPane().add(welcomePage, BorderLayout.CENTER);
		
	}
	
	public JPanel welcomePage() {
		JPanel welcomePage = new JPanel();
		welcomePage.setBounds(0, 0, 534, 385);
		welcomePage.setLayout(null);
		
		JLabel labelChoisirPseudo = new JLabel("Choisissez un pseudo");
		labelChoisirPseudo.setBounds(203, 228, 157, 13);
		welcomePage.add(labelChoisirPseudo);
		
		JButton buttonValider = new JButton("Valider");
		buttonValider.setBounds(221, 302, 96, 21);
		buttonValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicValider();
			}
		});
		welcomePage.add(buttonValider);
		
		fieldEntreePseudo = new JTextField();
		fieldEntreePseudo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					clicValider();
				}
			}
		});
		fieldEntreePseudo.setBounds(223, 271, 96, 19);
		welcomePage.add(fieldEntreePseudo);
		fieldEntreePseudo.setColumns(10);
		
		JLabel labelLogo = new JLabel("Logo");
		labelLogo.setBounds(183, 40, 195, 157);
		labelLogo.setIcon(new ImageIcon(new File("image/logo.jpg").getAbsolutePath()));
		welcomePage.add(labelLogo);
		
		labelErreurPseudo = new JLabel("Pseudo d\u00E9j\u00E0 pris, veuillez en choisir un autre");
		labelErreurPseudo.setBounds(145, 246, 351, 13);
		labelErreurPseudo.setForeground(Color.RED);
		labelErreurPseudo.setVisible(false);
		welcomePage.add(labelErreurPseudo);
		return welcomePage;
	}
	
	private void clicValider() {
		controleur.verifierPseudo(fieldEntreePseudo.getText());
	}
	
	public void erreurPseudo() {
		labelErreurPseudo.setVisible(true);
	}
	
	public void toHomePage() {
		welcomePage.setVisible(false);
		frame.dispose();
	}
}
