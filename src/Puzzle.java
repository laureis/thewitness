import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/** CLASSE PUZZLE
 * représente le puzzle où le joueur pourra se déplacer
 *
 */


@SuppressWarnings("serial")
public class Puzzle extends JFrame implements KeyListener {
	
	protected int type; // 0 pour puzzle normal, autre pour puzzle symétrique 
	private MenuTW menuBar; // barre de menu 
	
	/* Boutons utiles pour le jeu 
	 * Relancer pour remettre à zéro le labyrinthe
	 * Move Back pour revenir d'un déplacement en arrière 
	 */
	private JButton b_relancer = new JButton (" Relancer ");
	protected JButton b_moveback = new JButton(" Move back ");
	private JPanel p_boutons = new JPanel();
	
	// Caractéristiques de la pièce joueur
		protected Piece joueur;
		protected int pos_joueur_x;
		protected int pos_joueur_y;
		
		
	/* Caractéristiques propres au puzzle
	 * informations lues dans le fichier texte représentant le puzzle
	 */
	protected int nbLignes; 
	protected int nbColonnes;   
	static int taillePiece = 40;
	private int nb_cases_speciales;
	protected int pos_Depart_x;
	protected int pos_Depart_y;
	protected int pos_Arrivee_x;
	protected int pos_Arrivee_y;
	protected Piece[][] tabPieces = new Piece[100][100]; 
	
	
	// Suivi des déplacements du joueur 
	private boolean premiere_couleur; // mis à vrai lors du premier passage par une case spéciale du puzzle
	private int cases_speciales_crt; // compte le nombre de cases spéciales par lequel passe le joueur
	private PileList pile_cases_speciales = new PileList(); // stocke le type de chaque pièce colorée par lequel passe le joueur
	protected PileList pile_deplacements = new PileList(); // stocke chaque déplacement du joueur (1:haut,2:bas,3:gauche,4:droite)
	private PileList pile_nbCasesCrt = new PileList(); // stocke le nombre de déplacements entre chaque cases colorée (incrémentation)

	
/* Constructeur 
 *  ajout d'une barre menu avec la classe MenuTW
 *  ajout des boutons relancer et move back
 *  construction du puzzle avec la classe Piece
 *  valeurs par défaut des champs suivant le déplacement du joueur 
 */
public Puzzle(String nomFichier) {

	
	try {
		lirePuzzle(nomFichier);
	} catch (IOException e1) {
	}	
	this.setTitle("The Witness");
	this.setSize((this.nbColonnes) * Puzzle.taillePiece +5,(this.nbLignes) * Puzzle.taillePiece + 90 );
	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	this.setLocationRelativeTo(null);
	this.setResizable(false);
	this.type = 1;
	this.addKeyListener(this);
	menuBar = new MenuTW(this.type);
	this.setJMenuBar(menuBar);

	// Boutons 
	
	p_boutons.setSize(100,200);
	p_boutons.setBackground(Color.WHITE);
	b_relancer.addActionListener( new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				
				dispose();
				@SuppressWarnings("unused")
				Puzzle p;
				if (type == 1) p = new Puzzle(nomFichier);
				else p = new PuzzleSymetrique(nomFichier);
				
			
	}});
	b_relancer.setFocusable(false);
	p_boutons.add(b_relancer);
	b_moveback.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				moveBack();
			}
	});
	b_moveback.setFocusable(false);
	p_boutons.add(b_moveback);
	this.add(p_boutons, BorderLayout.SOUTH); 
		

	// Ajout du joueur et initialisation

	this.cases_speciales_crt = 0;
	this.pile_nbCasesCrt.empiler(0);
	this.pos_joueur_x = this.pos_Depart_x;
	this.pos_joueur_y = this.pos_Depart_y;
	this.joueur = new Piece (this.pos_Depart_x, this.pos_Depart_y, 'J');
	this.add(this.joueur);
	this.tabPieces[this.pos_Depart_x][this.pos_Depart_y].setAccess(false);
	this.premiere_couleur = false;
	
	// Ajout des pièces au puzzle 

	for (int i=0; i<this.nbColonnes; i++) {
		for (int j=0; j<this.nbLignes; j++) {
			
			this.add(this.tabPieces[i][j]);
		}
	}
	

	this.setVisible(true);
	
	}
	
	

