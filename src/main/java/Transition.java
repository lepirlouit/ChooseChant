// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 2/6/2007 9:58:10 PM
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Transition.java



import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

public class Transition
{

    public Transition()
    {
        numero = 0;
        parametre = "";
    }

    public void setNumero(int numero, String parametre)
    {
        this.numero = numero;
        this.parametre = parametre;
    }

    public void setEcran(int x_ecran, int y_ecran, int w_ecran, int h_ecran, int nbf)
    {
        this.x_ecran = x_ecran;
        this.y_ecran = y_ecran;
        this.w_ecran = w_ecran;
        this.h_ecran = h_ecran;
        this.nbf = nbf;
    }

    public void setNbf(int nbf)
    {
        this.nbf = nbf;
    }

    public void setCompteur(int compteur)
    {
        this.compteur = compteur;
    }

    public void setImages(Image frame, Image frame_suivante, Image image_travail)
    {
        this.frame = frame;
        this.frame_suivante = frame_suivante;
        this.image_travail = image_travail;
        graphique_travail = this.image_travail.getGraphics();
    }

    public int incrCompteur()
    {
        return ++compteur;
    }

    public void paintTransition(Graphics g, ImageObserver io, boolean bordure, Color coolbordure, Component co)
    {
        int w = frame_suivante.getWidth(io);
        int h = frame_suivante.getHeight(io);
        int xo = x_ecran + (w_ecran - w) / 2;
        int yo = y_ecran + (h_ecran - h) / 2;
        if(compteur > nbf)
            compteur = nbf;
        switch(numero)
        {
        case 0: // '\0'
            decalegauche(g, xo, yo, w, h, compteur, true, io);
            break;

        case 1: // '\001'
            decalegauche(g, xo, yo, w, h, nbf - compteur, false, io);
            break;

        case 2: // '\002'
            decalebas(g, xo, yo, w, h, compteur, true, io);
            break;

        case 3: // '\003'
            decalebas(g, xo, yo, w, h, nbf - compteur, false, io);
            break;

        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
            dessineFrame(g, io);
            zoom(g, xo, yo, w, h, compteur, numero - 4, false, io);
            break;

        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
        case 12: // '\f'
        case 13: // '\r'
            dessineFrameSuivante(g, io);
            zoom(g, xo, yo, w, h, compteur, numero - 9, true, io);
            break;

        case 14: // '\016'
            dessineFrameSuivante(g, io);
            fenetreouvrantehorizontal(g, xo, yo, w, h, compteur, true, io);
            break;

        case 15: // '\017'
            dessineFrameSuivante(g, io);
            fenetreouvrantehorizontal(g, xo, yo, w, h, compteur, false, io);
            break;

        case 16: // '\020'
            dessineFrame(g, io);
            fenetrefermantehorizontal(g, xo, yo, w, h, compteur, true, io);
            break;

        case 17: // '\021'
            dessineFrame(g, io);
            fenetrefermantehorizontal(g, xo, yo, w, h, compteur, false, io);
            break;

        case 18: // '\022'
            dessineFrameSuivante(g, io);
            fenetreouvrantevertical(g, xo, yo, w, h, compteur, true, io);
            break;

        case 19: // '\023'
            dessineFrameSuivante(g, io);
            fenetreouvrantevertical(g, xo, yo, w, h, compteur, false, io);
            break;

        case 20: // '\024'
            dessineFrame(g, io);
            fenetrefermantevertical(g, xo, yo, w, h, compteur, true, io);
            break;

        case 21: // '\025'
            dessineFrame(g, io);
            fenetrefermantevertical(g, xo, yo, w, h, compteur, false, io);
            break;

        case 22: // '\026'
            dessineFrameSuivante(g, io);
            fenetreouvrantequatrecoins(g, xo, yo, w, h, compteur, true, io);
            break;

        case 23: // '\027'
            dessineFrameSuivante(g, io);
            fenetreouvrantequatrecoins(g, xo, yo, w, h, compteur, false, io);
            break;

        case 24: // '\030'
            dessineFrame(g, io);
            fenetrefermantequatrecoins(g, xo, yo, w, h, compteur, true, io);
            break;

        case 25: // '\031'
            dessineFrame(g, io);
            fenetrefermantequatrecoins(g, xo, yo, w, h, compteur, false, io);
            break;

        case 26: // '\032'
            dessineFrame(g, io);
            parFormeOvale(g, xo, yo, w, h, compteur, io);
            break;

        case 27: // '\033'
            dessineFrame(g, io);
            balayageRadial(g, xo, yo, w, h, compteur, false, io);
            break;

        case 28: // '\034'
            dessineFrame(g, io);
            balayageRadial(g, xo, yo, w, h, compteur, true, io);
            break;

        case 29: // '\035'
            dessineFrame(g, io);
            balayageenX(g, xo, yo, w, h, compteur, true, io);
            break;

        case 30: // '\036'
            dessineFrame(g, io);
            balayageenX(g, xo, yo, w, h, compteur, false, io);
            break;

        case 31: // '\037'
            fondu((Graphics2D)g, compteur, io);
            break;

        case 32: // ' '
            noiretblanc((Graphics2D)g, xo, yo, w, h, compteur, io);
            break;

        case 33: // '!'
            posterize((Graphics2D)g, xo, yo, w, h, compteur, io);
            break;

        case 34: // '"'
            couleurPlus((Graphics2D)g, xo, yo, w, h, compteur, io);
            break;

        case 35: // '#'
            dessineFrame(g, io);
            parFormeOvale(g, xo, yo, w, h, compteur, io);
            break;

        case 36: // '$'
            dessineFrame(g, io);
            parFormeLosange(g, xo, yo, w, h, compteur, io);
            break;

        case 37: // '%'
            dessineFrame(g, io);
            parFormeEtoile(g, xo, yo, w, h, compteur, io);
            break;

        case 38: // '&'
            dessineFrameSuivante(g, io);
            decollage(g, false, true, xo, yo, w, h, compteur, io);
            break;

        case 39: // '\''
            dessineFrameSuivante(g, io);
            decollage(g, true, true, xo, yo, w, h, compteur, io);
            break;

        case 40: // '('
            dessineFrameSuivante(g, io);
            decollage(g, false, false, xo, yo, w, h, compteur, io);
            break;

        case 41: // ')'
            dessineFrameSuivante(g, io);
            decollage(g, true, false, xo, yo, w, h, compteur, io);
            break;

        case 42: // '*'
            dessineFrame(g, io);
            avancecanard(g, xo, yo, w, h, compteur, io);
            break;

        case 43: // '+'
            dessineFrame(g, io);
            avancecanard(g, xo, yo, w, h, compteur, io);
            break;

        case 44: // ','
            dessineFrameSuivante(g, io);
            decollageparcoin(g, xo, yo, w, h, compteur, io);
            break;

        case 45: // '-'
            essaiGrabbler(g, xo, yo, w, h, compteur, 0, true, io, co);
            break;

        case 46: // '.'
            essaiGrabbler(g, xo, yo, w, h, compteur, 1, true, io, co);
            break;

        case 47: // '/'
        case 48: // '0'
        case 49: // '1'
            if(nbf < 16)
                nbf = 16;
            essaiGrabbler(g, xo, yo, w, h, compteur, numero - 45, true, io, co);
            break;

        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
            if(nbf < 16)
                nbf = 16;
            essaiGrabbler(g, xo, yo, w, h, compteur, numero - 45, true, io, co);
            break;

        case 53: // '5'
            dessineFrame(g, io);
            tombante(g, xo, yo, w, h, compteur, io);
            break;

        default:
            dessineFrame(g, io);
            break;
        }
        if(bordure)
        {
            g.setColor(coolbordure);
            g.drawRect(xo, yo, w - 1, h - 1);
        }
    }

