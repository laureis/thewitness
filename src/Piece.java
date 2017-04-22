import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/** CLASSE PIECE
 * représente les différentes pièces du puzzle/labyrinthe
 */

@SuppressWarnings("serial")
public class Piece extends JPanel {
	
	Color c = new Color(192,192,192);
	private Image backgroundImage;
	// M(mur), C(chemin), A(arrivée), D(départ), X(case couleur 1), Y(case couleur 2), J(joueur) pour le jeu
	// T pour le menu principal
	private char type; 
	private int pos_x;
	private int pos_y;
	private boolean est_accessible;
	private int est_speciale; // -1 si la case n'est pas spéciale, 1 pour le type 1 et 2 pour le type 2
	public static int taillePiece = 40;

	/* CONSTRUCTEUR 
	 * crée un nouveau JPanel avec une image selon le type de la pièce du puzzle : 
	 * C : chemin (accessible par défaut)
	 * M : mur (inaccessible)
	 * X : le premier type de case coloré (accessible par défaut
	 * Y : le deuxième type de case coloré (accessible par défaut)
	 * A : la case arrivée du labyrinthe (accessible)
	 * D : la case départ du labyrinthe (accessible par défaut)
	 * J : la case du joueur
	 * T : pour le design du menu principal du jeu
	 */

	
	public Piece (int x, int y, char typePiece) {
	
		this.pos_x = x;
		this.pos_y = y;
		this.setBackground(Color.WHITE);
		switch (typePiece) {
		
			case 'J' : {
				
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\black-dot.png"));
					this.setBackground(Color.WHITE);
				} catch (IOException e) {
					this.setBackground(Color.BLACK);
				}
				this.setSize(Puzzle.taillePiece,Puzzle.taillePiece);
				this.setLocation(pos_x * Puzzle.taillePiece, pos_y * Puzzle.taillePiece);
			}
		
			case 'M' : {
		
				this.est_accessible = false;
				this.est_speciale = -1;
				this.setSize(Puzzle.taillePiece,Puzzle.taillePiece);
				this.setLocation(pos_x * Puzzle.taillePiece, pos_y * Puzzle.taillePiece);
				break; 
			}
				
			case 'C' : {
			
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\white-dot.png"));
				} catch (IOException e) {
					this.setBackground(Color.GRAY);
				}
				this.est_accessible = true;
				this.est_speciale = -1;
				this.setSize(taillePiece,taillePiece);
				this.setLocation(this.pos_x * taillePiece, this.pos_y * taillePiece);
				break;
			}
		
			case 'X' : {
			
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\blue-dot.png"));
				} catch (IOException e) {
					this.setBackground(Color.BLUE);
				}
				this.est_accessible = true;
				this.est_speciale = 1;
				this.setSize(taillePiece,taillePiece);
				this.setLocation(this.pos_x * taillePiece, this.pos_y * taillePiece);
				break;
			}
			
			case 'Y' : {
			
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\red-dot.png"));
				} catch (IOException e) {
					this.setBackground(Color.RED);
				}
				this.est_accessible = true;
				this.est_speciale = 2;
				this.setSize(taillePiece,taillePiece);
				this.setLocation(this.pos_x * taillePiece, this.pos_y * taillePiece);
				break;
			}
			
			case 'A' : {
			
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\cible.png"));
				} catch (IOException e) {
					this.setBackground(c);
				}
				this.est_accessible = true;
				this.est_speciale = -1;
				this.setSize(taillePiece,taillePiece);
				this.setLocation(this.pos_x * taillePiece, this.pos_y * taillePiece);
				break;
			}
			
			case 'D' : {
			
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\white-dot-tr.png"));
				} catch (IOException e) {
					this.setBackground(Color.BLACK);
				}
				this.est_accessible = true;
				this.est_speciale = -1;
				this.setSize(taillePiece,taillePiece);
				this.setLocation(this.pos_x * taillePiece, this.pos_y * taillePiece);
				break;
			}
			
			case 'T' : {
				
				this.setSize(1144, 274);
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\title.png"));
				} catch (IOException e) {
					this.setBackground(Color.BLUE);
				}
				this.setLocation(pos_x, pos_y);
				break;
				}
			
		}
	
	
	}

	public boolean getAcess(){
	
		return this.est_accessible;
		}
	// retourne vrai si la case est accessible

	public void setAccess(boolean access)  {
			this.est_accessible = access; 
			if (access == false) {	
				try {
					backgroundImage = ImageIO.read(new File("icons-projet\\white-dot-tr.png"));
				} catch (IOException e) {
						this.setBackground(Color.BLACK);
				}
			}
			else {
				if (this.est_speciale == 1) {
					try {
						backgroundImage = ImageIO.read(new File("icons-projet\\blue-dot.png"));
					} catch (IOException e) {
						this.setBackground(Color.BLUE);
					}
				}
				else if (this.est_speciale == 2) {
					try {
						backgroundImage = ImageIO.read(new File("icons-projet\\red-dot.png"));
					} catch (IOException e) {
						this.setBackground(Color.RED);
					}
				} else
					try {
						backgroundImage = ImageIO.read(new File("icons-projet\\white-dot.png"));
					} catch (IOException e) {
						this.setBackground(Color.GRAY);
					}
			}
		}
	// rend une case inaccessible ou lui retourne ses caractéristiques par défaut 
	
	public boolean estSpeciale() {
		
		return (this.est_speciale==1) || (this.est_speciale==2);
	}
	// retourne vrai si une case est spéciale (colorée)
	
	public int getSpeciale() {
		
		return this.est_speciale;
	}
	// retourne le type de la case spéciale
	
	public char getType() {
		return this.type;
	}
	// retourne le type d'une case (mur, chemin, etc..)

	 public void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    g.drawImage(backgroundImage, 0, 0, this);
		    
	}
	// ajoute une image de fond à l'objet
	 
}