public void lirePuzzle (String nomFichier) throws IOException {
	
		int cases_speciales = 0;
		BufferedReader br = null;
		try {
			int noLigne = 0;
			InputStream ips = new FileInputStream(nomFichier); 
			InputStreamReader ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			String ligne = null;
			while ((ligne=br.readLine())!=null) {
				ligne = ligne.trim();
				String[] tmp = ligne.split("\\s+");
				this.nbColonnes = tmp.length;
				for (int i=0; i<tmp.length; i++) {
					this.tabPieces[i][noLigne] = new Piece(i, noLigne, tmp[i].charAt(0));
					if (tmp[i].charAt(0)=='D') {
			
						this.pos_Depart_x = i;
						this.pos_Depart_y = noLigne;
					}
		
					else if (tmp[i].charAt(0)=='A') {
		
						this.pos_Arrivee_x = i;
						this.pos_Arrivee_y = noLigne;
					}
					
					else if ((tmp[i].charAt(0)=='X')||(tmp[i].charAt(0)=='Y')) {
						cases_speciales++;
					}
				}
				noLigne++;
				
			}
			
			this.nb_cases_speciales = cases_speciales;
			this.nbLignes = noLigne;
			
		} catch (FileNotFoundException e) {
		
			
			String tab[] = { "M M M M M M M M M M M M M M M M M",
					"M M C C C C C C C C C C C C D M M",
					"M M C M C M C M M C M M C M C M M",
					"M M C M C M C M M C M M X M C M M",
					"M M C M C M C M M C M M C M C M M",
					"M M C M C M X C C Y M M C M C M M",
					"M M C M C M C M M C M M C M C M M",
					"M M C M C M Y C C C X C Y M C M M",
					"M M C C X C C M M M M M C M C M M",
					"M M Y M C M C M M M M M C M C M M",
					"M M C M C C C C C C C C C M C M M",
					"M M C M M M C M M M M M C M C M M",
					"M M A C C C C C C C C C C C C M M",
					"M M M M M M M M M M M M M M M M M",
					"M M M M M M M M M M M M M M M M M" };
			int noLigne = 0;
			for (int j=0; j<tab.length; j++) {
				String ligne = tab[j].trim();
				String[] tmp = ligne.split("\\s+");
				this.nbColonnes = tmp.length;
				for (int i=0; i<tmp.length; i++) {
					this.tabPieces[i][noLigne] = new Piece(i, noLigne, tmp[i].charAt(0));
					if (tmp[i].charAt(0)=='D') {
			
						this.pos_Depart_x = i;
						this.pos_Depart_y = noLigne;
					}
		
					else if (tmp[i].charAt(0)=='A') {
		
						this.pos_Arrivee_x = i;
						this.pos_Arrivee_y = noLigne;
					}
					
					else if ((tmp[i].charAt(0)=='X')||(tmp[i].charAt(0)=='Y')) {
						cases_speciales++;
					}
				}
				noLigne++;
				
			}
			this.nb_cases_speciales = cases_speciales;
			this.nbLignes = noLigne;
			
		}
		finally {
			if (br!=null) br.close();
		}
	}
// lecture d'un fichier texte où sont stockés des caractères (M : mur, C : chemin, A : arrivée, D : départ)
// et stockage dans une matrice de Piece 
	

public boolean estBloque() {

	return ((!this.tabPieces[this.pos_joueur_x][this.pos_joueur_y-1].getAcess()) 
		&& (!this.tabPieces[this.pos_joueur_x+1][this.pos_joueur_y].getAcess()) 
		&& (!this.tabPieces[this.pos_joueur_x][this.pos_joueur_y+1].getAcess()) 
		&& (!this.tabPieces[this.pos_joueur_x-1][this.pos_joueur_y].getAcess()) );
		
}
// retourne vrai si le joueur est bloqué = si toutes les cases autour de lui sont inaccessibles (access = false)
// il faut que le labyrinthe soit entouré de mur pour éviter les erreurs


// méthodes modifiant la position du joueur selon le déplacement voulu par l'utilisateur
public void deplacementHaut() {

	joueur.setLocation(joueur.getX(), joueur.getY()-Puzzle.taillePiece);
	this.pos_joueur_y--;
	
}

public void deplacementBas() {

	joueur.setLocation(joueur.getX(), joueur.getY()+Puzzle.taillePiece);
	this.pos_joueur_y++;
}

public void deplacementGauche() {

	joueur.setLocation(joueur.getX()-Puzzle.taillePiece, joueur.getY());
	this.pos_joueur_x--;
}

public void deplacementDroite() {
	
	joueur.setLocation(joueur.getX()+Puzzle.taillePiece, joueur.getY());
	this.pos_joueur_x++;

}

public void moveBack() {
	
	if (!this.pile_deplacements.estVide()){
		
		if (this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].estSpeciale()) {
			this.pile_cases_speciales.depiler();
			cases_speciales_crt--;
			if (this.pile_cases_speciales.estVide()) this.premiere_couleur = false;
		}
		int dep = this.pile_deplacements.sommet();
		this.pile_deplacements.depiler();
		
		switch (dep) {
	
			case 1 : { this.deplacementBas(); break; }
			case 2 : { this.deplacementHaut(); break; }
			case 3 : { this.deplacementDroite(); break; }
			case 4 : { this.deplacementGauche(); break; }
		}
		this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(true);
		this.pile_nbCasesCrt.depiler();
	}
}
// permet de revenir un déplacement en arrière avec modifications des piles et variables 

