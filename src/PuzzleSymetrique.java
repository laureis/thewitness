import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class PuzzleSymetrique extends Puzzle {

	private int nb_MoveBack;
	private int pos_sym_x;
	private int pos_sym_y;
	private Piece sym;
	
	public PuzzleSymetrique(String nomFichier) {
		
		super(nomFichier);
		// Ajout de la symétrie 
		this.type = 2;
		this.nb_MoveBack = 0;
		this.pos_sym_x = this.pos_Depart_x;
		this.pos_sym_y = this.pos_Depart_y;
		sym = new Piece (this.pos_sym_x, this.pos_sym_y, 'J');
		this.add(sym);

	}


public void deplacementHaut() {
	
	super.deplacementHaut();
	sym.setLocation(sym.getX(), sym.getY()+Puzzle.taillePiece);
	this.pos_sym_y++;
	
}

public void deplacementBas() {
	
	super.deplacementBas();
	sym.setLocation(sym.getX(), sym.getY()-Puzzle.taillePiece);
	this.pos_sym_y--;
}

public void deplacementGauche() {
	
	super.deplacementGauche();
	sym.setLocation(sym.getX()-Puzzle.taillePiece, sym.getY());
	this.pos_sym_x--;
}

public void deplacementDroite() {
	
	super.deplacementDroite();
	sym.setLocation(sym.getX()+Puzzle.taillePiece, sym.getY());
	this.pos_sym_x++;

}

public boolean estBloque() {

	return super.estBloque()  && ((!this.tabPieces[this.pos_sym_x][this.pos_sym_y-1].getAcess()) 
		&& (!this.tabPieces[this.pos_sym_x+1][this.pos_sym_y].getAcess()) 
		&& (!this.tabPieces[this.pos_sym_x][this.pos_sym_y+1].getAcess()) 
		&& (!this.tabPieces[this.pos_sym_x-1][this.pos_sym_y].getAcess()) );
		
}

public void moveBack() {
	
	if ((!this.pile_deplacements.estVide())){
		
		this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(true);
		this.tabPieces[this.pos_sym_x][this.pos_sym_y].setAccess(true);
		int dep = this.pile_deplacements.sommet();
		this.pile_deplacements.depiler();
		this.nb_MoveBack ++;
		if (this.nb_MoveBack >= 3) this.b_moveback.setEnabled(false);
		switch (dep) {
	
			case 1 : { this.deplacementBas(); break; }
			case 2 : { this.deplacementHaut(); break; }
			case 3 : { this.deplacementDroite(); break; }
			case 4 : { this.deplacementGauche(); break; }
		}
		
	}
}

public void keyPressed(KeyEvent e) {
	
	
			
	if ((e.getKeyCode() == KeyEvent.VK_UP)||(e.getKeyCode() == KeyEvent.VK_W)) {
	
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");

		else if ((this.pos_joueur_y > 0) && (this.tabPieces[this.pos_joueur_x][this.pos_joueur_y-1].getAcess())
				&& (this.pos_sym_y < this.nbLignes-1) && (this.tabPieces[this.pos_sym_x][this.pos_sym_y+1].getAcess())) {
			
			this.deplacementHaut();
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.tabPieces[this.pos_sym_x][this.pos_sym_y].setAccess(false);
			this.pile_deplacements.empiler(1);
			this.messageArrivee();
		}
		
	}
			
	if ((e.getKeyCode() == KeyEvent.VK_LEFT)||(e.getKeyCode() == KeyEvent.VK_A)) {
		
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");
		
		else if ((this.pos_joueur_x > 0) && (this.tabPieces[this.pos_joueur_x-1][this.pos_joueur_y].getAcess())
				&& (this.pos_sym_x > 0) && (this.tabPieces[this.pos_sym_x-1][this.pos_sym_y].getAcess())) {
			
			this.deplacementGauche();
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.tabPieces[this.pos_sym_x][this.pos_sym_y].setAccess(false);
			this.pile_deplacements.empiler(3);
			this.messageArrivee();
				}
	}
			
			
	if ((e.getKeyCode() == KeyEvent.VK_RIGHT)||(e.getKeyCode() == KeyEvent.VK_D)) {
			
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");
		else if ((this.pos_joueur_x < this.nbColonnes-1) && (this.tabPieces[this.pos_joueur_x+1][this.pos_joueur_y].getAcess())
				&& (this.pos_sym_x < this.nbColonnes-1) && (this.tabPieces[this.pos_sym_x+1][this.pos_sym_y].getAcess())) {
			
			this.deplacementDroite();
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.tabPieces[this.pos_sym_x][this.pos_sym_y].setAccess(false);
			this.pile_deplacements.empiler(4);
			this.messageArrivee();
				}
			}
			
	if ((e.getKeyCode() == KeyEvent.VK_DOWN)||(e.getKeyCode() == KeyEvent.VK_S)) {
	
		if (estBloque()) perdu("Pas de chemins accessibles, vous êtes bloqués! ");
		
		if ((this.pos_joueur_y < this.nbLignes-1) && (this.tabPieces[this.pos_joueur_x][this.pos_joueur_y+1].getAcess())
			&& (this.pos_sym_y > 0) && (this.tabPieces[this.pos_sym_x][this.pos_sym_y-1].getAcess())) {
			
			this.deplacementBas();
			this.tabPieces[this.pos_joueur_x][this.pos_joueur_y].setAccess(false);
			this.tabPieces[this.pos_sym_x][this.pos_sym_y].setAccess(false);
			this.pile_deplacements.empiler(2);
			
			this.messageArrivee();
		}
	}
	
	if (e.getKeyCode() == KeyEvent.VK_Z) {
		if (nb_MoveBack < 3) this.moveBack();
	}
			
}

}
