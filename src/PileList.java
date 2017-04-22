import java.util.ArrayList;

/** CLASSE PILELIST 
 * représente une pile d'entiers 
 * utilisation pour stocker : 
 * * * * les déplacements du joueur
 * * * * les types des piles spéciales
 * * * * le nombre de déplacements entre chaque case spéciales
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
	// retourne la valeur du deuxième élement de la pile 
	
	public void depiler() {
		
		liste.remove(liste.size()-1);
		
	}
	// supprime le premier élement de la pile 
	
	public int taille() {
		return liste.size();
	}
	// retourne le nombre d'élements dans la pile
	
	
	public boolean catchPossible (int typeCase) {
		if (!this.estVide()) {
			if (typeCase == this.sommet()) return false;
			else return true;
		}
		else return true;
	}
	/* lorsque le joueur passe par une case spéciale
	// on compare la valeur de cette case avec le sommet de la pile qui stocke les valeurs des cases
	// spéciales par lesquelles il est passé précedemment
	// retourne vrai si la case par laquelle il veut passer est différente de la dernière case par 
	// laquelle il est passé (le sommet de la pile)
	 */
	
}