public void attraperCouleur() {

	if (tabPieces[pos_joueur_x][pos_joueur_y].estSpeciale()) {
				
				if (!this.premiere_couleur) this.premiere_couleur = true;
				if (pile_cases_speciales.catchPossible(tabPieces[pos_joueur_x][pos_joueur_y].getSpeciale())) {
					
					this.pile_nbCasesCrt.empiler(0);
					this.pile_cases_speciales.empiler(tabPieces[pos_joueur_x][pos_joueur_y].getSpeciale());
					cases_speciales_crt++;
				}
				
				else perdu("Impossible d'attraper cette case!");
				
			}
			else {
			
				this.pile_nbCasesCrt.empiler(this.pile_nbCasesCrt.sommet()+1);
			}

}
// si le joueur est sur une case de couleur, modification des piles et variables

public void perdu(String raison)  {

	JOptionPane.showMessageDialog(null, raison, "Perdu" ,JOptionPane.INFORMATION_MESSAGE);
	dispose();
	
}
// permet d'afficher un message 'perdu' si le joueur est bloqué, ou s'il n'a pas respecré les contraintes à l'arrivée

public void messageArrivee() {

	if ((this.pos_joueur_y == this.pos_Arrivee_y)&&(this.pos_joueur_x == this.pos_Arrivee_x)) {
						
		if (cases_speciales_crt == nb_cases_speciales) {

			JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Bravo" ,JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		else perdu("Vous n'êtes pas passé par toutes les cases de couleur.");
	}
}
// si le joueur est sur la case arrivé, affiche un message 'gagné' (ou perdu s'il n'a pas respecté les contraintes)


/* Méthode du Key Listener 
 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
 * si le joueur appuie sur la touche haut(ou W), bas(ou S), gauche(ou A) ou droite (ou D)
 * on effectue le déplacement seulement si c'est possible
 * un déplacement en peut pas être effectué si le joueur fait plus de 4 déplacements entre deux cases colorées
 * si le joueur appuie sur le touche Z, un move back est lancé 
 */
public void keyPressed(KeyEvent e) {
			
	if ((e.getKeyCode() == KeyEvent.VK_UP)||(e.getKeyCode() == KeyEvent.VK_W)) {
	
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");
		
		else if ((this.pile_nbCasesCrt.sommet()> 4) && (this.premiere_couleur) ) perdu("Vous avez fait plus que 4 déplacements.");

		else if ((this.pos_joueur_y > 0) && (this.tabPieces[this.pos_joueur_x][this.pos_joueur_y-1].getAcess())) {
		
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.pile_deplacements.empiler(1);
			this.deplacementHaut();
			this.attraperCouleur();
			this.messageArrivee();
		}
	}
			
	if ((e.getKeyCode() == KeyEvent.VK_LEFT)||(e.getKeyCode() == KeyEvent.VK_A)) {
		
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");

		else if ((this.pile_nbCasesCrt.sommet()> 4) && (this.premiere_couleur) ) perdu("Vous avez fait plus que 4 déplacements.");
		
		else if ((this.pos_joueur_x > 0) && (this.tabPieces[this.pos_joueur_x-1][this.pos_joueur_y].getAcess())) {
		
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.pile_deplacements.empiler(3);
			this.deplacementGauche();
			this.attraperCouleur();
			this.messageArrivee();
				}
	}
			
			
	if ((e.getKeyCode() == KeyEvent.VK_RIGHT)||(e.getKeyCode() == KeyEvent.VK_D)) {
			
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");
		
		else if ((this.pile_nbCasesCrt.sommet()> 4) && (this.premiere_couleur) ) perdu("Vous avez fait plus que 4 déplacements.");

		else if ((this.pos_joueur_x < this.nbColonnes-1) && (this.tabPieces[this.pos_joueur_x+1][this.pos_joueur_y].getAcess())) {
			
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.pile_deplacements.empiler(4);
			this.deplacementDroite();
			this.attraperCouleur();
			this.messageArrivee();
				}
			}
			
	if ((e.getKeyCode() == KeyEvent.VK_DOWN)||(e.getKeyCode() == KeyEvent.VK_S)) {
	
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");

		else if ((this.pile_nbCasesCrt.sommet()> 4) && (this.premiere_couleur) ) perdu("Vous avez fait plus que 4 déplacements.");

		if ((this.pos_joueur_y < this.nbLignes-1) && (this.tabPieces[this.pos_joueur_x][this.pos_joueur_y+1].getAcess())) {
		
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.pile_deplacements.empiler(2);
			this.deplacementBas();
			this.attraperCouleur();
			this.messageArrivee();
		}
	}
	
	if (e.getKeyCode() == KeyEvent.VK_Z) {
		
		this.moveBack();
	}
			
}
	

public void keyReleased(KeyEvent e) {
			
}

	
public void keyTyped(KeyEvent e) {
		
}
	
	
}
	