    public void genereParam()
    {
        switch(numero)
        {
        case 34: // '"'
            parametre = genereCouleur();
            break;

        case 27: // '\033'
        case 28: // '\034'
            parametre = "" + (int)(360D * Math.random());
            break;

        case 37: // '%'
            parametre = genereNombreString(0, 90, 3) + "." + genereNombreString(3, 15, 2) + "." + genereNombreString(10, 16, 2) + "." + genereNombreString(0, 90, 3);
            break;

        case 42: // '*'
        case 43: // '+'
            parametre = genereNombreString(0, 2, 1) + genereNombreString(0, 1, 1) + "." + "-010.-005." + genereNombreString(10, 12, 2) + ".10";
            break;

        case 44: // ','
            parametre = genereNombreString(0, 5, 1) + "." + aleadecollage();
            break;

        case 47: // '/'
        case 48: // '0'
        case 49: // '1'
            parametre = genereNombreString(2, 50, 2) + "." + genereNombreString(2, 99, 2);
            break;

        case 50: // '2'
        case 51: // '3'
        case 52: // '4'
            parametre = genereNombreString(0, 50, 2) + "." + genereNombreString(0, 2, 2);
            break;

        case 53: // '5'
            if(Math.random() < 0.5D)
                parametre = "02.005";
            else
                parametre = genereNombreString(1, 50, 2) + "." + genereNombreString(30, 240, 3);
            break;

        case 29: // '\035'
        case 30: // '\036'
        case 31: // '\037'
        case 32: // ' '
        case 33: // '!'
        case 35: // '#'
        case 36: // '$'
        case 38: // '&'
        case 39: // '\''
        case 40: // '('
        case 41: // ')'
        case 45: // '-'
        case 46: // '.'
        default:
            parametre = "";
            break;
        }
    }

    private String aleadecollage()
    {
        int v = (int)(6D * Math.random());
        String s = "";
        switch(v)
        {
        case 0: // '\0'
            s = "100.000.000.050.050.000.FFFFFF.094";
            break;

        case 1: // '\001'
            s = "010.050.040.100.000.000.665500.080";
            break;

        case 2: // '\002'
            s = "034.033.033.099.001.000.656742." + genereNombreString(120, 280, 3);
            break;

        case 3: // '\003'
            s = "100.000.000.050.050.000";
            break;

        case 4: // '\004'
            s = "010.050.040.100.000.000";
            break;

        case 5: // '\005'
            s = "034.033.033.099.001.000";
            break;
        }
        return s;
    }

    public String genereNombreString(int min, int max, int nbc)
    {
        int v = (int)((double)min + (double)(max - min) * Math.random());
        String s = "";
        for(s = s + v; s.length() < nbc; s = "0" + s);
        return s;
    }

    public String genereCouleur()
    {
        String s = "0123456789ABCDEF";
        String res;
        for(res = ""; res.length() < 6; res = res + s.charAt((int)(16D * Math.random())));
        return res;
    }

    public float distance(int s, float di, float dj)
    {
        if(s == 0)
            return (float)Math.sqrt((di * di + dj * dj) / 2.0F);
        if(s == 3)
            return (float)Math.sqrt(di * di + dj * dj);
        if(s == 1 || s == 4)
            return Math.abs(di) + Math.abs(dj) / 2.0F;
        else
            return Math.max(Math.abs(di), Math.abs(dj));
    }

    public void dessineFrame(Graphics g, ImageObserver io)
    {
        int w = frame.getWidth(io);
        int h = frame.getHeight(io);
        g.drawImage(frame, x_ecran + (w_ecran - w) / 2, y_ecran + (h_ecran - h) / 2, io);
    }

    public void dessineFrameSuivante(Graphics g, ImageObserver io)
    {
        int w = frame_suivante.getWidth(io);
        int h = frame_suivante.getHeight(io);
        g.drawImage(frame_suivante, x_ecran + (w_ecran - w) / 2, y_ecran + (h_ecran - h) / 2, io);
    }

    public void tombante(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        int tp = parametre.length();
        int hp = tp <= 2 ? h / 16 : getParamInt(parametre.substring(0, 2));
        int espace = tp <= 5 ? 3 * hp : getParamInt(parametre.substring(3, 6));
        if(hp < 1)
            hp = 1;
        if(espace < hp)
            espace += hp;
        int N = Math.round(h / hp);
        int last = h - (N - 1) * hp;
        int v = (espace * (N - 1)) / nbf;
        int pose = 0;
        int i;
        for(i = N - 1; i > (h - (N - i) * hp - (cpt - nbf) * v) / espace; i--)
            if(i > 0 || espace > v || i > (h - ((N - i) + 1) * hp - (cpt - nbf) * v) / espace)
                pose += i != 0 ? hp : last;

        if(pose > 0)
            g.drawImage(frame_suivante, xo, (yo + h) - pose, xo + w, yo + h, 0, h - pose, w, h, io);
        for(; i > (-hp - (cpt - nbf) * v) / espace; i--)
        {
            if(i == 0 && espace < v)
                return;
            int ory = i != 0 ? last + (i - 1) * hp : 0;
            int oryd = (cpt - nbf) * v + i * espace;
            int hdess = i != 0 ? hp : last;
            int dec = 0;
            if(oryd < 0)
            {
                dec = -oryd;
                if(hdess < dec)
                    return;
                oryd = 0;
            }
            g.drawImage(frame_suivante, xo, yo + oryd, xo + w, (yo + oryd + hdess) - dec, 0, ory, w, (ory + hdess) - dec, io);
        }

    }

