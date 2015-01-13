package settings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import domaine.Carnet;
import domaine.Chant;
import domaine.Couplet;
public class ChantSettings implements Serializable {
	transient private static ChantSettings instance;
	private static final long serialVersionUID = 5L;
	private Color cTitre, cContenu, traductionColor;
	transient private String sTitreEnCours, sContenuEnCours;
	transient private Couplet coupletEnCours;
	transient private Chant ChansonEnCours;
	private Font fTitre, fContenu, traductionFont;
	transient private List<Chant> listeChansons= Carnet.getInstance().getChants();
	transient private Vector<Image> imagesDesPhotos;
	private Dimension dimenionDeLEcran;
	private Point positionDeLEcran;
	private Color backgroundColor;
	private String backgroundImage;
	private boolean isImageBackgroundSet;
	transient private ArrayList<ActionListener> listeVues;
	transient private int alpha, alphaTitre, alphaContenu;
	private transient Image imageFromDiapo=null;

	private String lastOpenedFile;


	public Image getImageFromDiapo() {
		return imageFromDiapo;
	}

	public void setImageFromDiapo(Image imageFromDiapo) {
		this.imageFromDiapo = imageFromDiapo;
		traiterEvent(new ActionEvent(this, 0, "background image"));
	}

	public String getLastOpenedFile() {
		return lastOpenedFile;
	}

	public void setLastOpenedFile(String lastOpenedFile) {
		this.lastOpenedFile = lastOpenedFile;
	}

	public void loadLastOpenedFile() {
		if (this.getLastOpenedFile() != null) {
			this.chargerChansonsTxt(new File(this.getLastOpenedFile()));
		}
	}

	private ChantSettings() {
		cTitre = cContenu = Color.WHITE;

		fTitre = fContenu = new Font("arial", Font.ITALIC, 20);
		dimenionDeLEcran = new Dimension(640, 480);
		positionDeLEcran = new Point(0, 0);
		backgroundColor = Color.BLACK;
		backgroundImage = "";
		isImageBackgroundSet = false;

		initTransient();
		initDefault();


	}

	public Color getTraductionColor() {
		return traductionColor;
	}

	public void setTraductionColor(Color traductionColor) {
		this.traductionColor = traductionColor;
		traiterEvent(new ActionEvent(this, 0, "TraductionColor"));
	}

	public Font getTraductionFont() {
		return traductionFont;
	}

	public void setTraductionFont(Font traductionFont) {
		this.traductionFont = traductionFont;
		traiterEvent(new ActionEvent(this, 0, "TraductionFont"));
	}

	@SuppressWarnings("unchecked")
	public synchronized void addActionListener(ActionListener al) {
		ArrayList<ActionListener> liste = listeVues == null ? new ArrayList<ActionListener>(2)
				: (ArrayList<ActionListener>) listeVues.clone();
		if (!liste.contains(al)) {
			liste.add(al);
			listeVues = liste;
		}
	}

	/** notifie le(s) listener(s) */
	@SuppressWarnings("unchecked")
	public void traiterEvent(ActionEvent e) {
		ArrayList<ActionListener> list;
		synchronized (this) {
			if (listeVues == null) {
				return;
			}
			list = (ArrayList<ActionListener>) listeVues.clone();
		}
		for (int i = 0; i < list.size(); i++) {
			ActionListener listener = list.get(i);
			listener.actionPerformed(e);
		}
	}

	public void setTitre(String titre) {
		if(titre==null) {
			this.sTitreEnCours="";
		} else {
			this.sTitreEnCours = titre;
		}
		traiterEvent(new ActionEvent(this, 0, "Titre"));
	}

	public void setContenu(String contenu) {
		this.sContenuEnCours = contenu;
		traiterEvent(new ActionEvent(this, 0, "Contenu"));
	}

	public void setTitreFont(Font police) {
		fTitre = police;
		traiterEvent(new ActionEvent(this, 0, "TitreFont"));
	}

	public void setTitreColor(Color couleur) {
		cTitre = couleur;
		traiterEvent(new ActionEvent(this, 0, "TitreColor"));
	}

	public void setContenuFont(Font police) {
		fContenu = police;
		traiterEvent(new ActionEvent(this, 0, "ContenuFont"));
	}

	public void setContenuColor(Color couleure) {
		cContenu = couleure;
		traiterEvent(new ActionEvent(this, 0, "ContenuColor"));
	}

	public void setDimenionDeLEcran(Dimension dim) {
		this.dimenionDeLEcran = dim;
		traiterEvent(new ActionEvent(this, 0, "DimenionEcran"));
	}

	public void setPositionDeLEcran(Point point) {
		this.positionDeLEcran = point;
		traiterEvent(new ActionEvent(this, 0, "PositionEcran"));
	}

	public void setBackgroundColor(Color c) {
		backgroundColor = c;
		traiterEvent(new ActionEvent(this, 0, "background color"));
	}

	public void setBackgroundImage(String s) {
		backgroundImage = s;
		traiterEvent(new ActionEvent(this, 0, "background image"));
	}

