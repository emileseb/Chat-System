package interfaceGraphique;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JTextField;

import utilisateur.Utilisateur;

import javax.swing.JTabbedPane;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.*;


import clavardage.*;
import conversation.Message;
import javax.swing.JTextPane;

public class FenetrePrincipale {

	private JFrame frame;
	private JPanel panelLeft;
	private JPanel panelPseudo;
	private JTabbedPane panelOnglets;
	private JPanel panelHistoriques;
	private JPanel panelClavardeurs;
	private JTextPane areaMessages;
	private StyledDocument document;
	private SimpleAttributeSet left;
	private SimpleAttributeSet right;
	
	private JLabel labelPseudo;
	private JButton boutonChangerPseudo;
	private JButton boutonValider;
	private JTextField fieldEntreePseudo;
	private JLabel labelPseudoErreur;
	private JLabel labelPseudoPartenaire;
	private JTextField entreeMessage;
	private JButton boutonEnvoyer;
	private JButton boutonClavarder;
	private JButton boutonFinClavardage;

	private Controleur controleur;
	public Utilisateur utilisateurSelectionne;
	/**
	 * Create the application.
	 */
	public FenetrePrincipale(Controleur controleur) {
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
		frame.setBounds(100, 100, 739, 520);
		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                controleur.fermetureApp();
                e.getWindow().dispose();
            }
        });
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0};
		gridBagLayout.columnWeights = new double[]{1.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		panelLeft();

		panelPseudo();
		
		panelOnglets();
		
		panelRight();
		
	}
	
	private void panelLeft() {
		panelLeft = new JPanel();
		GridBagConstraints gbc_panelLeft = new GridBagConstraints();
		gbc_panelLeft.fill = GridBagConstraints.BOTH;
		gbc_panelLeft.gridx = 0;
		gbc_panelLeft.gridy = 0;
		frame.getContentPane().add(panelLeft, gbc_panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWeights = new double[]{1.0};
		gbl_panelLeft.rowWeights = new double[]{1.0, 1.0};
		panelLeft.setLayout(gbl_panelLeft);
	}
	
	/*Panel Pseudo*/
	private void panelPseudo() {
		panelPseudo = new JPanel();
		GridBagConstraints gbc_panelPseudo = new GridBagConstraints();
		gbc_panelPseudo.insets = new Insets(0, 0, 5, 0);
		gbc_panelPseudo.fill = GridBagConstraints.BOTH;
		gbc_panelPseudo.gridx = 0;
		gbc_panelPseudo.gridy = 0;
		panelLeft.add(panelPseudo, gbc_panelPseudo);
		GridBagLayout gbl_panelPseudo = new GridBagLayout();
		gbl_panelPseudo.columnWidths = new int[]{0, 0, 0};
		gbl_panelPseudo.rowHeights = new int[]{0, 0, 0};
		gbl_panelPseudo.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelPseudo.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelPseudo.setLayout(gbl_panelPseudo);
		
		labelPseudo = new JLabel(controleur.demandePseudo());
		GridBagConstraints gbc_labelPseudo = new GridBagConstraints();
		gbc_labelPseudo.insets = new Insets(10, 10, 5, 5);
		gbc_labelPseudo.gridx = 0;
		gbc_labelPseudo.gridy = 0;
		gbc_labelPseudo.weightx = 2;
		panelPseudo.add(labelPseudo, gbc_labelPseudo);		

		fieldEntreePseudo = new JTextField();
		fieldEntreePseudo.setVisible(false);
		GridBagConstraints gbc_fieldEntreePseudo = new GridBagConstraints();
		gbc_fieldEntreePseudo.fill = GridBagConstraints.HORIZONTAL;
		gbc_fieldEntreePseudo.insets = new Insets(10, 10, 5, 5);
		gbc_fieldEntreePseudo.gridx = 0;
		gbc_fieldEntreePseudo.gridy = 0;
		panelPseudo.add(fieldEntreePseudo, gbc_fieldEntreePseudo);
		fieldEntreePseudo.setColumns(10);
		
		boutonChangerPseudo = new JButton("Changer pseudo");
		boutonChangerPseudo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boutonChangerPseudo.setVisible(false);
				boutonValider.setVisible(true);
				labelPseudo.setVisible(false);
				fieldEntreePseudo.setVisible(true);
			}
		});
		GridBagConstraints gbc_boutonChangerPseudo = new GridBagConstraints();
		gbc_boutonChangerPseudo.insets = new Insets(5, 0, 5, 0);
		gbc_boutonChangerPseudo.gridx = 1;
		gbc_boutonChangerPseudo.gridy = 0;
		panelPseudo.add(boutonChangerPseudo, gbc_boutonChangerPseudo);

		boutonValider = new JButton("Valider");
		boutonValider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicValider();
			}
		});
		boutonValider.setVisible(false);
		GridBagConstraints gbc_boutonValider = new GridBagConstraints();
		gbc_boutonValider.insets = new Insets(5, 0, 5, 0);
		gbc_boutonValider.gridx = 1;
		gbc_boutonValider.gridy = 0;
		panelPseudo.add(boutonValider, gbc_boutonValider);
		
		labelPseudoErreur = new JLabel("Pseudo déjà pris");
		labelPseudoErreur.setForeground(Color.RED);
		labelPseudoErreur.setVisible(false);
		GridBagConstraints gbc_labelPseudoErreur = new GridBagConstraints();
		gbc_labelPseudoErreur.anchor = GridBagConstraints.EAST;
		gbc_labelPseudoErreur.insets = new Insets(0, 0, 0, 5);
		gbc_labelPseudoErreur.gridx = 0;
		gbc_labelPseudoErreur.gridy = 1;
		panelPseudo.add(labelPseudoErreur, gbc_labelPseudoErreur);
	}

	private void clicValider() {
		controleur.verifierPseudo(fieldEntreePseudo.getText());
	}
	
	public void erreurPseudo() {
		labelPseudoErreur.setVisible(true);
	}
	
	public void pseudoChange() {
		boutonChangerPseudo.setVisible(true);
		boutonValider.setVisible(false);
		labelPseudo.setVisible(true);
		labelPseudo.setText(controleur.demandePseudo());
		labelPseudoErreur.setVisible(false);
		fieldEntreePseudo.setVisible(false);
		fieldEntreePseudo.setText("");
	}
	
	/*Panel Onglets*/
	private void panelOnglets() {
		panelOnglets = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_panelOnglets = new GridBagConstraints();
		gbc_panelOnglets.fill = GridBagConstraints.BOTH;
		gbc_panelOnglets.gridx = 0;
		gbc_panelOnglets.gridy = 1;
		gbc_panelOnglets.weighty = 15;
		panelLeft.add(panelOnglets, gbc_panelOnglets);
		
		panelHistoriques();
		
		panelClavardeur();	
	}
	
	private void panelHistoriques() {
		panelHistoriques = new JPanel();
		JScrollPane scroll = new JScrollPane(panelHistoriques, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelOnglets.addTab("Historiques", null, scroll, null);
		panelHistoriques.setLayout(new GridLayout(50, 1));
		
		afficherHistoriques();
	}
	
	public void afficherHistoriques() {
		panelHistoriques.removeAll();
		ArrayList<Utilisateur> listeUtilisateurs = controleur.demandeUtilisateursHistorique();
		for (Utilisateur user : listeUtilisateurs) {
			JButton btnUser = new JButton(user.getPseudo());
			btnUser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clicUtilisateurHistorique(user);
				}
			});
			panelHistoriques.add(btnUser);
		}
	}

	private void clicUtilisateurHistorique(Utilisateur user) {
		entreeMessage.setVisible(false);
		boutonEnvoyer.setVisible(false);
		boutonClavarder.setVisible(false);
		labelPseudoPartenaire.setText(user.getPseudo());

		afficherHistorique(user);
	}
	
	private void panelClavardeur() {
		panelClavardeurs = new JPanel();
		JScrollPane scroll = new JScrollPane(panelClavardeurs, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelOnglets.addTab("Clavardeurs", null, scroll, null);
		panelClavardeurs.setLayout(new GridLayout(50, 1));
		
		afficherClavardeurs();
	}
	
	public void afficherClavardeurs() {
		panelClavardeurs.removeAll();
		//ajout utilisateurs en cours
		ArrayList<Utilisateur> listeUtilisateursEnCours = new ArrayList<>();
		for (Session sess : ClavardageManager.getListeSessions()) {
			listeUtilisateursEnCours.add(sess.getLui());
			JButton btnUser = new JButton(sess.getLui().getPseudo());
			btnUser.setBackground(Color.GREEN);
			btnUser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clicUtilisateurEnCours(sess.getLui());
				}
			});
			panelClavardeurs.add(btnUser);			
		}
		// ajout utilisateur actifs
		ArrayList<Utilisateur> listeUtilisateurs = controleur.demandeUtilisateursActifs();
		for (Utilisateur user : listeUtilisateurs) {
			if (!listeUtilisateursEnCours.contains(user)) {
				JButton btnUser = new JButton(user.getPseudo());
				btnUser.setBackground(Color.CYAN);
				btnUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						clicUtilisateurActifs(user);
					}
				});
				panelClavardeurs.add(btnUser);
			}	
		}
	}
	
	private void clicUtilisateurActifs(Utilisateur user) {
		boutonClavarder.setVisible(true);
		boutonFinClavardage.setVisible(false);
		boutonEnvoyer.setVisible(false);
		entreeMessage.setVisible(false);
		labelPseudoPartenaire.setText(user.getPseudo());
		areaMessages.setText("");
		utilisateurSelectionne = user;
	}
	
	private void clicUtilisateurEnCours(Utilisateur user) {
		boutonClavarder.setVisible(false);
		boutonFinClavardage.setVisible(true);
		boutonEnvoyer.setVisible(true);
		entreeMessage.setVisible(true);
		labelPseudoPartenaire.setText(user.getPseudo());
		utilisateurSelectionne = user;
		//afficher la conversation
		areaMessages.setText("");
		afficherHistorique(user);
		ArrayList<Message> conv = ClavardageManager.trouveSession(user.getId()).getConversation();
		for (Message msg : conv) {
			afficherMessage(msg);
		}		
	}
	
	/*Panel Droite*/
	private void panelRight() {
		JPanel panelRight = new JPanel();
		GridBagConstraints gbc_panelRight = new GridBagConstraints();
		gbc_panelRight.fill = GridBagConstraints.BOTH;
		gbc_panelRight.gridx = 1;
		gbc_panelRight.weightx = 3;
		gbc_panelRight.gridy = 0;
		frame.getContentPane().add(panelRight, gbc_panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWeights = new double[]{1.0, 0.0};
		gbl_panelRight.rowWeights = new double[]{0.0, 1.0, 0.0};
		panelRight.setLayout(gbl_panelRight);
		
		labelPseudoPartenaire = new JLabel("");
		GridBagConstraints gbc_labelPseudoPartenaire = new GridBagConstraints();
		gbc_labelPseudoPartenaire.insets = new Insets(0, 0, 5, 0);
		gbc_labelPseudoPartenaire.gridx = 0;
		gbc_labelPseudoPartenaire.gridy = 0;
		gbc_labelPseudoPartenaire.gridwidth = 2;
		panelRight.add(labelPseudoPartenaire, gbc_labelPseudoPartenaire);
		
		JScrollPane scrollPanel = new JScrollPane();
		scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPanel = new GridBagConstraints();
		gbc_scrollPanel.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanel.fill = GridBagConstraints.BOTH;
		gbc_scrollPanel.gridx = 0;
		gbc_scrollPanel.gridy = 1;
		gbc_scrollPanel.gridwidth = 2;
		panelRight.add(scrollPanel, gbc_scrollPanel);
		
		areaMessages = new JTextPane();
		areaMessages.setEditable(false);
		document = areaMessages.getStyledDocument();
		left = new SimpleAttributeSet();
		right = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT); 
		scrollPanel.setViewportView(areaMessages);
		
		entreeMessage = new JTextField();
		entreeMessage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					clicEnvoyer();
				}
			}
		});
		entreeMessage.setVisible(false);
		GridBagConstraints gbc_entreeMessage = new GridBagConstraints();
		gbc_entreeMessage.insets = new Insets(0, 0, 5, 5);
		gbc_entreeMessage.fill = GridBagConstraints.HORIZONTAL;
		gbc_entreeMessage.gridx = 0;
		gbc_entreeMessage.gridy = 2;
		panelRight.add(entreeMessage, gbc_entreeMessage);
		entreeMessage.setColumns(10);
		
		boutonEnvoyer = new JButton("Envoyer");
		boutonEnvoyer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicEnvoyer();
			}
		});
		boutonEnvoyer.setVisible(false);
		GridBagConstraints gbc_boutonEnvoyer = new GridBagConstraints();
		gbc_boutonEnvoyer.insets = new Insets(0, 0, 5, 0);
		gbc_boutonEnvoyer.gridx = 1;
		gbc_boutonEnvoyer.gridy = 2;
		panelRight.add(boutonEnvoyer, gbc_boutonEnvoyer);
		
		boutonClavarder = new JButton("Clavarder");
		boutonClavarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicClavarder();
			}
		});
		boutonClavarder.setVisible(false);
		GridBagConstraints gbc_boutonClavarder = new GridBagConstraints();
		gbc_boutonEnvoyer.insets = new Insets(0, 0, 5, 0);
		gbc_boutonEnvoyer.gridx = 1;
		gbc_boutonEnvoyer.gridy = 2;
		panelRight.add(boutonClavarder, gbc_boutonClavarder);
		
		boutonFinClavardage = new JButton("Fin");
		boutonFinClavardage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicFinClavardage();
			}
		});
		boutonFinClavardage.setVisible(false);
		GridBagConstraints gbc_boutonFinClavarder = new GridBagConstraints();
		gbc_boutonEnvoyer.insets = new Insets(0, 0, 5, 0);
		gbc_boutonEnvoyer.gridx = 1;
		gbc_boutonEnvoyer.gridy = 2;
		panelRight.add(boutonFinClavardage, gbc_boutonFinClavarder);
	}
	
	public void clearRightPanel() {
		areaMessages.setText("");
		entreeMessage.setVisible(false);
		boutonEnvoyer.setVisible(false);
		boutonClavarder.setVisible(false);
		boutonFinClavardage.setVisible(false);
		
	}

	private void clicClavarder() {
		ClavardageManager.demandeClavardage(controleur.demandeUtilisateur(), utilisateurSelectionne);
		affichageDebutClavardage();
		afficherClavardeurs();
		afficherHistorique(utilisateurSelectionne);
	}
	
	private void clicFinClavardage() {
		ClavardageManager.envoyerMessage(utilisateurSelectionne, ClavardageManager.messageFin);
		affichageFinClavardage();
		afficherClavardeurs();
		afficherHistoriques();	
	}
	
	public void affichageDebutClavardage() {
		boutonClavarder.setVisible(false);
		boutonFinClavardage.setVisible(true);
		boutonEnvoyer.setVisible(true);
		entreeMessage.setVisible(true);		
	}
	
	public void affichageFinClavardage() {
		boutonClavarder.setVisible(true);
		boutonFinClavardage.setVisible(false);
		boutonEnvoyer.setVisible(false);
		entreeMessage.setVisible(false);	
	}
	
	private void clicEnvoyer() {
		ClavardageManager.envoyerMessage(utilisateurSelectionne, entreeMessage.getText());
		afficherMessage(new Message(controleur.demandeUtilisateur(), utilisateurSelectionne, entreeMessage.getText()));
		entreeMessage.setText("");
	}
	
	public void afficherMessage(Message msg) {       
		try{
			if (msg.getAuteur().equals(controleur.demandeUtilisateur())) {
				document.setParagraphAttributes(document.getLength(), 1, right, false);
				document.insertString(document.getLength(), msg.toString() + "\n", right);
			}else {
				document.setParagraphAttributes(document.getLength(), 1, left, false);	
				document.insertString(document.getLength(), msg.toString() + "\n", left);				
			}			
		}catch(BadLocationException ex) {
			
		}		
	}
	
	public void afficherHistorique(Utilisateur user) {
		areaMessages.setText("");
		ArrayList<Message> conversation = controleur.demandeHistoriqueDe(user);
		for (Message msg : conversation) {
			afficherMessage(msg);
		}	
	}
}
