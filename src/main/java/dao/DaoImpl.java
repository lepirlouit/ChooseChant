package dao;

import java.util.List;

public abstract class DaoImpl<E> {
	abstract List<E> lister();
	abstract boolean ajouter(E elt);
	abstract boolean supprimer(E elt);
	
}