    public void essaiGrabbler(Graphics g, int xo, int yo, int w, int h, int cpt, int type, 
            boolean sens, ImageObserver io, Component co)
    {
        int pixels[] = new int[w * h];
        DirectColorModel model = new DirectColorModel(32, 0xff0000, 65280, 255);
        MemoryImageSource mis = new MemoryImageSource(w, h, model, pixels, 0, w);
        PixelGrabber pg = new PixelGrabber(type <= 4 && 2 * cpt >= nbf ? frame_suivante : frame, 0, 0, w, h, pixels, 0, w);
        try
        {
            pg.grabPixels();
        }
        catch(InterruptedException e) { }
        switch(type)
        {
        case 0: // '\0'
            pixels = petits_carres(pixels, w, h, cpt);
            break;

        case 1: // '\001'
            pixels = deforme_horiz(pixels, w, h, cpt);
            break;

        default:
            pixels = deforme_spirale(pixels, w, h, cpt, type - 2);
            break;
        }
        mis.newPixels(pixels, model, 0, w);
        Image target = co.createImage(mis);
        if(type > 4 && 100 * cpt > 50 * nbf)
        {
            Graphics2D g2 = (Graphics2D)g;
            float alpha = (float)(nbf - cpt) / (0.5F * (float)nbf);
            g2.setComposite(AlphaComposite.getInstance(3, 1.0F - alpha));
            g2.drawImage(frame_suivante, xo, yo, io);
            g2.setComposite(AlphaComposite.getInstance(3, alpha));
            g2.drawImage(target, xo, yo, io);
            g2.setComposite(AlphaComposite.getInstance(3, 1.0F));
        }
        g.drawImage(target, xo, yo, io);
    }

    public int[] petits_carres(int pixels[], int w, int h, int cpt)
    {
        int pixels2[] = new int[w * h];
        /*int min = Math.min(w, h);*/
        float rap = Math.abs((2.0F * (float)(cpt - nbf / 2)) / (float)nbf);
        int nbc = (int)Math.pow(2D, 1 + (int)(rap * 6F));
        int wc = ((w + nbc) - 1) / nbc;
        int hc = ((h + nbc) - 1) / nbc;
        for(int i = 0; i < nbc; i++)
        {
            for(int j = 0; j < nbc; j++)
            {
                int ox = Math.min(j * hc, h - 1) * w + Math.min(i * wc, w - 1);
                int pix = pixels[ox];
                /*int oxavirer = (ox + (hc - 1) * w + wc) - 1;*/
                for(int i2 = 0; i2 < wc; i2++)
                {
                    for(int j2 = 0; j2 < hc; j2++)
                        if(j * hc + j2 < h && i * wc + i2 < w)
                            pixels2[ox + j2 * w + i2] = pix;

                }

            }

        }

        return pixels2;
    }

    public int[] deforme_horiz(int pixels[], int w, int h, int cpt)
    {
        int pixels2[] = new int[w * h];
        float rap = 1.0F - Math.abs((2.0F * (float)(cpt - nbf / 2)) / (float)nbf);
        int nbc = (int)((float)w * rap);
        if(nbc < 1)
            nbc = 1;
        for(int i = 0; i < w * h; i++)
            pixels2[i] = pixels[i - i % nbc];

        return pixels2;
    }

