package interfaceGraphique;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class Fenetre {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Create the application.
	 */
	public Fenetre() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 538, 413);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("Valider");
		btnNewButton.setBounds(218, 267, 82, 25);
		panel.add(btnNewButton);
		
		JLabel lblChoisissezUnPseudo = new JLabel("Choisissez un pseudo");
		lblChoisissezUnPseudo.setBounds(186, 180, 146, 15);
		panel.add(lblChoisissezUnPseudo);
		
		textField = new JTextField();
		textField.setBounds(197, 221, 124, 19);
		panel.add(textField);
		textField.setColumns(10);
	}
}