	public void setIsImageBackgroundSet(boolean b) {
		isImageBackgroundSet = b;
		traiterEvent(new ActionEvent(this, 0, "is image set"));
	}

	public void setAlpha(int a) {
		this.alpha = a;
		traiterEvent(new ActionEvent(this, 0, "alpha"));
	}

	public int getAlphaTitre() {
		return alphaTitre;
	}

	public void setAlphaTitre(int alphaTitre) {
		this.alphaTitre = alphaTitre;
		traiterEvent(new ActionEvent(this, 0, "alphaTitre"));
	}

	public int getAlphaContenu() {
		return alphaContenu;
	}

	public void setAlphaContenu(int alphaContenu) {
		this.alphaContenu = alphaContenu;
		traiterEvent(new ActionEvent(this, 0, "alphaContenu"));
	}

	public Color getTitreColor() {
		return cTitre;

	}

	public Color getContenuColor() {
		return cContenu;
	}

	public Font getTitreFont() {
		return fTitre;
	}

	public Font getContenuFont() {
		return fContenu;
	}

	public String getTitreEnCours() {
		return sTitreEnCours;
	}

	public String getContenuEnCours() {
		return sContenuEnCours;
	}

	public List<Chant> getTitres() {
		return listeChansons;
	}

	public List<Chant> getTitres(String mot,boolean inCouplets) {
		List<Chant> maListe = new ArrayList<Chant>();
		for (Chant chant : getTitres()) {
			if(chant.getTitre().toLowerCase().contains(mot.toLowerCase())) {
				maListe.add(chant);
			}
			if (!maListe.contains(chant) && inCouplets) {
				for (Couplet couplet : chant.getCouplets()) {
					if(!maListe.contains(chant) && couplet.getContenu().toLowerCase().contains(mot.toLowerCase())) {
						maListe.add(chant);
					}
				}
			}
		}
		return maListe;
	}

	public List<Couplet> getCouplets(int nrTitre) {
		return listeChansons.get(nrTitre).getCouplets();
	}

	public String getContenu(int nrTitre, int nrCouplet) {
		return listeChansons.get(nrTitre).getCouplets().get(nrCouplet).getContenu();
	}

	public Dimension getDimenionDeLEcran() {
		return dimenionDeLEcran;
	}

