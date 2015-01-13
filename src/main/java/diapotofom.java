// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 2/6/2007 9:57:49 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   diapotofom.java


import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

// Referenced classes of package diapojava:
//            Transition

public class diapotofom extends Applet
    implements Runnable, MouseListener, MouseMotionListener, KeyListener
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 9084924309296337446L;
	public diapotofom()
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
        int koin = 50;
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
        numerotation = getParameter("numero") != null;
        bordure = getParameter("bordure") != null;
        bordureimage = getParameter("bordureimage") != null;
        String nompolice = getParameter("police");
        if(nompolice == null)
            nompolice = "Palatino";
        int style = -1;
        String stylet = getParameter("style");
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
        String controle = getParameter("controle");
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
        for(; getParameter("image" + (i + 1)) != null; i++);
        nbImages = i;
        images = new Image[nbImages];
        transitions = new int[nbImages];
        tracker = new MediaTracker(this);
        for(i = 0; i < nbImages; i++)
        {
            images[i] = getImage(getCodeBase(), getParameter("image" + (i + 1)));
            transitions[i] = 9;
            tracker.addImage(images[i], i);
        }

        frame = 0;
        frame_suivante = nbImages <= 1 ? 0 : 1;
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        transiteur = new Transition();
        transiteur.setEcran(x_ecran, y_ecran, w_ecran, h_ecran, nb_frames_transition);
        transiteur.setCompteur(0);
        transiteur.setNumero(numerotrans, "");
    }

    private void setImagesTransition()
    {
        int wi = images[frame_suivante].getWidth(this);
        int hi = images[frame_suivante].getHeight(this);
        transiteur.setImages(images[frame], images[frame_suivante], createImage(wi, hi));
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
        paintControle(graphique_off);
        paintLegende(graphique_off);
        paintPub(graphique_off);
        if(!pub)
            graphique_off.drawString("transition n\260" + numerotrans + " -" + duree_image + "-" + nb_frames_transition + "-" + delai + "- p=" + transiteur.parametre, x_controle + 10, getHeight() - 2);
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
        return tracker.statusID(i, true) == 8;
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
        String s = getParameter(parametre);
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
        String s = getParameter("titre" + (i + 1));
        if(s == null)
            return "";
        else
            return s;
    }

    public int[] getParamRGB(String parametre)
    {
        String s = getParameter(parametre);
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

    public void paintControle(Graphics g)
    {
        if(pub && y_controle > y_ecran)
            return;
        g.setColor(couleurControle);
        int x_paint = x_controle + (menu_horizontal ? (w_controle - 13 * wb) / 2 : (w_controle - wb) / 2);
        int y_paint = y_controle + (menu_horizontal ? (h_controle - wb) / 2 : (h_controle - 11 * wb) / 2);
        int xp[] = {
            (x_paint + wb) - 3, (x_paint + wb) - 3, x_paint
        };
        int yp[] = {
            y_paint + 4, y_paint + wb, y_paint + wb / 2 + 2
        };
        g.fillPolygon(xp, yp, 3);
        if(menu_horizontal)
            x_paint += 2 * wb;
        else
            y_paint += 2 * wb;
        xp[0] = x_paint;
        yp[0] = y_paint + 4;
        xp[1] = x_paint;
        yp[1] = y_paint + wb;
        xp[2] = (x_paint + wb) - 3;
        yp[2] = y_paint + wb / 2 + 2;
        g.fillPolygon(xp, yp, 3);
        if(menu_horizontal)
            x_paint += 2 * wb;
        else
            y_paint += 2 * wb;
        if(!play)
            if(rgb_controle[0] + rgb_controle[1] + rgb_controle[2] > 64)
                g.setColor(new Color(rgb_controle[0] / 2, rgb_controle[1] / 2, rgb_controle[2] / 2));
            else
                g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x_paint, y_paint + 4, wb / 2 - 3, wb - 4);
        g.fillRect(x_paint + wb / 2 + 2, y_paint + 4, wb / 2 - 3, wb - 4);
        if(!play)
            g.setColor(couleurControle);
        if(menu_horizontal)
            x_paint += 2 * wb;
        else
            y_paint += 2 * wb;
        if(!menu_horizontal)
            x_paint = x_controle + (w_controle - 2 * wb) / 2;
        g.drawLine(x_paint, y_paint, x_paint + 2 * wb, y_paint);
        g.drawLine(x_paint + (2 * wb) / 3, y_paint, x_paint + (2 * wb) / 3, y_paint + wb / 2);
        g.drawLine(x_paint + (4 * wb) / 3, y_paint, x_paint + (4 * wb) / 3, y_paint + wb / 2);
        paintDegradePlusMoins(g, x_paint, y_paint);
        g.setColor(couleurControle);
        g.drawRect(x_paint, y_paint + wb / 2, 2 * wb, wb / 2);
        if(menu_horizontal)
            x_paint += 3 * wb;
        else
            y_paint += 2 * wb;
        for(int i = 0; i < 4; i++)
            g.drawRect(x_paint + (i * wb) / 4, y_paint, wb / 4, wb / 2);

        g.drawRect(x_paint + wb, y_paint, wb / 2, wb / 2);
        g.drawRect(x_paint + (3 * wb) / 2, y_paint, wb / 2, wb / 2);
        paintDegradePlusMoins(g, x_paint, y_paint);
        g.setColor(couleurControle);
        g.drawRect(x_paint, y_paint + wb / 2, 2 * wb, wb / 2);
        if(menu_horizontal)
            x_paint += 3 * wb;
        else
            y_paint += 2 * wb;
        if(!menu_horizontal)
            x_paint = x_controle + (w_controle - wb / 3) / 2;
        g.drawRect(x_paint, y_paint, wb / 3, wb);
        int hb = (wb * (compteur + (transition ? 0 : nb_frames_transition))) / (duree_image + nb_frames_transition);
        g.fillRect(x_paint, y_paint + hb, wb / 3, wb - hb);
    }

    private void paintDegradePlusMoins(Graphics g, int x_paint, int y_paint)
    {
        for(int i = 0; i < 2 * wb; i++)
        {
            g.setColor(new Color(rgb_controle[0] + ((rgb_fond[0] - rgb_controle[0]) * i) / (2 * wb), rgb_controle[1] + ((rgb_fond[1] - rgb_controle[1]) * i) / (2 * wb), rgb_controle[2] + ((rgb_fond[2] - rgb_controle[2]) * i) / (2 * wb)));
            g.drawLine(x_paint + i, y_paint + wb / 2, x_paint + i, y_paint + wb);
        }

        g.setColor(couleurFond);
        g.drawString("+", x_paint + 2, y_paint + wb);
        g.setColor(couleurControle);
        g.drawString("-", (x_paint + 2 * wb) - 8, y_paint + wb);
    }

    private void paintPub(Graphics g)
    {
        Font polpub = new Font("Verdana", 0, 12);
        g.setFont(polpub);
        g.setColor(couleurControle);
        if(pub)
        {
            String tpub = "DiapoJava \251 tofem@free.fr - D\351veloppements internet";
            FontMetrics fm = getFontMetrics(police);
            int x = getWidth() - fm.stringWidth(tpub) - 10;
            g.drawString(tpub, x, getHeight() - 4);
        } else
        {
            g.drawString("\251", getWidth() - 20, getHeight() - 8);
        }
    }

    private void paintLegende(Graphics g)
    {
        if(pub)
        {
            return;
        } else
        {
            g.setFont(police);
            g.setColor(couleurControle);
            int fl = transition ? frame_suivante : frame;
            String leg = getLegende(fl);
            String legende = (numerotation ? " " + (fl + 1) + " " + (leg == "" ? "" : "- ") : "") + getLegende(fl);
            FontMetrics fm = getFontMetrics(police);
            int x = (x_ecran + w_ecran / 2) - fm.stringWidth(legende) / 2;
            g.drawString(legende, x, (y_ecran + h_ecran) - 4);
            return;
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
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

    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == 40)
            numerotrans = suivant(numerotrans - 1, 0, 55);
        else
        if(e.getKeyCode() == 38)
            numerotrans = suivant(numerotrans + 1, 0, 55);
        if(e.getKeyCode() == 65)
            delai += 10;
        else
        if(e.getKeyCode() == 90)
        {
            delai -= 10;
            if(delai < 20)
                delai = 20;
        } else
        {
            transiteur.setNumero(numerotrans, "");
            transiteur.genereParam();
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
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
    Transition transiteur;
    int numerotrans;
    boolean pret_transition;
    boolean pub;
    boolean numerotation;
    boolean bordure;
    boolean bordureimage;
    Font police;
}
