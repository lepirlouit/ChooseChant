package domaine;

import java.util.ArrayList;
import java.util.List;



public class Carnet {
	List<Chant> chants = new ArrayList<Chant>();
	private static Carnet instance;
	private Carnet(){
	}

	public List<Chant> getChants() {
		return chants;
	}

	public void setChants(List<Chant> chants) {
		this.chants = chants;
	}
	
	public static Carnet getInstance() {
		if (instance ==  null)
			instance = new Carnet();
		return instance;
	}
	
}
