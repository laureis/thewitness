import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class MenuTW extends JMenuBar {

	private JMenu m_jouer = new JMenu("Jouer");
	private JMenu m_aide = new JMenu("Aide");
	private JMenu m_select = new JMenu("Sélectionner");
	private JMenuItem i_regles = new JMenuItem("Règles du jeu");
	private JMenuItem i_how = new JMenuItem("Comment jouer?");
	private JMenuItem i_fermer = new JMenuItem("Fermer"); 
	private String tabNiveaux[] = { "Facile", "Moyen", "Difficile", "Bonus" };
	
	
	// Constructeur 
	
	// type 0 : main menu, type 1 : puzzle, type 2 : puzzle symétrique 
	
	public MenuTW (int type) {
		
		for (int i=0; i<tabNiveaux.length; i++) {
			
			String s = tabNiveaux[i];
			JMenuItem it = new JMenuItem(s);
			it.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					@SuppressWarnings("unused")
					Puzzle p = new Puzzle("fichiers_puzzle\\"+s+".txt"); }
			
		});
			this.m_select.add(it);
		}
		
		this.m_jouer.add(this.m_select);
		this.m_jouer.addSeparator();
		i_fermer.addActionListener ( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
		});
		this.m_jouer.add(i_fermer);
		this.add(m_jouer);
		
		// Partie "Aide" du menu
		i_regles.addActionListener ( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, TheWitness.reglesPuzzle+"\n\n"+TheWitness.reglesPuzzleSym, "The Witness : règles du jeu" ,JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		this.m_aide.add(i_regles);
		
		i_how.addActionListener ( new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, TheWitness.aide, "Comment jouer ? " ,JOptionPane.INFORMATION_MESSAGE);
				}
		});
		
		this.m_aide.add(i_how);
		
		this.add(m_aide);
		
		
	}
}
