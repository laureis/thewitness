import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** CLASSE THEWITNESS
 * le menu principal du jeu
 */
@SuppressWarnings("serial")
public class TheWitness extends JFrame{

	
	// Règles du jeu et aide 
	public static String reglesPuzzle = "PUZZLE : \n \nLe but du jeu est d'arriver jusqu'à la cible en passant par toutes les cases rouges et bleus.\n"
			+ "Il faut alterner rouge/bleu ou bleu/rouge "
			+ "en 4 cases maximum entre chaque chaque case de couleur.\n"
			+ "Vous pouvez revenir sur vos pas et relancer le jeu à tout moment.\n\n";
	
	public static String reglesPuzzleSym = "PUZZLE SYMETRIQUE : \n \nLe but est simplement d'arriver à la cible. Un chemin 'miroir' selon une symétrie"
			+"\nhorizontale est tracé au fur et à mesure que vous avancez dans le labyrinthe. \n"
			+ "Il faut faire attention à ce que le joueur ne soit pas bloqué par la symétrie. \n"
			+ "Vous pouvez utilisez le 'Move Back' seulement 3 fois.\n";
	
	public static String aide = "Pour vous déplacer dans le labyrinthe, \n\n"
			+ "- vers le haut : ↑ ou W \n"
			+ "- vers la gauche : ← ou A \n"
			+ "- vers la droite : → ou D\n"
			+ "- vers le bas : ↓ ou S \n\n"
			+ "Vous pouvez revenir sur vos pas en appuyant sur Z ou le bouton 'Move back'\n"
			+ "ou relancer le labyrinthe en appuyant sur le bouton 'Relancer'. ";
	
	
	
	// Menu 
	private MenuTW menuBar = new MenuTW(0); 

	// Panneau jouer regroupant les niveaux et le bouton pour jouer 
	private JPanel pan = new JPanel();
	private String tabNiveaux[] = { "Facile", "Moyen", "Difficile", "Bonus" };
	private JComboBox<String> niveaux = new JComboBox<String>(tabNiveaux);
	private JButton b_jouer = new JButton("Jouer!");
	
	/* Constructeur 
	 * ajout d'une barre menu avec la classe MenuTW
	 * ajout d'une image avec la classe Piece
	 * ajout d'un outil pour sélectionner un niveau 
	 * ajout d'un bouton permettant de lancer le jeu selon le niveau choisis
	 * affichage des règles du jeu choisis
	 */
	
	public TheWitness() {

			this.setTitle("The Witness");
			this.setSize(800,350);
			this.setResizable(false);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLocationRelativeTo(null);
			this.setJMenuBar(menuBar);
			Piece t = new Piece(0,0,'T');this.add(t);
			
			pan.add(niveaux);
			pan.setBackground(Color.WHITE);
			b_jouer.addActionListener( new ActionListener() {
				@SuppressWarnings("unused")
				public void actionPerformed(ActionEvent arg0) {
					String s = (String) niveaux.getSelectedItem();
					
					if (s.compareTo("Bonus") == 0) {
						s = s+".txt";
						JOptionPane.showMessageDialog(null, TheWitness.reglesPuzzleSym, "The Witness : règles du jeu" ,JOptionPane.INFORMATION_MESSAGE);
						PuzzleSymetrique ps = new PuzzleSymetrique("fichiers_puzzle\\"+s);
					}
					else {
						s = s+".txt";
						JOptionPane.showMessageDialog(null, TheWitness.reglesPuzzle, "The Witness : règles du jeu" ,JOptionPane.INFORMATION_MESSAGE);
						Puzzle pz = new Puzzle("fichiers_puzzle\\"+s);
				}}}
			);
			pan.add(b_jouer);
			this.add(pan, BorderLayout.SOUTH);
			this.setVisible(true);

}
	
}
