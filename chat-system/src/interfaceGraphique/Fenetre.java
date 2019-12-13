package interfaceGraphique;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import utilisateur.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Fenetre {

	private JFrame frmChatsystem;
	private JTextField fieldEntreePseudo;
	private JLabel labelErreurPseudo;
	JPanel welcomePage;
	JPanel homePage;
	
	Controleur controleur;

	/**
	 * Create the application.
	 */
	public Fenetre(Utilisateur modele) {
		controleur = new Controleur(modele, this);
		initialize();
		frmChatsystem.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatsystem = new JFrame();
		frmChatsystem.setTitle("ChatSystem");
		frmChatsystem.setResizable(false);
		frmChatsystem.setBounds(100, 100, 538, 413);
		frmChatsystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatsystem.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLayeredPane layeredPane = new JLayeredPane();
		frmChatsystem.getContentPane().add(layeredPane, BorderLayout.CENTER);
		
		
		welcomePage = welcomePage();
		homePage = homePage();
		homePage.setVisible(false);
		
		layeredPane.add(welcomePage);		
		layeredPane.add(homePage);
		
	}
	
	public JPanel welcomePage() {
		JPanel welcomePage = new JPanel();
		welcomePage.setBounds(0, 0, 534, 385);
		welcomePage.setLayout(null);
		
		JLabel labelChoisirPseudo = new JLabel("Choisissez un pseudo");
		labelChoisirPseudo.setBounds(221, 228, 157, 13);
		welcomePage.add(labelChoisirPseudo);
		
		JButton buttonValider = new JButton("Valider");
		buttonValider.setBounds(240, 300, 79, 21);
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
		labelErreurPseudo.setBounds(173, 251, 301, 13);
		labelErreurPseudo.setForeground(Color.RED);
		labelErreurPseudo.setVisible(false);
		welcomePage.add(labelErreurPseudo);
		return welcomePage;
	}
	
	public JPanel homePage() {
		JPanel homePage = new JPanel();
		homePage.setBounds(0, 0, 534, 385);
		
		return homePage;
	}
	
	private void clicValider() {
		controleur.verifierPseudo(fieldEntreePseudo.getText());
	}
	
	public void erreurPseudo() {
		labelErreurPseudo.setVisible(true);
	}
	
	public void toHomePage() {
		welcomePage.setVisible(false);
		homePage.setVisible(true);
	}
}
