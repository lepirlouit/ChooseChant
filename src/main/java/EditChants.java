import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import settings.ChantSettings;
import swingview.PanelEditCouplet;

import domaine.Chant;
import domaine.Couplet;




public class EditChants extends JFrame {


	private static final long serialVersionUID = 4116327524689404603L;
	
	private static ChantSettings model=ChantSettings.getInstance();
	private Box listePanelsCouplets = Box.createVerticalBox(); 

	{
		// copie de sauvegarde toutes les 60 secondes.
		Thread th = new Thread(new Runnable(){

			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(60*1000);
						model.sauverChansonsTxt(new File("out_recup.txt"));
						System.err.println("creation du fichier de récupération : out_recup.txt");
					} catch (InterruptedException e) {
					}
				}
				
			}
			
		});
		th.start();
		
	}
	
	private JMenuBar barreDesMenus() {
		JMenuBar maBarre = new JMenuBar();
		JMenu fichier = new JMenu("Fichier");
		maBarre.add(fichier);
		JMenuItem openTxt = new JMenuItem("Ouvrir fichier TXT");
		fichier.add(openTxt);
		openTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fileOpenTxt();
			}
		});
		JMenuItem openXml = new JMenuItem("Ouvrir fichier XML");
		fichier.add(openXml);
		openXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				fileOpenXml();
			}
		});
		
		JMenuItem saveFile = new JMenuItem("Save currentlyEditiongFile");
		fichier.add(saveFile);
		saveFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				model.sauverChansonsTxt(new File(model.getLastOpenedFile()));
			}
		});
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		
		return maBarre;
	}
	
	private void fileOpenTxt() {
		JFileChooser fichierCh = new JFileChooser(model.getLastOpenedFile());
		fichierCh.setAcceptAllFileFilterUsed(false);
		
		fichierCh.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
			    if (f.isDirectory())
			        return true;
			      String s = f.getName();
			      int i = s.lastIndexOf('.');

			      if (i > 0 && i < s.length() - 1)
			        if (s.substring(i + 1).toLowerCase().equals("txt"))
			          return true;

			      return false;
			}

			@Override
			public String getDescription() {
				return "Fichiers texte (chant ... & ref-cpl) (*.txt)";
			}
			
		});
		
		int returnVal = fichierCh.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION){
			int result = askForSave();
			if(result != JOptionPane.CANCEL_OPTION){
				model.chargerChansonsTxt(fichierCh.getSelectedFile());
				this.setTitle(model.getLastOpenedFile());
			}
			
		}
	}

	private void fileOpenXml() {
		JFileChooser fichierCh = new JFileChooser(model.getLastOpenedFile());
		fichierCh.setAcceptAllFileFilterUsed(false);
		
		fichierCh.addChoosableFileFilter(new FileFilter(){

			@Override
			public boolean accept(File f) {
			    if (f.isDirectory())
			        return true;
			      String s = f.getName();
			      int i = s.lastIndexOf('.');

			      if (i > 0 && i < s.length() - 1)
			        if (s.substring(i + 1).toLowerCase().equals("xml"))
			          return true;

			      return false;
			}

			@Override
			public String getDescription() {
				return "Fichiers xml (<chant> <ref-cpl>) (*.xml)";
			}
			
		});
		int returnVal = fichierCh.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION){
			int result = askForSave();
			if(result != JOptionPane.CANCEL_OPTION){
				/*model.chargerChansonsXML(fichierCh.getSelectedFile());*/
				this.setTitle(model.getLastOpenedFile());
			}
		}
	}
	
	public EditChants() {
		super();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				model.saveParam();
				
				int result = askForSave();
				if (result!=JOptionPane.CANCEL_OPTION)System.exit(0);
			}

			
		});
		model.loadLastOpenedFile();
		this.setTitle(model.getLastOpenedFile());
		Container co = this.getContentPane();
		final JTextField titreText = new JTextField(35);
		JPanel panelMaitreTitres= new JPanel();
		final JList listeChansons = new JList();
		panelMaitreTitres.setLayout(new BorderLayout());
		
		setJMenuBar(barreDesMenus());
		
		JPanel panelBoutonsListeTitres=new JPanel();
		panelBoutonsListeTitres.setLayout(new GridLayout(/*hauteur,largeur*/1,6));
		JButton btPlusChanson = new JButton("+");
		btPlusChanson.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int nbr = listeChansons.getSelectedIndex();
				model.getTitres(). add(nbr+1,new Chant(" "));
				listeChansons.setListData(model.getTitres().toArray() );
				listeChansons.setSelectedIndex(nbr+1);
			}
		});
		panelBoutonsListeTitres.add(btPlusChanson);
		
		JButton btMoinsChanson = new JButton("-");
		btMoinsChanson.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {

				int nbr = listeChansons.getSelectedIndex();
				if (nbr!=-1){
					model.getTitres().remove(nbr);
					listeChansons.setListData(model.getTitres().toArray() );
					listeChansons.setSelectedIndex(Math.min(nbr, model.getTitres().size()));
				}
			}
		});
		panelBoutonsListeTitres.add(btMoinsChanson);
		
		panelBoutonsListeTitres.add( new JPanel());
		panelBoutonsListeTitres.add( new JPanel());
		JButton btnMoveSongUp = new JButton("/\\");
		btnMoveSongUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listeChansons.getSelectedIndex();
				
				if (selectedIndex!=-1 && selectedIndex!=0){
					Chant songTomove = model.getTitres().get(selectedIndex);
					model.getTitres().remove(selectedIndex);
					model.getTitres(). add(selectedIndex-1,songTomove);
					
					listeChansons.setListData(model.getTitres().toArray() );
					listeChansons.setSelectedIndex(Math.min(selectedIndex-1, model.getTitres().size()));
				}
				
			}
		});
		JButton btnMoveSongDown = new JButton("\\/");
		btnMoveSongDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = listeChansons.getSelectedIndex();
				
				if (selectedIndex!=-1 && selectedIndex!=model.getTitres().size()-1){
					Chant songTomove = model.getTitres().get(selectedIndex);
					model.getTitres().remove(selectedIndex);
					model.getTitres(). add(selectedIndex+1,songTomove);
					
					listeChansons.setListData(model.getTitres().toArray() );
					listeChansons.setSelectedIndex(Math.min(selectedIndex+1, model.getTitres().size()));
				}
				
			}
		});
		panelBoutonsListeTitres.add(btnMoveSongUp);
		panelBoutonsListeTitres.add(btnMoveSongDown);
		
		panelMaitreTitres.add(panelBoutonsListeTitres,BorderLayout.NORTH);
		
		
		panelMaitreTitres.add(new JScrollPane(listeChansons),BorderLayout.CENTER);
		listeChansons.setListData(model.getTitres().toArray() );
		listeChansons.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent lse) {
/*					System.out.println("----------------");
					System.out.println(((Chant)listeChansons.getSelectedValue()).getTitre());
					System.out.println("----------------");
*/					if ((!lse.getValueIsAdjusting())
						&& (listeChansons.getSelectedIndex() != -1)) {
					Chant c = (Chant)listeChansons.getSelectedValue();
					model.setTitre(c.getTitre());
					
					listePanelsCouplets.removeAll();
		        	for (Couplet monCouplet : c.getCouplets()) {
		        		listePanelsCouplets.add(new PanelEditCouplet(monCouplet,c));
		        		listePanelsCouplets.add(Box.createVerticalStrut(2));
					}
		        	
		        	titreText.setText(c.getTitre());

		        	listePanelsCouplets.revalidate();
		        	listePanelsCouplets.repaint();
					model.setCurrentlyEditingCouplet(null);
					model.setChanson(c);
					
					return;
				}
				
			}
			
		});
		listeChansons.setCellRenderer(new DefaultListCellRenderer () {
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				Chant c = (Chant)value;
				super.setText(c.getTitre());
				this.setToolTipText("<HTML><center/>"+c.getTitre()+"</HTML>");
				return this;
			}
		});
		
			
		
		co.add(panelMaitreTitres, BorderLayout.WEST);
		
		JPanel panelDetailsChanson = new JPanel();
		panelDetailsChanson.setLayout(new BorderLayout());
			
			JPanel panelResume = new JPanel();
			panelResume.setLayout(new BorderLayout());
			
			JLabel titreLabel = new JLabel("Titre : ");
	        
	        titreText.setMaximumSize(titreText.getPreferredSize());
	        titreText.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
				}

				@Override
				public void focusLost(FocusEvent e) {
					model.getChanson().setTitre(titreText.getText());
					listeChansons.repaint();
				}
	        	
	        });	        
	        Box hBox1 = Box.createHorizontalBox();
	        hBox1.add(titreLabel);
	        hBox1.add(Box.createHorizontalStrut(5));
	        hBox1.add(titreText);
	        
	        Box vBox = Box.createVerticalBox();
	        vBox.add(hBox1);