    public int[] deforme_spirale(int pixels[], int w, int h, int cpt, int d)
    {
        int pixels2[] = new int[w * h];
        float rap = d >= 3 ? (1.0F * (float)cpt) / (float)nbf : 1.0F - Math.abs((2.0F * (float)(cpt - nbf / 2)) / (float)nbf);
        int nbc = (int)((float)w * rap);
        int tp = parametre.length();
        int param = tp <= 2 ? 16 : getParamInt(parametre.substring(0, 2));
        int param2 = tp <= 4 ? 50 : getParamInt(parametre.substring(3, 5));
        if(param < 1)
            param = 16;
        if(nbc < 1)
            nbc = 1;
        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                float di = ((float)(i - w / 2) * 2.0F) / (float)w;
                float dj = ((float)(j - h / 2) * 2.0F) / (float)h;
                float dist = 0.0F;
                if(d < 3)
                    dist = distance(d, di, dj) * (1.0F - ((float)param2 * rap) / 100F);
                else
                    dist = (float)Math.pow(distance(d, di, dj), param2 != 0 ? 1.0F / (((float)param * rap) / 10F + 1.0F) : 1.0F + (rap * (float)param) / 10F);
                double alpha = Math.atan2(di, dj) + (double)(d >= 3 ? 0.0F : ((float)param * rap * (1.0F - dist)) / 3F);
                int jx = enBornes((int)((double)(h / 2) * (1.0D + (double)dist * Math.cos(alpha))), 0, h - 1);
                int ix = enBornes((int)((double)(w / 2) * (1.0D + (double)dist * Math.sin(alpha))), 0, w - 1);
                pixels2[j * w + i] = pixels[jx * w + ix];
            }

        }

        return pixels2;
    }

    public void decalegauche(Graphics g, int xo, int yo, int w, int h, int cpt, boolean sens, 
            ImageObserver io)
    {
        g.drawImage(sens ? frame : frame_suivante, xo, yo, (xo + w) - (w * cpt) / nbf, yo + h, (w * cpt) / nbf, 0, w, h, io);
        g.drawImage(sens ? frame_suivante : frame, (xo + w) - (w * cpt) / nbf, yo, xo + w, yo + h, 0, 0, (w * cpt) / nbf, h, io);
    }

    public void decalebas(Graphics g, int xo, int yo, int w, int h, int cpt, boolean sens, 
            ImageObserver io)
    {
        g.drawImage(sens ? frame : frame_suivante, xo, yo, xo + w, (yo + h) - (h * cpt) / nbf, 0, (h * cpt) / nbf, w, h, io);
        g.drawImage(sens ? frame_suivante : frame, xo, (yo + h) - (h * cpt) / nbf, xo + w, yo + h, 0, 0, w, (h * cpt) / nbf, io);
    }

    public void zoom(Graphics g, int xo, int yo, int w, int h, int cpt, int orig, 
            boolean ferme, ImageObserver io)
    {
        int wx = (cpt * w) / nbf;
        int hx = (cpt * h) / nbf;
        if(ferme)
        {
            wx = w - wx;
            hx = h - hx;
        }
        int xp = 0;
        int yp = 0;
        switch(orig)
        {
        case 0: // '\0'
            xp = xo;
            yp = yo;
            break;

        case 1: // '\001'
            xp = (xo + w) - wx;
            yp = yo;
            break;

        case 2: // '\002'
            xp = xo;
            yp = (yo + h) - hx;
            break;

        case 3: // '\003'
            xp = (xo + w) - wx;
            yp = (yo + h) - hx;
            break;

        case 4: // '\004'
            xp = xo + (w - wx) / 2;
            yp = yo + (h - hx) / 2;
            break;
        }
        g.drawImage(ferme ? frame : frame_suivante, xp, yp, xp + wx, yp + hx, 0, 0, w, h, io);
    }

    public void fenetrehorizontal(Graphics g, Image framep, int xo, int yo, int w, int h, int cpt, 
            boolean scale, ImageObserver io)
    {
        g.drawImage(framep, xo, yo, (xo + w / 2) - (w * cpt) / (2 * nbf), yo + h, scale ? (w * cpt) / (2 * nbf) : 0, 0, w / 2, h, io);
        g.drawImage(framep, xo + w / 2 + (w * cpt) / (2 * nbf), yo, xo + w, yo + h, w / 2, 0, scale ? w - (w * cpt) / (2 * nbf) : w, h, io);
    }

    public void fenetreouvrantehorizontal(Graphics g, int xo, int yo, int w, int h, int cpt, boolean scale, 
            ImageObserver io)
    {
        fenetrehorizontal(g, frame, xo, yo, w, h, cpt, scale, io);
    }

    public void fenetrefermantehorizontal(Graphics g, int xo, int yo, int w, int h, int cpt, boolean scale, 
            ImageObserver io)
    {
        fenetrehorizontal(g, frame_suivante, xo, yo, w, h, nbf - cpt, scale, io);
    }

    public void fenetrevertical(Graphics g, Image framep, int xo, int yo, int w, int h, int cpt, 
            boolean scale, ImageObserver io)
    {
        g.drawImage(framep, xo, yo, xo + w, (yo + h / 2) - (h * cpt) / (2 * nbf), 0, scale ? (h * cpt) / (2 * nbf) : 0, w, h / 2, io);
        g.drawImage(framep, xo, yo + h / 2 + (h * cpt) / (2 * nbf), xo + w, yo + h, 0, h / 2, w, scale ? h - (h * cpt) / (2 * nbf) : h, io);
    }

    public void fenetreouvrantevertical(Graphics g, int xo, int yo, int w, int h, int cpt, boolean scale, 
            ImageObserver io)
    {
        fenetrevertical(g, frame, xo, yo, w, h, cpt, scale, io);
    }

    public void fenetrefermantevertical(Graphics g, int xo, int yo, int w, int h, int cpt, boolean scale, 
            ImageObserver io)
    {
        fenetrevertical(g, frame_suivante, xo, yo, w, h, nbf - cpt, scale, io);
    }

    public void fenetrequatrecoins(Graphics g, Image framep, int xo, int yo, int w, int h, int cpt, 
            boolean scale, ImageObserver io)
    {
        g.drawImage(framep, xo, yo, (xo + w / 2) - (w * cpt) / (2 * nbf), (yo + h / 2) - (h * cpt) / (2 * nbf), scale ? (w * cpt) / (2 * nbf) : 0, scale ? (h * cpt) / (2 * nbf) : 0, w / 2, h / 2, io);
        g.drawImage(framep, xo, yo + h / 2 + (h * cpt) / (2 * nbf), (xo + w / 2) - (w * cpt) / (2 * nbf), yo + h, scale ? (w * cpt) / (2 * nbf) : 0, h / 2, w / 2, scale ? h - (h * cpt) / (2 * nbf) : h, io);
        g.drawImage(framep, xo + w / 2 + (w * cpt) / (2 * nbf), yo, xo + w, (yo + h / 2) - (h * cpt) / (2 * nbf), w / 2, scale ? (h * cpt) / (2 * nbf) : 0, scale ? w - (w * cpt) / (2 * nbf) : w, h / 2, io);
        g.drawImage(framep, xo + w / 2 + (w * cpt) / (2 * nbf), yo + h / 2 + (h * cpt) / (2 * nbf), xo + w, yo + h, w / 2, h / 2, scale ? w - (w * cpt) / (2 * nbf) : w, scale ? h - (h * cpt) / (2 * nbf) : h, io);
    }

    public void fenetreouvrantequatrecoins(Graphics g, int xo, int yo, int w, int h, int cpt, boolean scale, 
            ImageObserver io)
    {
        fenetrequatrecoins(g, frame, xo, yo, w, h, cpt, scale, io);
    }

    public void fenetrefermantequatrecoins(Graphics g, int xo, int yo, int w, int h, int cpt, boolean scale, 
            ImageObserver io)
    {
        fenetrequatrecoins(g, frame_suivante, xo, yo, w, h, nbf - cpt, scale, io);
    }

    public void rotation(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        Graphics2D gb = (Graphics2D)g;
        int nb = 1;
        try
        {
            nb = Integer.parseInt(parametre);
        }
        catch(NumberFormatException e) { }
        if(nb < 1)
            nb = 1;
        double rap = (double)cpt / (double)nbf;
        double alpha = 4D * rap * (double)nb * 3.1415926535897931D;
        double c = Math.cos(alpha) * rap;
        double s = Math.sin(alpha) * rap;
        double pmx = (double)(xo + w / 2) - ((c * (double)w) / 2D - (s * (double)h) / 2D);
        double pmy = (double)(yo + h / 2) - ((s * (double)w) / 2D + (c * (double)h) / 2D);
        gb.drawImage(frame_suivante, new AffineTransform(c, -s, s, c, pmx, pmy), io);
    }

    public void parFormeOvale(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        double rap = (Math.sqrt(w * w + h * h) * (double)cpt) / (double)(2 * nbf * Math.min(w, h));
        double dx = (double)w * rap;
        double dy = (double)h * rap;
        parForme(g, new java.awt.geom.Ellipse2D.Double((double)(w / 2) - dx, (double)(h / 2) - dy, 2D * dx, 2D * dy), xo, yo, w, h, cpt, io);
    }

    public void parFormeLosange(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        double rap = (double)cpt / (double)nbf;
        int dx = (int)((double)w * rap);
        int dy = (int)((double)h * rap);
        int xpoints[] = {
            w / 2 - dx, w / 2, w / 2 + dx, w / 2
        };
        int ypoints[] = {
            h / 2, h / 2 - dy, h / 2, h / 2 + dy
        };
        parForme(g, new Polygon(xpoints, ypoints, 4), xo, yo, w, h, cpt, io);
    }

    public void parFormeEtoile(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        double rap = (1.1000000000000001D * (double)cpt) / (double)nbf;
        int tp = parametre.length();
        int angledep = tp <= 2 ? 0 : getParamInt(parametre.substring(0, 3));
        int nbbranches = tp <= 5 ? 5 : getParamInt(parametre.substring(4, 6));
        if(nbbranches < 2)
            nbbranches = 5;
        int taillebran = tp <= 8 ? 10 : getParamInt(parametre.substring(7, 9));
        int v = tp <= 12 ? 10 : getParamInt(parametre.substring(10, 13));
        int dx = (int)((double)w * rap) / 2;
        int dy = (int)((double)h * rap) / 2;
        angledep += cpt * v;
        angledep *= nbbranches;
        int xpoints[] = new int[2 * nbbranches];
        int ypoints[] = new int[2 * nbbranches];
        for(int i = 0; i < nbbranches; i++)
        {
            xpoints[2 * i] = w / 2 + (int)((double)dx * Math.cos((2D * ((double)i + (double)angledep / 360D) * 3.1415926535897931D) / (double)nbbranches));
            ypoints[2 * i] = h / 2 + (int)((double)dy * Math.sin((2D * ((double)i + (double)angledep / 360D) * 3.1415926535897931D) / (double)nbbranches));
            xpoints[2 * i + 1] = w / 2 + (int)(((double)(dx * taillebran) / 10D) * Math.cos((2D * ((double)i + 0.5D + (double)angledep / 360D) * 3.1415926535897931D) / (double)nbbranches));
            ypoints[2 * i + 1] = h / 2 + (int)(((double)(dy * taillebran) / 10D) * Math.sin((2D * ((double)i + 0.5D + (double)angledep / 360D) * 3.1415926535897931D) / (double)nbbranches));
        }

        parForme(g, new Polygon(xpoints, ypoints, 2 * nbbranches), xo, yo, w, h, cpt, io);
    }

    public void parForme(Graphics g, Shape forme, int xo, int yo, int w, int h, int cpt, 
            ImageObserver io)
    {
        BufferedImage buff_im = new BufferedImage(w, h, 1);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(frame_suivante, 0, 0, io);
        BufferedImage oval_im = new BufferedImage(w, h, 6);
        Graphics2D gg_ov = oval_im.createGraphics();
        gg.drawImage(frame_suivante, 0, 0, io);
        gg_ov.setPaint(new TexturePaint(buff_im, new Rectangle(w, h)));
        gg_ov.fill(forme);
        g.drawImage(oval_im, xo, yo, io);
    }

    public void balayageRadial(Graphics g, int xo, int yo, int w, int h, int cpt, boolean montre, 
            ImageObserver io)
    {
        double angle = (double)(360 * cpt) / (double)Math.max(1, nbf - 1);
        double rap = Math.sqrt(w * w + h * h) / (double)Math.min(w, h);
        int angledepart = getParamInt();
        java.awt.geom.Arc2D.Double ellipse_tronquee = new java.awt.geom.Arc2D.Double(new java.awt.geom.Rectangle2D.Double(-rap * (double)w, -rap * (double)h, (double)w + 2D * rap * (double)w, (double)h + 2D * rap * (double)h), (montre ? -angle : 0.0D) + (double)angledepart, angle, 2);
        BufferedImage buff_im = new BufferedImage(w, h, 1);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(frame_suivante, 0, 0, io);
        BufferedImage oval_im = new BufferedImage(w, h, 6);
        Graphics2D gg_ov = oval_im.createGraphics();
        gg_ov.setPaint(new TexturePaint(buff_im, new Rectangle(w, h)));
        gg_ov.fill(ellipse_tronquee);
        g.drawImage(oval_im, xo, yo, io);
    }

    public void balayageenX(Graphics g, int xo, int yo, int w, int h, int cpt, boolean montre, 
            ImageObserver io)
    {
        double angle = (double)(90 * cpt) / (double)Math.max(1, nbf - 1);
        double rap = Math.sqrt(w * w + h * h) / (double)Math.min(w, h);
        /*int angledepart = getParamInt();*/
        java.awt.geom.Arc2D.Double ellipse_tronquee = new java.awt.geom.Arc2D.Double(new java.awt.geom.Rectangle2D.Double(-rap * (double)w, -rap * (double)h, (double)w + 2D * rap * (double)w, (double)h + 2D * rap * (double)h), (double)(montre ? 180 : 90) - angle, 2D * angle, 2);
        BufferedImage buff_im = new BufferedImage(w, h, 1);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(frame_suivante, 0, 0, io);
        BufferedImage oval_im = new BufferedImage(w, h, 6);
        Graphics2D gg_ov = oval_im.createGraphics();
        gg_ov.setPaint(new TexturePaint(buff_im, new Rectangle(w, h)));
        gg_ov.fill(ellipse_tronquee);
        ellipse_tronquee = new java.awt.geom.Arc2D.Double(new java.awt.geom.Rectangle2D.Double(-rap * (double)w, -rap * (double)h, (double)w + 2D * rap * (double)w, (double)h + 2D * rap * (double)h), (double)(montre ? 0 : -90) - angle, 2D * angle, 2);
        gg_ov.fill(ellipse_tronquee);
        g.drawImage(oval_im, xo, yo, io);
    }

    public void fondu(Graphics2D g, int cpt, ImageObserver io)
    {
        int w = frame_suivante.getWidth(io);
        int h = frame_suivante.getHeight(io);
        float alpha = (float)cpt / (float)Math.max(nbf - 1, 1);
        if(alpha > 1.0F)
            alpha = 1.0F;
        g.setComposite(AlphaComposite.getInstance(3, 1.0F - alpha));
        g.drawImage(frame, x_ecran + (w_ecran - w) / 2, y_ecran + (h_ecran - h) / 2, io);
        g.setComposite(AlphaComposite.getInstance(3, alpha));
        g.drawImage(frame_suivante, x_ecran + (w_ecran - w) / 2, y_ecran + (h_ecran - h) / 2, io);
        g.setComposite(AlphaComposite.getInstance(3, 1.0F));
    }

    public void decollage(Graphics g, boolean enx, boolean kot, int xo, int yo, int w, int h, 
            int cpt, ImageObserver io)
    {
        double rap = (1.0D * (double)cpt) / (double)nbf;
        int xdest = enx ? (int)(rap * (double)w) : 0;
        int ydest = enx ? 0 : (int)(rap * (double)h);
        if(kot)
        {
            if(enx && xdest < w / 2 || !enx && ydest < h / 2)
                g.drawImage(frame, xo + 2 * xdest, yo + 2 * ydest, xo + w, yo + h, 2 * xdest, 2 * ydest, w, h, io);
            g.drawImage(frame, xo + xdest, yo + ydest, xo + (enx ? Math.min(2 * xdest, w) : w), yo + (enx ? h : Math.min(2 * ydest, h)), xdest, ydest, enx ? Math.max(2 * xdest - w, 0) : w, enx ? h : Math.max(2 * ydest - h, 0), io);
        } else
        {
            if(enx && xdest < w / 2 || !enx && ydest < h / 2)
                g.drawImage(frame, xo, yo, (xo + w) - 2 * xdest, (yo + h) - 2 * ydest, 0, 0, w - 2 * xdest, h - 2 * ydest, io);
            g.drawImage(frame, xo + (enx ? Math.max(w - 2 * xdest, 0) : 0), yo + (enx ? 0 : Math.max(h - 2 * ydest, 0)), (xo + w) - (enx ? xdest : 0), (yo + h) - (enx ? 0 : ydest), enx ? Math.min(w, 2 * w - 2 * xdest) : 0, enx ? 0 : Math.min(h, 2 * h - 2 * ydest), w - xdest, h - ydest, io);
        }
    }

    public void decollageparcoin(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        if(parametre.length() < 15)
            parametre = "0.100.000.000.050.050.000.FFAA55.080";
        int tp = parametre.length();
        int xc = 0;
        int yc = 0;
        if(tp > 0)
        {
            int coin = getParamInt(parametre.substring(0, 1));
            switch(coin)
            {
            case 1: // '\001'
                xc = w;
                break;

            case 2: // '\002'
                xc = w;
                yc = h;
                break;

            case 3: // '\003'
                yc = h;
                break;
            }
        }
        int pa[] = {
            tp <= 4 ? 100 : getParamInt(parametre.substring(2, 5)), tp <= 8 ? 0 : getParamInt(parametre.substring(6, 9)), tp <= 12 ? 0 : getParamInt(parametre.substring(10, 13))
        };
        int pb[] = {
            tp <= 16 ? 100 : getParamInt(parametre.substring(14, 17)), tp <= 20 ? 0 : getParamInt(parametre.substring(18, 21)), tp <= 24 ? 0 : getParamInt(parametre.substring(22, 25))
        };
        String couleur = tp <= 31 ? "" : parametre.substring(26, 32);
        int pct_couleur = tp <= 35 ? 0 : getParamInt(parametre.substring(33, 36));
        int rap = (100 * cpt) / (nbf + 1);
        int xa = xc;
        int ya = yc;
        int xb = xc;
        int yb = yc;
        int xd = xc;
        int yd = yc;
        int xds = xd;
        int yds = yd;
        boolean fin = false;
        int cumul = 0;
        int i = 0;
        do
        {
            int tabox[] = coinSuivant(xd, yd, true, w, h);
            xds = tabox[0];
            yds = tabox[1];
            cumul += pa[i];
            if(rap < cumul)
            {
                xa = xds + ((xd - xds) * (cumul - rap)) / pa[i];
                ya = yds + ((yd - yds) * (cumul - rap)) / pa[i];
                fin = true;
            }
            i++;
            xd = xds;
            yd = yds;
            fin |= i > 2;
        } while(!fin);
        xd = xc;
        yd = yc;
        fin = false;
        i = 0;
        cumul = 0;
        do
        {
            int tabox[] = coinSuivant(xd, yd, false, w, h);
            xds = tabox[0];
            yds = tabox[1];
            cumul += pb[i];
            if(rap <= cumul)
            {
                xb = xds + ((xd - xds) * (cumul - rap)) / pb[i];
                yb = yds + ((yd - yds) * (cumul - rap)) / pb[i];
                fin = true;
            }
            i++;
            xd = xds;
            yd = yds;
            fin |= i > 2;
        } while(!fin);
        if(xa == xb || ya == yb)
            return;
        BufferedImage buff_im = new BufferedImage(w, h, 6);
        Graphics2D gg = buff_im.createGraphics();
        Polygon poly_a_retourner = getPolygon(xc, yc, true, xa, ya, xb, yb, w, h);
        double a = 0.0D;
        double b = 0.0D;
        double c = 0.0D;
        double det = ya * xb - xa * yb;
        if(det == 0.0D)
        {
            if(ya != 0)
            {
                a = 1.0D;
                b = -(a * (double)xa) / (double)ya;
            } else
            if(yb != 0)
            {
                a = 1.0D;
                b = -(a * (double)xb) / (double)yb;
            } else
            if(xa != 0)
            {
                b = 1.0D;
                a = -(b * (double)ya) / (double)xa;
            } else
            if(xb != 0)
            {
                b = 1.0D;
                a = -(b * (double)yb) / (double)xb;
            } else
            {
                return;
            }
        } else
        {
            c = 1.0D;
            b = (double)(xa - xb) / det;
            a = (double)(-(ya - yb)) / det;
        }
        det = 1.0D / (a * a + b * b);
        Polygon poly_a_conserver = getPolygon(xc, yc, false, xa, ya, xb, yb, w, h);
        gg.drawImage(coupe(frame_suivante, poly_a_retourner, w, h, io), 0, 0, io);
        gg.drawImage(coupe(frame, poly_a_conserver, w, h, io), 0, 0, io);
        if(couleur != "")
        {
            int rgb[] = getParamRGB(couleur);
            short rouge[] = new short[256];
            short vert[] = new short[256];
            short bleu[] = new short[256];
            for(i = 0; i < 256; i++)
            {
                float rapo = (1.0F * (float)pct_couleur) / 100F;
                rouge[i] = (short)(int)((float)i * rapo + (float)Math.min(rgb[0], 255) * (1.0F - rapo));
                vert[i] = (short)(int)((float)i * rapo + (float)Math.min(rgb[1], 255) * (1.0F - rapo));
                bleu[i] = (short)(int)((float)i * rapo + (float)Math.min(rgb[2], 255) * (1.0F - rapo));
            }

            short tabcoul[][] = {
                rouge, vert, bleu
            };
            BufferedImage buff_im2 = new BufferedImage(w, h, 1);
            Graphics2D gg2 = buff_im2.createGraphics();
            gg2.drawImage(frame, 0, 0, io);
            BufferedImageOp tabcoulOp = new LookupOp(new ShortLookupTable(0, tabcoul), null);
            BufferedImage buff_im4 = tabcoulOp.filter(buff_im2, null);
            gg.drawImage(coupe(buff_im4, poly_a_retourner, w, h, io), new AffineTransform((b * b - a * a) * det, -2D * a * b * det, -2D * a * b * det, (a * a - b * b) * det, -2D * a * c * det, -2D * b * c * det), io);
        } else
        {
            gg.drawImage(coupe(frame, poly_a_retourner, w, h, io), new AffineTransform((b * b - a * a) * det, -2D * a * b * det, -2D * a * b * det, (a * a - b * b) * det, -2D * a * c * det, -2D * b * c * det), io);
        }
        g.drawImage(buff_im, xo, yo, io);
    }

    private int[] coinSuivant(int x, int y, boolean trigo, int w, int h)
    {
        if(x == 0 && y == 0)
            return trigo ? (new int[] {
                0, h
            }) : (new int[] {
                w, 0
            });
        if(x == w && y == 0)
            return trigo ? (new int[] {
                0, 0
            }) : (new int[] {
                w, h
            });
        if(x == w && y == h)
            return trigo ? (new int[] {
                w, 0
            }) : (new int[] {
                0, h
            });
        else
            return trigo ? (new int[] {
                w, h
            }) : (new int[] {
                0, 0
            });
    }

    public BufferedImage coupe(Image framep, Polygon zone, int w, int h, ImageObserver io)
    {
        /*int pasx = (2 * w) / nbf + 1;
        int pasy = (2 * h) / nbf + 1;*/
        BufferedImage buff_im = new BufferedImage(w, h, 6);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(framep, 0, 0, io);
        BufferedImage morceau_a_retourner = new BufferedImage(w, h, 6);
        Graphics2D gg_morceau_a_retourner = morceau_a_retourner.createGraphics();
        gg_morceau_a_retourner.setPaint(new TexturePaint(buff_im, new Rectangle(w, h)));
        gg_morceau_a_retourner.fill(zone);
        return morceau_a_retourner;
    }

    public BufferedImage coupeasupprimer(Image framep, int xa, int ya, int xb, int yb, int xc, int yc, 
            int w, int h, ImageObserver io)
    {
        /*int pasx = (2 * w) / nbf + 1;
        int pasy = (2 * h) / nbf + 1;*/
        Polygon zone_a_retourner = getPolygon(xc, yc, true, xa, ya, xb, yb, w, h);
        BufferedImage buff_im = new BufferedImage(w, h, 1);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(framep, 0, 0, io);
        BufferedImage morceau_a_retourner = new BufferedImage(w, h, 6);
        Graphics2D gg_morceau_a_retourner = morceau_a_retourner.createGraphics();
        gg_morceau_a_retourner.setPaint(new TexturePaint(buff_im, new Rectangle(w, h)));
        gg_morceau_a_retourner.fill(zone_a_retourner);
        return morceau_a_retourner;
    }

    private Polygon getPolygon(int xc, int yc, boolean dedans, int xa, int ya, int xb, int yb, 
            int w, int h)
    {
        int xpts_in[] = new int[7];
        int ypts_in[] = new int[7];
        int nb_in = -1;
        int dx = xc;
        int dy = yc;
        boolean parcouru = false;
        boolean pris = false;
        if(xa == xc && ya == yc || xb == xc && yb == yc)
        {
            parcouru = true;
            nb_in = 0;
        }
        for(; !parcouru && nb_in < 6; parcouru |= dx == xc && dy == yc)
        {
            nb_in++;
            xpts_in[nb_in] = dx;
            ypts_in[nb_in] = dy;
            if(!pris && nb_in < 4 && (dx != xa || dy != ya) && (dx != xb || dy != yb))
            {
                if(dx == 0 && dy == 0 || dx == w && dy == h)
                {
                    if(ya == dy)
                    {
                        pris = true;
                        nb_in++;
                        xpts_in[nb_in] = xa;
                        ypts_in[nb_in] = ya;
                        nb_in++;
                        dx = xpts_in[nb_in] = xb;
                        dy = ypts_in[nb_in] = yb;
                    } else
                    if(yb == dy)
                    {
                        pris = true;
                        nb_in++;
                        xpts_in[nb_in] = xb;
                        ypts_in[nb_in] = yb;
                        nb_in++;
                        dx = xpts_in[nb_in] = xa;
                        dy = ypts_in[nb_in] = ya;
                    }
                } else
                if(dx == w && dy == 0 || dx == 0 && dy == h)
                    if(xa == dx)
                    {
                        pris = true;
                        nb_in++;
                        xpts_in[nb_in] = xa;
                        ypts_in[nb_in] = ya;
                        nb_in++;
                        dx = xpts_in[nb_in] = xb;
                        dy = ypts_in[nb_in] = yb;
                    } else
                    if(xb == dx)
                    {
                        pris = true;
                        nb_in++;
                        xpts_in[nb_in] = xb;
                        ypts_in[nb_in] = yb;
                        nb_in++;
                        dx = xpts_in[nb_in] = xa;
                        dy = ypts_in[nb_in] = ya;
                    }
                if(pris && (xa == xc && ya == yc || xb == xc && yb == yc))
                    parcouru = true;
            }
            if(dx != 0 && dx != w)
            {
                if(dy == 0)
                    dx = w;
                else
                    dx = 0;
                continue;
            }
            if(dy != 0 && dy != h)
            {
                if(dx == 0)
                    dy = 0;
                else
                    dy = h;
                continue;
            }
            if(dx == 0 && dy == 0)
            {
                dx = w;
                dy = 0;
                continue;
            }
            if(dx == w && dy == 0)
            {
                dx = w;
                dy = h;
                continue;
            }
            if(dx == w && dy == h)
            {
                dx = 0;
                dy = h;
                continue;
            }
            if(dx == 0 && dy == h)
            {
                dx = 0;
                dy = 0;
            }
        }

        if(dedans)
            return new Polygon(xpts_in, ypts_in, nb_in + 1);
        boolean sg = false;
        boolean sd = false;
        boolean ig = false;
        boolean id = false;
        for(int j = 0; j < nb_in + 1; j++)
        {
            if(xpts_in[j] == 0 && ypts_in[j] == 0)
                sg = true;
            if(xpts_in[j] == w && ypts_in[j] == 0)
                sd = true;
            if(xpts_in[j] == w && ypts_in[j] == h)
                id = true;
            if(xpts_in[j] == 0 && ypts_in[j] == h)
                ig = true;
        }

        if(!sg)
            return getPolygon(0, 0, true, xa, ya, xb, yb, w, h);
        if(!sd)
            return getPolygon(w, 0, true, xa, ya, xb, yb, w, h);
        if(!id)
            return getPolygon(w, h, true, xa, ya, xb, yb, w, h);
        if(!ig)
            return getPolygon(0, h, true, xa, ya, xb, yb, w, h);
        else
            return new Polygon();
    }

    public void avancecanard(Graphics g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        int tp = parametre.length();
        boolean enx = tp <= 0 ? true : getParamInt(parametre.substring(0, 1)) == 0;
        boolean kot = tp <= 1 ? true : getParamInt(parametre.substring(1, 2)) == 0;
        int or1 = tp <= 6 ? 0 : getParamInt(parametre.substring(3, 7));
        int or2 = tp <= 11 ? 0 : getParamInt(parametre.substring(8, 12));
        int v1 = tp <= 14 ? 10 : getParamInt(parametre.substring(13, 15));
        int v2 = tp <= 17 ? 10 : getParamInt(parametre.substring(16, 18));
        if(v1 < 1)
            v1 = 1;
        if(v2 < 1)
            v2 = 1;
        float pasx = (2.0F * (float)(w - or1)) / (float)(10 * nbf) + 1.0F;
        float pasy = (2.0F * (float)(h - or2)) / (float)(10 * nbf) + 1.0F;
        int xa = enBornes(enx ? (int)((float)((cpt / 2) * v1) * pasx + (float)or1) : 0, 0, w);
        if(enx && !kot)
            xa = w - xa;
        int ya = enBornes(enx ? 0 : (int)((float)((cpt / 2) * v1) * pasy + (float)or1), 0, h);
        if(!enx && !kot)
            ya = h - ya;
        int xb = enBornes(enx ? (int)((float)(((cpt - 1) / 2) * v2) * pasx + (float)or2) : w, 0, w);
        if(enx && !kot)
            xb = w - xb;
        int yb = enBornes(enx ? h : (int)((float)(((cpt - 1) / 2) * v2) * pasy + (float)or2), 0, h);
        if(!enx && !kot)
            yb = h - yb;
        int xc = kot ? 0 : w;
        int yc = kot ? 0 : h;
        Polygon zone = getPolygon(xc, yc, true, xa, ya, xb, yb, w, h);
        g.drawImage(coupe(frame_suivante, zone, w, h, io), xo, yo, io);
    }

    public void couleurPlus(Graphics2D g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        int rgb[] = getParamRGB();
        short rouge[] = new short[256];
        short vert[] = new short[256];
        short bleu[] = new short[256];
        float rap = Math.abs((float)cpt - (float)(nbf / 2));
        rap *= 2.0F / (float)nbf;
        if(rap < 0.0F)
            rap = 0.0F;
        if(rap > 1.0F)
            rap = 1.0F;
        for(int i = 0; i < 256; i++)
        {
            rouge[i] = (short)(int)((float)i * rap + (float)Math.min(rgb[0], 255) * (1.0F - rap));
            vert[i] = (short)(int)((float)i * rap + (float)Math.min(rgb[1], 255) * (1.0F - rap));
            bleu[i] = (short)(int)((float)i * rap + (float)Math.min(rgb[2], 255) * (1.0F - rap));
        }

        short tabcoul[][] = {
            rouge, vert, bleu
        };
        BufferedImageOp tabcoulOp = new LookupOp(new ShortLookupTable(0, tabcoul), null);
        BufferedImage buff_im = new BufferedImage(w, h, 1);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(cpt >= nbf / 2 ? frame_suivante : frame, 0, 0, io);
        BufferedImage nouvelleImage = tabcoulOp.filter(buff_im, null);
        g.drawImage(nouvelleImage, xo, yo, io);
    }

    public void posterize(Graphics2D g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        float rap = Math.abs((float)cpt - (float)(nbf / 2));
        rap *= 2.0F / (float)nbf;
        rap = 1.0F - rap;
        int pont = (int)(256F * rap);
        if(pont < 1)
            pont = 1;
        short posterize[] = new short[256];
        for(int i = 0; i < 256; i++)
            posterize[i] = (short)(i - (3 * (i % pont)) / 4);

        tableau256(g, posterize, xo, yo, w, h, cpt, io);
    }

    public void noiretblanc(Graphics2D g, int xo, int yo, int w, int h, int cpt, ImageObserver io)
    {
        float rap = Math.abs((float)cpt - (float)(nbf / 2));
        rap *= 2.0F / (float)nbf;
        int pont = (int)(256F * rap);
        if(pont < 1)
            pont = 1;
        short posterize[] = new short[256];
        for(int i = 0; i < 256; i++)
            posterize[i] = i >= 128 ? (short)(int)((float)i * rap + 255F * (1.0F - rap)) : (short)(int)((float)i * rap);

        tableau256(g, posterize, xo, yo, w, h, cpt, io);
    }

    public void tableau256(Graphics2D g, short tableau[], int xo, int yo, int w, int h, int cpt, 
            ImageObserver io)
    {
        BufferedImageOp posterizeOp = new LookupOp(new ShortLookupTable(0, tableau), null);
        BufferedImage buff_im = new BufferedImage(w, h, 1);
        Graphics2D gg = buff_im.createGraphics();
        gg.drawImage(cpt >= nbf / 2 ? frame_suivante : frame, 0, 0, io);
        BufferedImage nouvelleImage = posterizeOp.filter(buff_im, null);
        g.drawImage(nouvelleImage, xo, yo, io);
    }

    public int getParamInt()
    {
        return getParamInt(parametre);
    }

    public int getParamInt(String param)
    {
        int p = 0;
        try
        {
            p = Integer.parseInt(param);
        }
        catch(NumberFormatException e) { }
        return p;
    }

    public int[] getParamRGB()
    {
        return getParamRGB(parametre);
    }

    public int[] getParamRGB(String param)
    {
        if(param.length() != 6)
            return (new int[] {
                0, 0, 0
            });
        int res[] = new int[3];
        for(int i = 0; i < 3; i++)
        {
            String s = param.substring(2 * i, 2 * i + 2);
            res[i] = 128;
            try
            {
                res[i] = Integer.parseInt(s.toUpperCase(), 16);
            }
            catch(NumberFormatException e) { }
        }

        return res;
    }

    public int enBornes(int x, int min, int max)
    {
        if(x < min)
            return min;
        if(x > max)
            return max;
        else
            return x;
    }

    int numero;
    String parametre;
    Image frame;
    Image frame_suivante;
    Image image_travail;
    Graphics graphique_travail;
    int x_ecran;
    int y_ecran;
    int w_ecran;
    int h_ecran;
    int compteur;
    int nbf;
}
