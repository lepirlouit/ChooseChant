package run;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import settings.ChantSettings;
import swingview.ChantEcran;
import swingview.ChantSelection;

/**
 * @version 2.0.5
 * @author lepirlouit
 * @idées : -15/02/2008: - case écran noir - case only background -21/02/2008: -
 *        interligne, espace entre titre et contenu
 * 
 * 
 */

public class ProjectChant extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1942219153505266354L;

	private final ChantSettings model;

	private final ChantEcran ecran;

	private final ChantSelection select;

	public ProjectChant() {
		this.model=ChantSettings.getInstance();

		ecran = new ChantEcran();
		select = new ChantSelection();
		ecran.setModel(model);
		select.setModel(model);
		// ecran.setVisible(true);

		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				model.saveParam();
				ecran.dispose();
				dispose();
				System.exit(0);
			}

			@Override
			public void windowIconified(WindowEvent e) {
				ecran.setVisible(false);
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				ecran.setVisible(true);
			}
		});
		setJMenuBar(barreDesMenus());
		setTitle("Projection de chants : CopyLeft ChooseLife : "
				+ ProjectChant.class.getPackage().getImplementationVersion());

		this.setSize(640, 480);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		Container co = getContentPane();
		co.add(select);
		// panneau interface


		// model.chargerChansons(new File("chantsnv.txt"));
		//		openedFile = new File("paroles/carnet_festival_choose_life_2008.txt");
		//		if(!model.chargerChansons(openedFile))
		//			model.chargerChansons(new File("carnet_festival_choose_life_2008.txt"));
		//
		//
		//		model.sauverChansonsXML(new File("paroles/carnet.xml"));
		//		model.chargerChansonsXML(new File("paroles/carnet.xml"));
		//		model.sauverChansonsXML(new File("paroles/carnet.xml"));
		model.loadLastOpenedFile();

		setVisible(true);

	}

	private JMenuBar barreDesMenus() {
		JMenuBar maBarre = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		maBarre.add(fichier);
		JMenuItem openTxt = new JMenuItem("Ouvrir fichier TXT");
		fichier.add(openTxt);
		openTxt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				fileOpenTxt();
			}
		});
		JMenuItem openXml = new JMenuItem("Ouvrir fichier XML");
		fichier.add(openXml);
		openXml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				fileOpenXml();
			}
		});
		fichier.addSeparator();
		JMenuItem saveSettings = new JMenuItem("Sauvegarde des parametres");
		fichier.add(saveSettings);
		saveSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				model.saveParam();
			}
		});
		JMenu aide = new JMenu("Aide");
		maBarre.add(aide);
		JMenuItem aProposDe = new JMenuItem("A propos de ...");
		aide.add(aProposDe);
		aProposDe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				JOptionPane
				.showMessageDialog(null,
						"<HTML><BODY>Ce programme réalisé par Benoît de Biolley, <br/>"
								+ "Benoit@deBiolley.be<br/>"
								+ "le code source est sous licence artistique Creative Commons<hr>"
								+ " changeLog : <br/>"
								+ "21/02/2008 :<ul><li>opacité titre/contenu</li>"
									+ "<li>tooltip sur les couplets</li>"
									+ "<li>éclaircir l'arrière plan</li>"
									+ "<li>curseur invisible sur l'écran</li></ul>"
								+ "06/03/2008 :<ul><li>suppression du bug des 25 % de l'opacité du background</li></ul>"
								+ "15/12/2008 :<ul><li>correction de divers bugs</li></ul>"
								+ "05/04/2014 :<ul><li>Ajout dans le menu : sauvegarde des parametres</li> "
										+ "<li>menu aide, avec à propos de.</li>"
										+ "<li>Force l'encodage d'ouverture à cp1252 </li>"
										+ "<li>Ajout du nr de version dans barre des titres.</li></ul>"
								+ "8/04/2014 :<ul><li>Force l'encodage d'ouverture à UTF-8</li></ul>"
								+ "</BODY></HTML>");
				;
			}
		});

		return maBarre;
	}

	private void fileOpenTxt() {
		JFileChooser fichierCh = new JFileChooser(model.getLastOpenedFile());
		fichierCh.setAcceptAllFileFilterUsed(false);

		fichierCh.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 && i < s.length() - 1) {
					if (s.substring(i + 1).toLowerCase().equals("txt")) {
						return true;
					}
				}

				return false;
			}

			@Override
			public String getDescription() {
				return "Fichiers texte (chant ... & ref-cpl) (*.txt)";
			}

		});

		int returnVal = fichierCh.showOpenDialog(this);

		if(returnVal == JFileChooser.APPROVE_OPTION){
			model.chargerChansonsTxt(fichierCh.getSelectedFile());
		}
	}

	private void fileOpenXml() {
		JFileChooser fichierCh = new JFileChooser(model.getLastOpenedFile());
		fichierCh.setAcceptAllFileFilterUsed(false);

		fichierCh.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String s = f.getName();
				int i = s.lastIndexOf('.');

				if (i > 0 && i < s.length() - 1) {
					if (s.substring(i + 1).toLowerCase().equals("xml")) {
						return true;
					}
				}

				return false;
			}

			@Override
			public String getDescription() {
				return "Fichiers xml (<chant> <ref-cpl>) (*.xml)";
			}

		});
		//		int returnVal = fichierCh.showOpenDialog(this);
		/*
		if(returnVal == JFileChooser.APPROVE_OPTION){
			model.chargerChansonsXML(fichierCh.getSelectedFile());
		}*/
	}

	public static void main(String[] args) {
		ProjectChant frame = new ProjectChant();
		// frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//frame.setTitle("MVCDemo");
		// frame.pack( );
		frame.setVisible(true);
	}
}
