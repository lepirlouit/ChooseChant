package swingview;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.security.AccessControlException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import settings.ChantSettings;

public class ChantEcran extends JFrame implements MouseListener,
		MouseMotionListener, ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2893167867817347439L;

	private Ecran monEcran;

	private Point pointDeClickRelatif;

	private ChantSettings model;

	public ChantEcran() {
		this.setUndecorated(true);
		monEcran = new Ecran();
		monEcran.setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		// setBounds(0,0,640,480);
		this.setLayout(new BorderLayout());
		Container co = getContentPane();
		co.add(monEcran);
		setVisible(true);
		// setBounds(0,0,640,480);

		monEcran.addMouseListener(this);
		try {
			this.setAlwaysOnTop(false);
		} catch (AccessControlException e) {
			System.out.println("catched ChantEcran : 57");
		}
		/* curseur transparent */
		BufferedImage bufferedImage = new BufferedImage(16, 16,
				Transparency.TRANSLUCENT);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
				bufferedImage, new Point(), "cursor");

		this.setCursor(cursor);
	}

	public void mousePressed(MouseEvent e) {
		pointDeClickRelatif = e.getPoint();
		monEcran.addMouseMotionListener(this);
	}

	public void mouseReleased(MouseEvent e) {
		monEcran.removeMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		Point pt = MouseInfo.getPointerInfo().getLocation();
		int x = (int) (pt.getX() - pointDeClickRelatif.getX());
		int y = (int) (pt.getY() - pointDeClickRelatif.getY());
		Point p = new Point(x, y);
		this.setLocation(p);
		model.setPositionDeLEcran(p);
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void setModel(ChantSettings model) {
		if (model == null)
			return;
		this.model = model;
		// enregistre la vue comme tant l'coute du modle
		this.model.addActionListener(this);

		this.repaint();
		monEcran.repaint();
		monEcran.setModel(model);
	}

	public void actionPerformed(ActionEvent e) {
		if (model == null)
			return;
		if (e.getSource() == model) {
			if (e.getActionCommand().equals("PositionEcran")) {
				this.setLocation(model.getPositionDeLEcran());
			}
			if (e.getActionCommand().equals("DimenionEcran")) {
				this.setSize(model.getDimenionDeLEcran());
				monEcran.setSize(model.getDimenionDeLEcran());
				this.invalidate();
				this.validate();
			}
			if (e.getActionCommand().equals("background color")) {
				monEcran.setBackground(model.getBackgroundColor());
			}
			this.repaint();
			monEcran.repaint();

		}
	}
}

class Ecran extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 516364703136622801L;

	private ChantSettings model;

	public void setModel(ChantSettings model) {
		// if (model == null) return;
		this.model = model;
		// enregistre la vue comme tant l'coute du modle
		// this.model.addActionListener(this);
		this.setBackground(model.getBackgroundColor());
		this.repaint();

	}

	public void paintComponent(Graphics g) {
		if (this.model == null)
			return;
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);

		// int tailleX = getHeight()/2;

		if (model.isImageBackgroundSet()) {
			Image image; 
			if(model.getImageFromDiapo()!=null){
				image = model.getImageFromDiapo();
			}
			else image = getToolkit().getImage(model.getBackgroundImage());
			int thumbH = model.getDimenionDeLEcran().height;
			int thumbW = thumbH * image.getWidth(this) / image.getHeight(this);
			if (thumbW > model.getDimenionDeLEcran().width) {
				thumbW = model.getDimenionDeLEcran().width;
				thumbH = thumbW * image.getHeight(this) / image.getWidth(this);
			}
			int decalX = (model.getDimenionDeLEcran().width - thumbW) / 2;
			int decalY = (model.getDimenionDeLEcran().height - thumbH) / 2;
			g.drawImage(image, decalX, decalY, thumbW, thumbH, this);
		}
		int modelAlpha = model.getAlpha();
		int alpha = Math.abs(modelAlpha);
		g.setColor((modelAlpha > 0) ? Color.BLACK : Color.WHITE);

		/* pour assombrir ou éclairsir l'arrière plan */
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				((float) alpha) / 100));
		g.fillRect(0, 0, model.getDimenionDeLEcran().width, model
				.getDimenionDeLEcran().height);

		/*
		 * variable pour calculer la position de la ligne depuis le centre de
		 * l'écran
		 */
		int tailleY = getWidth() / 2;
		g.setColor(model.getTitreColor());
		g.setFont(model.getTitreFont());
		FontMetrics fontMetrics = g.getFontMetrics();
		String titre = model.getTitreEnCours();
		int tailleTitre = fontMetrics.stringWidth(titre) / 2;
		int hautTitre = fontMetrics.getHeight();

		if (titre.length() > 0)
			if (titre.charAt(0) == '#')
				titre = "";
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				((float) model.getAlphaTitre()) / 100));
		g.drawString(titre, tailleY - tailleTitre, hautTitre + 10);

		// Ã©rire le contenu sur plusieurs lignes
		String[] lignes = model.getContenuEnCours().split("\n");
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				((float) model.getAlphaContenu()) / 100));
		int nextLineY = hautTitre + 10;
		for (int i = 0; i < lignes.length; i++) {
			boolean isTrad = (lignes[i].length() >= 2
					&& lignes[i].charAt(0) == '(' && lignes[i].charAt(lignes[i]
					.length() - 1) == ')');
			if (isTrad) {
				lignes[i] = lignes[i].substring(1, lignes[i].length() - 1);
				g.setColor(model.getTraductionColor());
				g.setFont(model.getTraductionFont());
			} else {
				g.setColor(model.getContenuColor());
				g.setFont(model.getContenuFont());
			}
			fontMetrics = g.getFontMetrics();
			// x doit varier en fonction de la longuer de la chaine
			int x = tailleY - (fontMetrics.stringWidth(lignes[i]) / 2);
			// y varie en fonction de la hauteur de la ligne
			int y =  nextLineY+= fontMetrics.getHeight();
			g.drawString(lignes[i], x, y);
		}
		g2d.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, 1));
		g2d.dispose();

	}
}
