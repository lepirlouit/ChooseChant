package swingview;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import settings.ChantSettings;
import swingview.diapo.ListPhotosDiaporama;
import util.JFontChooser;
import util.MyUtils;

public class ChantParam extends JPanel implements ActionListener,
		ChangeListener {

	// définition

	/**
	 * 
	 */
	private static final long serialVersionUID = -8280548254591867259L;

	private ChantSettings model;

	private JPanel JPDimPos, JPBackground, JPFontColor;

	private ListPhotosDiaporama lpd;

	private JLabel JLX, JLY, JLLargeur, JLHauteur, JLCBackground,
			JLIBackground;

	private JTextField JTFX, JTFY, JTFLargeur, JTFHauteur, JTFCheminImage;

	private JButton JBAjust, JBBGColor, JBParImage, JBTitre, JBCouplet,JBTraduction, jbDia;

	private JCheckBox JCBAfficherImage;

	private JSlider JSAlpha;
	private JSlider JSAlphaTitre;
	private JSlider JSAlphaContenu;
	
	private JCheckBox JCBLink;

	public ChantParam() {
		// instanciation
		JPDimPos = new JPanel();
		JPDimPos.setBorder(new TitledBorder("Position / Taille de l'écran"));

		JPBackground = new JPanel();
		JPBackground.setBorder(new TitledBorder(
				"Couleur et Image d'arrière plan"));

		JPFontColor = new JPanel();
		JPFontColor.setBorder(new TitledBorder(
				"Police et Couleur du titre et du contenu"));

		JBTitre = new JButton("Titre");
		JBCouplet = new JButton("Contenu");
		JBTraduction = new JButton("Traduction");
		JSAlphaTitre = new JSlider(0, 100, 100);
		JCBLink = new JCheckBox();
		JSAlphaContenu = new JSlider(0, 100, 100);

		JLX = new JLabel("x :");
		JLY = new JLabel("y :");
		JLLargeur = new JLabel("largeur :");
		JLHauteur = new JLabel("hauteur :");
		JLCBackground = new JLabel("Couleur d'arrière plan :");
		JLIBackground = new JLabel("Image d'arrière plan :");
		JTFX = new JTextField();
		JTFY = new JTextField();
		JTFLargeur = new JTextField();
		JTFHauteur = new JTextField();
		JTFCheminImage = new JTextField();
		JBAjust = new JButton("Ajust");
		JBBGColor = new JButton();
		JSAlpha = new JSlider(-100, 100, 0);
		JBParImage = new JButton("Parcourir");
		JCBAfficherImage = new JCheckBox();
		JTFCheminImage = new JTextField("");
		jbDia = new JButton(MyUtils.tovertical("Dia"));

		// setBounds
		JPFontColor.setBounds(0, 140, 310, 70);
		JBTitre.setBounds(10, 15, 90, 20);
		JBCouplet.setBounds(10, 40, 90, 20);
		JBTraduction.setBounds(205, 40, 95, 20);
		JSAlphaTitre.setBounds(105, 15, 90, 20);
		JCBLink.setBounds(140,30,20,20);
		JSAlphaContenu.setBounds(105, 45, 90, 20);


		JPDimPos.setBounds(0, 0, 310, 60);
		JLX.setBounds(10, 15, 15, 20);
		JTFX.setBounds(25, 15, 40, 20);
		JLY.setBounds(10, 35, 15, 20);
		JTFY.setBounds(25, 35, 40, 20);
		JLLargeur.setBounds(70, 15, 60, 20);
		JTFLargeur.setBounds(125, 15, 40, 20);
		JLHauteur.setBounds(70, 35, 60, 20);
		JTFHauteur.setBounds(125, 35, 40, 20);
		JBAjust.setBounds(180, 15, 120, 40);

		JPBackground.setBounds(0, 60, 310, 80);
		JLCBackground.setBounds(10, 15, 135, 20);
		JSAlpha.setBounds(170, 35, 110, 20);/* 5465465 */
		jbDia.setBounds(285, 10, 20, 45);

		jbDia.setMargin(new Insets(0, 0, 0, 0));

		JLIBackground.setBounds(10, 35, 135, 20);
		JCBAfficherImage.setBounds(145, 35, 20, 20);
		JBBGColor.setBounds(145, 15, 50, 20);
		JBParImage.setBounds(200, 15, 80, 20);/* 4564654 */
		JTFCheminImage.setBounds(10, 55, 290, 20);
		
		// addActionListeners
		JBAjust.addActionListener(this);
		JBBGColor.addActionListener(this);
		JBTitre.addActionListener(this);
		JBCouplet.addActionListener(this);
		JBTraduction.addActionListener(this);
		JBParImage.addActionListener(this);
		JSAlpha.addChangeListener(this);
		JSAlphaTitre.addChangeListener(this);
		JSAlphaContenu.addChangeListener(this);
		JCBAfficherImage.addActionListener(this);
		JTFCheminImage.addActionListener(this);
		jbDia.addActionListener(this);
		// add
//		JSAlpha.setMajorTickSpacing(500);
//		JSAlpha.setMinorTickSpacing(500);
//		JSAlpha.setSnapToTicks(true);
//		JSAlpha.setExtent(0);
		//System.out.println();
		
		this.setLayout(null);

		JPFontColor.setLayout(null);
		JPFontColor.add(JBTitre);
		JPFontColor.add(JBCouplet);
		JPFontColor.add(JBTraduction);
		JPFontColor.add(JSAlphaTitre);
		JPFontColor.add(JSAlphaContenu);
		JPFontColor.add(JCBLink);
		this.add(JPFontColor);

		JPDimPos.setLayout(null);
		JPDimPos.add(JLX);
		JPDimPos.add(JTFX);
		JPDimPos.add(JLY);
		JPDimPos.add(JTFY);
		JPDimPos.add(JLLargeur);
		JPDimPos.add(JLHauteur);
		JPDimPos.add(JTFLargeur);
		JPDimPos.add(JTFHauteur);
		JPDimPos.add(JBAjust);
		this.add(JPDimPos);

		JPBackground.setLayout(null);
		JPBackground.add(JLCBackground);
		JPBackground.add(JLIBackground);
		JPBackground.add(JSAlpha);
		JPBackground.add(jbDia);

		JPBackground.add(JBBGColor);
		JPBackground.add(JBParImage);
		JPBackground.add(JCBAfficherImage);
		JPBackground.add(JTFCheminImage);
		this.add(JPBackground);
	}

	public void setModel(ChantSettings model) {
		this.model = model;
		this.model.addActionListener(this);
		this.model.traiterEvent(new ActionEvent(model,1,"PositionEcran"));
		this.model.traiterEvent(new ActionEvent(model,1,"DimenionEcran"));
		this.model.traiterEvent(new ActionEvent(model,1,"ContenuColor"));
		this.model.traiterEvent(new ActionEvent(model,1,"TraductionColor"));
		this.model.traiterEvent(new ActionEvent(model,1,"TitreColor"));
		this.model.traiterEvent(new ActionEvent(model,1,"background color"));
		
	}

	public static void main(String[] args) {
		ChantParam fch = new ChantParam();
		JFrame jf = new JFrame("test)");
		jf.setContentPane((java.awt.Container) fch);
		jf.setBounds(320, 240, 320, 240);
		jf.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (model == null)
			return;
		if (e.getSource() == model) {
			if (e.getActionCommand().equals("PositionEcran")) {
				JTFX.setText("" + model.getPositionDeLEcran().x);
				JTFY.setText("" + model.getPositionDeLEcran().y);
				return;
			}
			if (e.getActionCommand().equals("DimenionEcran")) {
				JTFLargeur.setText("" + model.getDimenionDeLEcran().width);
				JTFHauteur.setText("" + model.getDimenionDeLEcran().height);
				return;
			}
			if (e.getActionCommand().equals("ContenuColor")) {
				JBCouplet.setBackground(model.getContenuColor());
				return;
			}
			if (e.getActionCommand().equals("TitreColor")) {
				JBTitre.setBackground(model.getTitreColor());
			}
			if (e.getActionCommand().equals("TraductionColor")) {
				JBTraduction.setBackground(model.getTraductionColor());
			}
			if (e.getActionCommand().equals("background color")) {
				JBBGColor.setBackground(model.getBackgroundColor());
			}
		}
		if (e.getSource() == JBAjust) {
			try {
				Point pt = new Point(Integer.parseInt(JTFX.getText()), Integer
						.parseInt(JTFY.getText()));
				model.setPositionDeLEcran(pt);
			} catch (NumberFormatException nfe) {
			}
			try {
				Dimension dim = new Dimension(Integer.parseInt(JTFLargeur
						.getText()), Integer.parseInt(JTFHauteur.getText()));
				model.setDimenionDeLEcran(dim);
			} catch (NumberFormatException nfe) {
			}
		}
		if (e.getSource() == JBBGColor) {
			Color currentColor = model.getBackgroundColor();
			Color temp = JColorChooser.showDialog(this,
					"Choisisez une couleure", currentColor);
			if (temp != null)
				model.setBackgroundColor(temp);
		}

		if (e.getSource() == JBCouplet) {
			JFontChooser fch = new JFontChooser(model.getContenuFont(), model
					.getContenuColor());
			int option = fch.showDialog(this);
			if (option == JFontChooser.OK_OPTION) {
				model.setContenuColor(fch.getColor());
				model.setContenuFont(fch.getFont());
			}
			// fch.dispose();
		}
		if (e.getSource() == JBTraduction) {
			JFontChooser fch = new JFontChooser(model.getTraductionFont(), model
					.getTraductionColor());
			int option = fch.showDialog(this);
			if (option == JFontChooser.OK_OPTION) {
				model.setTraductionColor(fch.getColor());
				model.setTraductionFont(fch.getFont());
			}
			// fch.dispose();
		}
		if (e.getSource() == JBTitre) {

			JFontChooser fch = new JFontChooser(model.getTitreFont(), model
					.getTitreColor());
			int option = fch.showDialog(this);
			if (option == JFontChooser.OK_OPTION) {
				model.setTitreColor(fch.getColor());
				model.setTitreFont(fch.getFont());
			}
			// fch.dispose();
		}
		if (e.getSource() == JTFCheminImage) {
			model.setBackgroundImage(JTFCheminImage.getText());
		}
		if (e.getSource() == JCBAfficherImage) {
			model.setIsImageBackgroundSet(JCBAfficherImage.isSelected());
		}
		if (e.getSource() == JBParImage) {
			JFileChooser fichierCh = new JFileChooser(model
					.getBackgroundImage());
			int rep = fichierCh.showDialog(this, "Ouvrir");
			if (rep == JFileChooser.APPROVE_OPTION) {
				JTFCheminImage.setText(fichierCh.getSelectedFile()
						.getAbsolutePath());
				model.setBackgroundImage(JTFCheminImage.getText());
			}

		}
		if (e.getSource() == jbDia) {
			JDialog jd = new JDialog();
			if (lpd == null)
				lpd = new ListPhotosDiaporama(model);
			jd.setTitle("Diaporama");
			jd.setContentPane(lpd);
			jd.pack();
			jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			jd.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					lpd.clickSurStop();
				}
			});
			jd.setVisible(true);

		}
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == JSAlpha){
			model.setAlpha(-JSAlpha.getValue());
		}
		else if (e.getSource() == JSAlphaTitre){
			model.setAlphaTitre(JSAlphaTitre.getValue());
			if (JCBLink.isSelected()) JSAlphaContenu.setValue(JSAlphaTitre.getValue()) ;
		}
		else if (e.getSource() == JSAlphaContenu){
			model.setAlphaContenu(JSAlphaContenu.getValue());
			if (JCBLink.isSelected()) JSAlphaTitre.setValue(JSAlphaContenu.getValue()) ;
		}
	}
}
