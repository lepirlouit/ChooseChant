package domaine;

import java.util.ArrayList;
import java.util.List;

public class Chant {
	String titre;
	List<Couplet> couplets = new ArrayList<Couplet>();
	
	
	
	public Chant(String titre) {
		super();
		this.titre = titre;
	}
	
	
	public Chant(String titre, List<Couplet> couplets) {
		super();
		this.titre = titre;
		this.couplets = couplets;
	}


	public List<Couplet> getCouplets() {
		return couplets;
	}
	public void setCouplets(List<Couplet> couplets) {
		this.couplets = couplets;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}

}
