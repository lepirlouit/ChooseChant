// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 2/6/2007 9:57:09 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   diapojava.java


import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

// Referenced classes of package diapojava:
//            Transition

public class diapojava extends Applet
    implements Runnable, MouseListener, MouseMotionListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5259547703753749127L;
	
	  public static void main (String [ ] args)
	  {
	    // Création d'une fenêtre et d'une instance de l'applet
	    Frame  fenetreApplet = new Frame ("TraitementTexte");
	    Applet applet = new diapojava();
	 
	    // Ajout de l'applet à la fenêtre puis affichage de la fenêtre
	    fenetreApplet.add ("Center", applet);
	    fenetreApplet.setVisible(true) ;
	    fenetreApplet.setSize (300, 200);
	    
	    // Démarrage de l'applet
	    applet.init ();
	    fenetreApplet.validate ();
	  }
	public diapojava()
    {
        nbImages = 0;
        x_ecran = 0;
        y_ecran = 0;
        x_controle = 0;
        y_controle = 0;
        w_controle = 0;
        h_controle = 0;
        w_ecran = 0;
        h_ecran = 0;
        delai = 85;
        frame = 0;
        frame_suivante = 0;
        compteur = 0;
        duree_image = 26;
        nb_frames_transition = 25;
        sens = 1;
        wb = 16;
        menu_horizontal = true;
        play = true;
        transition = false;
        numerotrans = 0;
        pret_transition = false;
        pub = false;
        numerotation = false;
        bordure = false;
        bordureimage = false;
    }

    public void init()
    {
        int i = 0;
        Dimension dim = getSize();
        int w = dim.width;
        int h = dim.height;
        int koin = 2 * wb + 8;
        duree_image = getParamInt("duree");
        if(duree_image < 1)
            duree_image = 26;
        nb_frames_transition = getParamInt("dureetransition");
        if(nb_frames_transition < 1)
            nb_frames_transition = 20;
        wb = getParamInt("dimcontrole");
        if(wb < 1)
            wb = 16;
        if(wb < 8)
            wb = 8;
        delai = getParamInt("delai");
        if(delai < 25)
            delai = 85;
        int rgb[] = getParamRGB("couleurfond");
        rgb_fond = rgb;
        couleurFond = new Color(rgb[0], rgb[1], rgb[2]);
        rgb = getParamRGB("couleurcontrole");
        rgb_controle = rgb;
        couleurControle = new Color(rgb[0], rgb[1], rgb[2]);
        String nompolice=null;
        numerotation=false;
        	bordure=false;
        	bordureimage=false;
        try{
        	numerotation = getParameter("numero") != null;
        	bordure = getParameter("bordure") != null;
        	bordureimage = getParameter("bordureimage") != null;
        	nompolice = getParameter("police");
        }catch(Exception e){}
        if(nompolice == null)
            nompolice = "Palatino";
        int style = -1;
        String stylet=null;
        try{
        	stylet = getParameter("style");
    	}catch(Exception e){}
        if(stylet == null)
            stylet = "";
        if(stylet.indexOf("g") != -1)
            style = 1;
        if(stylet.indexOf("i") != -1)
        {
            if(style < 0)
                style = 0;
            style += 2;
        }
        if(style < 0)
            style = 0;
        int taille = getParamInt("taille");
        if(taille < 6)
            taille = 6;
        police = new Font(nompolice, style, taille);
        String paramtransit=null;
        try{
        	paramtransit = getParameter("transitions");
        }catch(Exception e){}
        
        if(paramtransit == null)
        {
            transitions = (new int[] {
                29
            });
        } else
        {
            transitions = new int[paramtransit.length() / 3];
            for(i = 0; i < transitions.length; i++)
                try
                {
                    transitions[i] = Integer.parseInt(paramtransit.substring(3 * i, 3 * i + 2));
                }
                catch(NumberFormatException e)
                {
                    transitions[i] = 31;
                }

        }
        
        String controle=null;
        try{
        	controle= getParameter("controle");
        }catch(Exception e){}	
        if(controle == null)
            controle = "bas";
        if(controle.equals("droite"))
        {
            x_controle = w - koin;
            y_controle = 0;
            w_controle = koin;
            h_controle = h;
            x_ecran = 0;
            y_ecran = 0;
            w_ecran = w - koin;
            h_ecran = h;
            menu_horizontal = false;
        } else
        if(controle.equals("gauche"))
        {
            x_controle = 0;
            y_controle = 0;
            w_controle = koin;
            h_controle = h;
            x_ecran = koin;
            y_ecran = 0;
            w_ecran = w - koin;
            h_ecran = h;
            menu_horizontal = false;
        } else
        if(controle.equals("haut"))
        {
            x_controle = 0;
            y_controle = 0;
            w_controle = w;
            h_controle = koin;
            x_ecran = 0;
            y_ecran = koin;
            w_ecran = w;
            h_ecran = h - koin;
        } else
        {
            x_controle = 0;
            y_controle = h - koin;
            w_controle = w;
            h_controle = koin;
            x_ecran = 0;
            y_ecran = 0;
            w_ecran = w;
            h_ecran = h - koin;
        }
        
        try{
        	for(i = 0; getParameter("image" + (i + 1)) != null; i++);
        }catch(Exception e){}
        nbImages = i;
        images = new Image[nbImages];
        tracker = new MediaTracker(this);
        for(i = 0; i < nbImages; i++)
        {
            images[i] = getImage(getCodeBase(), getParameter("image" + (i + 1)));
            tracker.addImage(images[i], i);
        }

        frame = 0;
        frame_suivante = nbImages <= 1 ? 0 : 1;
        addMouseListener(this);
        addMouseMotionListener(this);
        transiteur = new Transition();
        transiteur.setEcran(x_ecran, y_ecran, w_ecran, h_ecran, nb_frames_transition);
        transiteur.setCompteur(0);
        transiteur.setNumero(transitions[numerotrans], getParamTransit(0));
    }

    private void setImagesTransition()
    {
        int wi = images[frame_suivante].getWidth(this);
        int hi = images[frame_suivante].getHeight(this);
        Image imp = createImage(wi, hi);
        transiteur.setImages(images[frame], images[frame_suivante], imp);
    }

    public void paint(Graphics g)
    {
        Dimension dim = getSize();
        if(graphique_off == null)
            initGraphiques();
        graphique_off.setColor(couleurFond);
        graphique_off.fillRect(0, 0, dim.width, dim.height);
        if(bordure)
        {
            graphique_off.setColor(couleurControle);
            graphique_off.drawRect(0, 0, dim.width - 1, dim.height - 1);
        }
        if(!transition && estCharge(frame))
            dessinePhoto(frame, graphique_off);
        else
        if(transition && estCharge(frame))
        {
            if(estCharge(frame_suivante))
            {
                if(transiteur.image_travail == null)
                    setImagesTransition();
                transiteur.paintTransition(graphique_off, this, bordureimage, couleurControle, this);
            } else
            {
                dessinePhoto(frame, graphique_off);
                compteur = 0;
            }
        } else
        {
            graphique_off.setColor(couleurControle);
            graphique_off.drawString("Chargement en cours", x_ecran + 20, (y_ecran + h_ecran / 2) - 16);
            compteur--;
        }
        g.drawImage(image_off, 0, 0, this);
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public void dessinePhoto(int i, Graphics g)
    {
        int w = images[i].getWidth(this);
        int h = images[i].getHeight(this);
        g.drawImage(images[i], x_ecran + (w_ecran - w) / 2, y_ecran + (h_ecran - h) / 2, this);
        if(bordureimage)
        {
            g.setColor(couleurControle);
            g.drawRect(x_ecran + (w_ecran - w) / 2, y_ecran + (h_ecran - h) / 2, w - 1, h - 1);
        }
    }

    private boolean estCharge(int i)
    {
        try{
        	return tracker.statusID(i, true) == 8;
        }catch(Exception e){}
        return false;
    }

    public void initGraphiques()
    {
        Dimension dim = getSize();
        image_off = createImage(dim.width, dim.height);
        graphique_off = image_off.getGraphics();
    }

    public void run()
    {
        long tm = System.currentTimeMillis();
        do
        {
            if(Thread.currentThread() != animator)
                break;
            repaint();
            try
            {
                tm += delai;
                Thread.sleep(Math.max(0L, tm - System.currentTimeMillis()));
            }
            catch(InterruptedException e)
            {
                break;
            }
            if(compteur >= duree_image && !transition && pret_transition())
            {
                transition = true;
                transiteur.setCompteur(0);
                setImagesTransition();
                compteur = 0;
            } else
            if(compteur >= nb_frames_transition && transition)
                imageSuivante();
            if(play)
                if(transition)
                    compteur = transiteur.incrCompteur();
                else
                    compteur++;
        } while(true);
    }

    public void imageSuivante()
    {
        int suivante = suivant(frame + sens, 0, nbImages - 1);
        if(estCharge(suivante))
        {
            frame = suivante;
            frame_suivante = suivant(frame + sens, 0, nbImages - 1);
            compteur = 0;
            transition = false;
            transiteur.setCompteur(0);
            numerotrans = suivant(numerotrans + sens, 0, transitions.length - 1);
            transiteur.setNumero(transitions[numerotrans], getParamTransit(numerotrans));
        }
    }

    public boolean pret_transition()
    {
        return estCharge(frame) && estCharge(frame_suivante);
    }

    public int inBornes(int i, int mini, int maxi)
    {
        if(i < mini)
            return mini;
        if(i > maxi)
            return maxi;
        else
            return i;
    }

    public int suivant(int i, int mini, int maxi)
    {
        if(i < mini)
            return maxi;
        if(i > maxi)
            return mini;
        else
            return i;
    }

    public int getParamInt(String parametre)
    {
    	String s=null;
    	try{
    		s = getParameter(parametre);
    	}catch(Exception e){}
        if(s == null)
            return -1;
        int p = -1;
        try
        {
            p = Integer.parseInt(s);
        }
        catch(NumberFormatException e) { }
        return p;
    }

    public String getLegende(int i)
    {
    	String s=null;
    	try{
        	s = getParameter("titre" + (i + 1));
    	}catch(Exception e){}
        if(s == null)
            return "";
        else
            return s;
    }

    public String getParamTransit(int i)
    {
    	String s=null;
    	try{
    		s = getParameter("p" + (i + 1));
    	}catch(Exception e){}
        
        if(s == null)
            return "";
        else
            return s;
    }

    public int[] getParamRGB(String parametre)
    {
    	String s=null;
    	try{
    		s = getParameter(parametre);
    	}catch(Exception e){}	
        if(s == null)
            if(parametre == "couleurcontrole")
                return (new int[] {
                    255, 255, 255
                });
            else
                return (new int[] {
                    0, 0, 0
                });
        if(s.length() != 6)
            return (new int[] {
                0, 0, 0
            });
        int res[] = new int[3];
        for(int i = 0; i < 3; i++)
        {
            String st = s.substring(2 * i, 2 * i + 2);
            res[i] = 0;
            try
            {
                res[i] = Integer.parseInt(st.toUpperCase(), 16);
            }
            catch(NumberFormatException e) { }
        }

        return res;
    }

    public void start()
    {
        animator = new Thread(this);
        animator.start();
    }

    public void stop()
    {
        animator = null;
    }

   

     public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(pub && x > getWidth() - 220 && y > getHeight() - 22)
        {
            AppletContext navigateur = getAppletContext();
            try
            {
                navigateur.showDocument(new URL("http://tofem.free.fr"));
            }
            catch(MalformedURLException ex) { }
        }
        int x_paint = x_controle + (menu_horizontal ? (w_controle - 13 * wb) / 2 : (w_controle - wb) / 2);
        int y_paint = y_controle + (menu_horizontal ? (h_controle - wb) / 2 : (h_controle - 11 * wb) / 2);
        if(x >= x_paint && x < x_paint + wb + 1 && y >= y_paint && y < y_paint + wb + 1)
        {
            sens = -1;
            imageSuivante();
            return;
        }
        if(menu_horizontal)
            x_paint += 2 * wb;
        else
            y_paint += 2 * wb;
        if(x >= x_paint && x < x_paint + wb + 1 && y >= y_paint && y < y_paint + wb + 1)
        {
            sens = 1;
            imageSuivante();
            return;
        }
        if(menu_horizontal)
            x_paint += 2 * wb;
        else
            y_paint += 2 * wb;
        if(x >= x_paint && x < x_paint + wb + 1 && y >= y_paint && y < y_paint + wb + 1)
        {
            play = !play;
            return;
        }
        if(menu_horizontal)
            x_paint += 2 * wb;
        else
            y_paint += 2 * wb;
        if(!menu_horizontal)
            x_paint = x_controle + (w_controle - 2 * wb) / 2;
        if(x >= x_paint && x < x_paint + 2 * wb + 1 && y >= y_paint && y < y_paint + wb + 1)
        {
            int dep = (x_paint + wb + 1) - x;
            duree_image = inBornes(duree_image + dep / 2, 1, 1024);
            if(dep < 0 && duree_image > 120)
                duree_image += 2 * dep;
            return;
        }
        if(menu_horizontal)
            x_paint += 3 * wb;
        else
            y_paint += 2 * wb;
        if(x >= x_paint && x < x_paint + 2 * wb + 1 && y >= y_paint && y < y_paint + wb + 1)
        {
            int dep = (x_paint + wb + 1) - x;
            nb_frames_transition = inBornes(nb_frames_transition + dep / 2, 1, 1024);
            if(dep < 0 && duree_image > 60)
                nb_frames_transition += 2 * dep;
            transiteur.setNbf(nb_frames_transition);
            return;
        } else
        {
            return;
        }
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
    }

    public void mouseDragged(MouseEvent mouseevent)
    {
    }

    public void mouseMoved(MouseEvent e)
    {
        Dimension dim = getSize();
        if(e.getX() > dim.width - 22 && e.getY() > dim.height - 22)
            pub = true;
        if(e.getY() < dim.height - 22)
            pub = false;
    }

    int nbImages;
    int x_ecran;
    int y_ecran;
    int x_controle;
    int y_controle;
    int w_controle;
    int h_controle;
    int w_ecran;
    int h_ecran;
    Image images[];
    int transitions[];
    MediaTracker tracker;
    Graphics graphique_off;
    Image image_off;
    Thread animator;
    int delai;
    int frame;
    int frame_suivante;
    int compteur;
    int duree_image;
    int nb_frames_transition;
    int sens;
    int wb;
    boolean menu_horizontal;
    Color couleurControle;
    int rgb_controle[];
    Color couleurFond;
    int rgb_fond[];
    boolean play;
    boolean transition;
    int numerotrans;
    Transition transiteur;
    boolean pret_transition;
    boolean pub;
    boolean numerotation;
    boolean bordure;
    boolean bordureimage;
    Font police;
}
