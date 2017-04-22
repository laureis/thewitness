import java.util.ArrayList;

/** CLASSE PILELIST 
 * repr�sente une pile d'entiers 
 * utilisation pour stocker : 
 * * * * les d�placements du joueur
 * * * * les types des piles sp�ciales
 * * * * le nombre de d�placements entre chaque case sp�ciales
 */

public class PileList {

	private ArrayList<Integer> liste= new ArrayList<Integer>();
	
	public boolean estVide() {
		return liste.isEmpty();
	}
	// retourne vrai si la pile est vide
	

	public void empiler(int d) {
		liste.add(d);
	}
	// ajouter l'entier d au sommet de la pile

	public int sommet() {
		
		return liste.get(liste.size()-1);
	}
	// retourne la valeur du sommet de la pile
	
	public int element2() {
		
		return liste.get(liste.size()-2);
	}
	// retourne la valeur du deuxi�me �lement de la pile 
	
	public void depiler() {
		
		liste.remove(liste.size()-1);
		
	}
	// supprime le premier �lement de la pile 
	
	public int taille() {
		return liste.size();
	}
	// retourne le nombre d'�lements dans la pile
	
	
	public boolean catchPossible (int typeCase) {
		if (!this.estVide()) {
			if (typeCase == this.sommet()) return false;
			else return true;
		}
		else return true;
	}
	/* lorsque le joueur passe par une case sp�ciale
	// on compare la valeur de cette case avec le sommet de la pile qui stocke les valeurs des cases
	// sp�ciales par lesquelles il est pass� pr�cedemment
	// retourne vrai si la case par laquelle il veut passer est diff�rente de la derni�re case par 
	// laquelle il est pass� (le sommet de la pile)
	 */
	
}
