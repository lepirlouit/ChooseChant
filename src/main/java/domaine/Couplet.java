package domaine;

public class Couplet {
	String indic;
	String contenu;
	
	
	public Couplet(String indic, String contenu) {
		super();
		this.indic = indic;
		this.contenu = contenu;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getIndic() {
		return indic;
	}
	public void setIndic(String indic) {
		this.indic = indic;
	}
	

}