/*	        vBox.add(hBox2);
	        vBox.add(Box.createGlue());
	        vBox.add(hBox3);
*/			
	        panelResume.add(vBox,BorderLayout.CENTER);
	        panelDetailsChanson.add(panelResume,BorderLayout.NORTH);
	        
	        JPanel panelListeEtBoutonsCouplets = new JPanel();
	        panelListeEtBoutonsCouplets.setLayout(new BorderLayout());
	        	JPanel panelBoutonsListeCouplets = new JPanel();
	        	panelBoutonsListeCouplets.setLayout(new GridLayout(6,1,5,5));
	        	JButton btPlusCouplet = new JButton("+");
	        	btPlusCouplet.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						Chant c = (Chant)listeChansons.getSelectedValue();
						Couplet co = new Couplet("","");
						PanelEditCouplet paneltoBeAdded = new PanelEditCouplet(co,c);
						
						Couplet addAfterCouplet =null;
						for (int componentIndex = 0; componentIndex < listePanelsCouplets.getComponents().length-2; componentIndex++) {
						
							Component component = listePanelsCouplets.getComponents()[componentIndex];
							if (component instanceof PanelEditCouplet){
								if (((PanelEditCouplet) component).getCouplet()==model.getCurrentlyEditingCouplet()  ){
									addAfterCouplet = ((PanelEditCouplet) component).getCouplet();
									System.out.println("componentIndex : "+componentIndex );
									System.out.println("componentIndex/2 : "+componentIndex/2 );
									c.getCouplets().add((componentIndex/2)+1,co);
									listePanelsCouplets.add(Box.createVerticalStrut(2), componentIndex+2);
									listePanelsCouplets.add(paneltoBeAdded, componentIndex+2);
										
								}
							}
						}
						
						
						if(addAfterCouplet==null){
							c.getCouplets().add(co);
							listePanelsCouplets.add(paneltoBeAdded);
						}
						paneltoBeAdded.requestFocus();
						listePanelsCouplets.revalidate();
						listePanelsCouplets.repaint();
					}
	        	});
	        	panelBoutonsListeCouplets.add(btPlusCouplet);
	        	
	        	JButton btMoinsCouplet = new JButton("-");
	        	btMoinsCouplet.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						Chant c = (Chant)listeChansons.getSelectedValue();
						Couplet co=null;
						for (Component cmpt : listePanelsCouplets.getComponents()) {
							if (cmpt instanceof PanelEditCouplet){
								if (((PanelEditCouplet) cmpt).getCouplet()==model.getCurrentlyEditingCouplet() ){
									co = ((PanelEditCouplet) cmpt).getCouplet();
									c.getCouplets().remove(co);
									listePanelsCouplets.remove(cmpt);
								}
							}
						}
						if (co==null) System.err.println("aucun couplet supprimé");
						listePanelsCouplets.revalidate();
						listePanelsCouplets.repaint();
					}
	        	});
	        	model.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if (e.getActionCommand().equals("delete"))
							for (int componentIndex = 0; componentIndex < listePanelsCouplets.getComponents().length; componentIndex++) {
								Component cmpt = listePanelsCouplets.getComponents()[componentIndex];
							
								if (cmpt instanceof PanelEditCouplet){
									if (((PanelEditCouplet) cmpt).getCouplet()==e.getSource()){
										listePanelsCouplets.remove(componentIndex);
										listePanelsCouplets.remove(componentIndex);
									}
								}
							}
						else if (e.getActionCommand().equals("ChargementTermine")) {
							listeChansons.setListData(model.getTitres().toArray() );
							listeChansons.setSelectedIndex(0);
//							Chant c = (Chant)listeChansons.getSelectedValue();
//							jLCouplets.setListData(c.getCouplets().toArray());
//							jLCouplets.setSelectedIndex(0);
//							model.setTitre(c.getTitre());
							return;
						}
						listePanelsCouplets.revalidate();
						listePanelsCouplets.repaint();
					}
	        	});
	        	
	        	
	        	panelBoutonsListeCouplets.add(btMoinsCouplet);
	        	
	        	
	        	
	        	panelBoutonsListeCouplets.add( new JPanel());
	        	panelBoutonsListeCouplets.add( new JPanel());
	        	
	        	JButton btnMoveCoupletUp = new JButton("/\\");
	        	btnMoveCoupletUp.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						Couplet coupletToMove =null;
						for (int componentIndex = 0; componentIndex < listePanelsCouplets.getComponents().length; componentIndex++) {
						
							Component component = listePanelsCouplets.getComponents()[componentIndex];
							if (component instanceof PanelEditCouplet){
								if (((PanelEditCouplet) component).getCouplet()==model.getCurrentlyEditingCouplet() && componentIndex!=0 ){
									coupletToMove = ((PanelEditCouplet) component).getCouplet();
									
									//remove the compoennt and the horizonal line
									listePanelsCouplets.remove(componentIndex);
									listePanelsCouplets.remove(componentIndex);
									listePanelsCouplets.add(Box.createVerticalStrut(2), componentIndex-2);
									listePanelsCouplets.add(component, componentIndex-2);
									component.requestFocus();	
								}
							}
						}
						if (coupletToMove==null) System.err.println("aucun couplet supprimé");
						listePanelsCouplets.revalidate();
						listePanelsCouplets.repaint();
					}
	        		
	        	});
	        	JButton btnMoveCoupletDown = new JButton("\\/");
	        	
	        	btnMoveCoupletDown.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						Couplet coupletToMove =null;
						for (int componentIndex = 0; componentIndex < listePanelsCouplets.getComponents().length; componentIndex++) {
						
							Component component = listePanelsCouplets.getComponents()[componentIndex];
							if (component instanceof PanelEditCouplet){
								// && componentIndex!=(listePanelsCouplets.getComponents().length*2)-1 
								if (((PanelEditCouplet) component).getCouplet()==model.getCurrentlyEditingCouplet()){
									coupletToMove = ((PanelEditCouplet) component).getCouplet();
									
									//remove the compoennt and the horizonal line
								
									listePanelsCouplets.remove(componentIndex);
									listePanelsCouplets.remove(componentIndex);
									listePanelsCouplets.revalidate();
									
									listePanelsCouplets.add(Box.createVerticalStrut(2), componentIndex+2);
									listePanelsCouplets.add(component, componentIndex+2);
									component.requestFocus();	
									break;
								}
							}
						}
						if (coupletToMove==null) System.err.println("aucun couplet supprimé");
						listePanelsCouplets.revalidate();
						listePanelsCouplets.repaint();
					}
	        		
	        	});
	        	panelBoutonsListeCouplets.add(btnMoveCoupletUp);
	        	panelBoutonsListeCouplets.add(btnMoveCoupletDown);
	        	panelListeEtBoutonsCouplets.add(panelBoutonsListeCouplets,BorderLayout.EAST);

	        	
	        	JScrollPane coupletsListScrollPane = new JScrollPane(listePanelsCouplets);
	        	coupletsListScrollPane.getVerticalScrollBar().setUnitIncrement(32); 
	        	
	        	panelListeEtBoutonsCouplets.add(coupletsListScrollPane);
	        	
	        
	        panelDetailsChanson.add(panelListeEtBoutonsCouplets,BorderLayout.CENTER);
	        
	    co.add(panelDetailsChanson,BorderLayout.CENTER);
		
	}
	
	public static void main(String[] args) {
		JFrame jf= new EditChants();
		
      jf.setSize(800,600);
        jf.pack();
        jf.setExtendedState(MAXIMIZED_BOTH);

		jf.setVisible(true);

		jf.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	private int askForSave() {
		int result = JOptionPane.showConfirmDialog((Component) null, "Sauver les modifications dans le fichier "+model.getLastOpenedFile(),"alert", JOptionPane.YES_NO_CANCEL_OPTION);
		if (result==JOptionPane.YES_OPTION) model.sauverChansonsTxt(new File(model.getLastOpenedFile()));
		//TODO : demander quels sont les mdifications et inscrire dans le fichier en tant que commentaire.
		return result;
	}
	
}