	public Point getPositionDeLEcran() {
		return positionDeLEcran;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public int getAlpha() {
		return this.alpha;
	}

	public boolean isImageBackgroundSet() {
		return isImageBackgroundSet;
	}
	/*
	@SuppressWarnings("unchecked")
	public void chargerChansonsXML(File chemin) {
		try {
			String titre = "", intittul = "", couplet = "";

			List<Couplet> listeCouplets = null;

			Element carnetDeChants = new SAXBuilder().build(chemin).getRootElement();
			Iterator<Element> itCh = carnetDeChants.getChildren("chant").iterator();
			while (itCh.hasNext()){
				Element eltChant = itCh.next();
				titre=eltChant.getAttributeValue("titre");

				listeCouplets = new ArrayList<Couplet>();

				Iterator<Element> itCo = eltChant.getChildren("couplet").iterator();
				while (itCo.hasNext()){
					Element eltCouplet = itCo.next();
					couplet = new String("");
					intittul=eltCouplet.getAttributeValue("indic");

					Iterator<Element> itLi = eltCouplet.getChildren("ligne").iterator();
					while (itLi.hasNext()){
						Element eltLigne = itLi.next();
						couplet+= eltLigne.getText()+ "\n";
					}

					listeCouplets.add(new Couplet(intittul,couplet));

				}
				Chant c = new Chant(titre);
				listeChansons.add(c);
				c.setCouplets(listeCouplets);
			}
			traiterEvent(new ActionEvent(this, 0, "ChargementTermine"));



		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sauverChansonsXML(File chemin) {
		Element carnet = new Element("CarnetDeChant");
		for (Chant chant : listeChansons) {
			String titre = chant.getTitre();
			Element eltChant = new Element("chant");
			carnet.addContent(eltChant);
			eltChant.setAttribute(new Attribute("titre", titre));

			for (Couplet co:chant.getCouplets()) {
				Element eltCouplet = new Element("couplet");
				eltChant.addContent(eltCouplet);
				eltCouplet.setAttribute(new Attribute("indic", co.getIndic()));
				for (String line : co.getContenu().split("\n"))
					eltCouplet.addContent(new Element("ligne").setText(line));
			}
		}

		try {
			// On utilise ici un affichage classique avec getPrettyFormat()
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			// Remarquez qu'il suffit simplement de créer une instance de
			// FileOutputStream
			// avec en argument le nom du fichier pour effectuer la
			// sérialisation.
			sortie.output(new Document(carnet), new FileOutputStream(chemin));
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}*/
	public void sauverChansonsTxt(File chemin) {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter
					(new FileWriter(chemin)));
			pw.println("section	CarnetDeChant");

			for (Chant chant : listeChansons) {
				pw.println("chant\t"+chant.getTitre());
				for (Couplet co:chant.getCouplets()) {
					pw.println("ref-cpl\t"+ co.getIndic());
					for (String line : co.getContenu().split("\n")) {
						pw.println("\t"+line);
					}
				}
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public boolean chargerChansonsTxt(File chemin) {
		try {

			System.out.println(chemin.getName());
			this.setLastOpenedFile(chemin.getAbsolutePath());

			BufferedReader raf = new BufferedReader(
					new InputStreamReader(
							new DataInputStream(
									new FileInputStream(chemin)
									),
 "UTF-8")
					);

			String ligne = "", titre = "", intittul = "", couplet = "";
			Carnet.getInstance().setChants(new ArrayList<Chant>());
			listeChansons= Carnet.getInstance().getChants();

			List<Couplet> listeCouplet = new ArrayList<Couplet>();
			// exemple : "unhappy".substring(2) returns "happy"
			int i = 0;
			ligne = raf.readLine();
			if (!ligne.startsWith("section")) {
				JOptionPane
				.showMessageDialog(null,
						"Erreur : le Fichier selection ne commence pas par \"section\"");
			} else {
				while ((i++ < 2) && ((ligne = raf.readLine()) != null)) {
					if (ligne.startsWith("chant	")) {
						titre = ligne.substring(6);
					} else if (ligne.startsWith("ref-cpl	")) {
						intittul = ligne.substring(8);
					}
				}
				while ((ligne = raf.readLine()) != null) {
					if (ligne.startsWith("chant	")) {
						listeCouplet.add(new Couplet(intittul,couplet));
						listeChansons.add(new Chant(titre,listeCouplet));
						titre = ligne.substring(6);

						listeCouplet = new ArrayList<Couplet>();
						intittul = "";
						// rup du titre du chant
						// nouveau titre
						// nouveau contenu
					} else if (ligne.startsWith("ref-cpl	")) {
						// nouveau intitull
						// nouveau couplet
						// 1. stocker le couplet
						if (!intittul.equals("")) {
							listeCouplet.add(new Couplet(intittul,couplet));
						}
						// 2. reintialiser
						couplet = new String("");
						intittul = ligne.substring(8);
					} else if (!ligne.startsWith("#")) {
						// nouvelle ligne
						couplet += ligne.trim() + "\n";
					}

				}
				listeCouplet.add(new Couplet(intittul,couplet));
				listeChansons.add(new Chant(titre,listeCouplet));
				raf.close();
			}
			traiterEvent(new ActionEvent(this, 0, "ChargementTermine"));
			return true;

		} catch (IOException e) {
			System.out.println("erreur dans: " + e);
			return false;
		}

	}

	public void saveParam() {
		try {
			ObjectOutputStream fos = new ObjectOutputStream(
					new FileOutputStream("param.serial"));
			fos.writeObject(this);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static ChantSettings getInstance() {
		if(instance==null){
			try {
				ObjectInputStream fis = new ObjectInputStream(new FileInputStream(
						"param.serial"));
				instance = (ChantSettings) fis.readObject();
				fis.close();
				instance.initTransient();
			} catch (InvalidClassException e){
				System.out.println("Chanchement de version du serial du modèle : "+serialVersionUID);
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (instance==null){
				instance = new ChantSettings();
				instance.initDefault();
			}
		}
		return instance;
	}

	/*
	 * private static String formaterString(String s, int taille){ if
	 * (s.length()>taille){ return s.substring(0,taille); }else{ char c[] = new
	 * char[taille]; s.getChars(0, s.length(), c, 0); for(int i = s.length(); i <
	 * taille; i++){ c[i] = ' '; } return new String(c); }
	 *  }
	 */
	public Vector<Image> getImagesDesPhotos() {
		return imagesDesPhotos;
	}

	public void initDefault() {

		this.setTitreColor(Color.GREEN);
		this.setContenuColor(Color.WHITE);
		this.setTraductionColor(Color.YELLOW);
		Font f = new Font("SansSerif", Font.PLAIN, 20);
		this.setTitreFont(f);
		this.setContenuFont(f);
		this.setTraductionFont(new Font("SansSerif", Font.ITALIC, 20));
		this.setPositionDeLEcran(new Point(0, 0));
		this.setDimenionDeLEcran(new Dimension(640, 480));
	}
	public void initTransient() {
		listeChansons= Carnet.getInstance().getChants();
		sTitreEnCours = sContenuEnCours = "";
		alpha = 0;
		alphaTitre = 100;
		alphaContenu = 100;
		imagesDesPhotos = new Vector<Image>();
	}

	public void setCurrentlyEditingCouplet(Couplet couplet) {
		this.coupletEnCours=couplet;
	}
	public Couplet getCurrentlyEditingCouplet() {
		return this.coupletEnCours;
	}

	public void setChanson(Chant Chanson) {
		this.ChansonEnCours=Chanson;
	}
	public Chant getChanson() {
		return this.ChansonEnCours;
	}

}
