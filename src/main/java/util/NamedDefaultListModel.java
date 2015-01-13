package util;
import javax.swing.DefaultListModel;

public class NamedDefaultListModel extends DefaultListModel {
	private static final long serialVersionUID = 4984285480508059862L;

	private String titre;

	public NamedDefaultListModel(String titre) {
		this.titre = titre;
	}

	public NamedDefaultListModel() {
		super();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
}
