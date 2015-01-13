package swingview.diapo;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import settings.ChantSettings;


public class ListPhotosDiaporama extends JPanel implements ActionListener,ListSelectionListener{
	private static final long serialVersionUID = 8554210262463219684L;
	private DefaultListModel stringsDesPhotos;//le string des images

	private ChantSettings model;
	private Timer timer;
	private JList jlSelection;

	public ListPhotosDiaporama(ChantSettings model) {
		this();
		this.setModel(model);
	}
	public ListPhotosDiaporama() {
		stringsDesPhotos=new DefaultListModel();
		
		timer = new Timer(3000,this);
		this.setLayout(new BorderLayout());
		this.setToolbar();
		
		//jlSelection = new JList(model.getImagesDesPhotos());
		jlSelection = new JList();
		jlSelection.addListSelectionListener(this);
		JScrollPane jsp = new JScrollPane(jlSelection);
		jsp.setPreferredSize(new Dimension(250,640));
		this.add(jsp);
		this.setVisible(true);
	}
	
	private void setToolbar() {
		JPanel toolbar = new JPanel();
		JButton importe,play, stop , pause;
		JSpinner jspDelay;
		importe = new JButton(new ImageIcon("bin/icons/importe.jpg"));
		play = new JButton(new ImageIcon("bin/icons/play.jpg"));
		stop = new JButton(new ImageIcon("bin/icons/stop.jpg"));
		pause = new JButton(new ImageIcon("bin/icons/pause.jpg"));
		importe.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clickSurAdd();
			}
		});
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clickSurPlay();
			}

		});
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clickSurStop();
			}
		});
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				clickSurPause();
			}
		});
		
		jspDelay=new JSpinner(new SpinnerNumberModel(3000, 0, Integer.MAX_VALUE, 1000));
		jspDelay.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				timer.setDelay(   ((SpinnerNumberModel)(((JSpinner)e.getSource()).getModel())).getNumber().intValue()    );
				timer.setInitialDelay(   ((SpinnerNumberModel)(((JSpinner)e.getSource()).getModel())).getNumber().intValue()    );
			}
			
		});
		
		importe.setPreferredSize(new Dimension(24,24));
		play.setPreferredSize(new Dimension(24,24));
		stop.setPreferredSize(new Dimension(24,24));
		pause.setPreferredSize(new Dimension(24,24));
		jspDelay.setPreferredSize(new Dimension(60,24));
		
		toolbar.add(importe);
		toolbar.add(play);
		toolbar.add(stop);
		toolbar.add(pause);
		toolbar.add(jspDelay);
		
		this.add(toolbar,BorderLayout.NORTH);
	}

	protected void setDelay() {
		// TODO Auto-generated method stub
		
	}

	protected void clickSurPlay() {
		timer.start();
	}

	protected void clickSurPause() {
		timer.stop();
	}

	public void clickSurStop() {
		timer.stop();
	}

	public void setModel(ChantSettings model) {
		if (model == null) return;
		this.model = model;
		// enregistre la vue comme tant  l'écoute du modle
		//this.model.addActionListener(this);
	}
	private void addPhotosToListe(File[] photos){
		DefaultListModel listeATraiter=new DefaultListModel();
		addEltsToListe(listeATraiter,photos);
		for(int i=0;i< listeATraiter.getSize();i++){
			if (((File)listeATraiter.getElementAt(i)).isDirectory()){
				addEltsToListe(listeATraiter, ((File)listeATraiter.getElementAt(i)).listFiles());
			}else if (((File)listeATraiter.getElementAt(i)).getAbsolutePath().toLowerCase().matches("(.*).jpg|(.*).png")){
				stringsDesPhotos.addElement(((File)listeATraiter.getElementAt(i)).getAbsolutePath());
				if(model == null) System.out.println("Model is null");
				else model.getImagesDesPhotos().add(null);
			}
		}
		jlSelection.setModel(stringsDesPhotos);
	}

	private void addEltsToListe(DefaultListModel list, File[] photos) {
		for (int i = 0; i< photos.length;i++){
			list.addElement(photos[i]);
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			jlSelection.setSelectedIndex((jlSelection.getSelectedIndex()+1)%jlSelection.getModel().getSize()); 
		}
	}
	
	private void clickSurAdd(){
		JFileChooser fichierCh = new  JFileChooser();
		fichierCh.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fichierCh.setMultiSelectionEnabled(true);
		fichierCh.setApproveButtonText("Ajouter");
		int rep = fichierCh.showDialog(this,"Ouvrir") ;
		if (rep == JFileChooser.APPROVE_OPTION)addPhotosToListe(fichierCh.getSelectedFiles());
		
		
	}
	
	public static void main(String[] args){
		JFrame jf =new JFrame();
		ListPhotosDiaporama lpd=new ListPhotosDiaporama(ChantSettings.getInstance()); 
		jf.setContentPane(lpd);
		jf.setVisible(true);
		jf.pack();
		
	}

	public void valueChanged(ListSelectionEvent e) {
		int index = jlSelection.getSelectedIndex();
		if (model==null) return;
		try{
			model.getImagesDesPhotos().setElementAt(null,index-1);
			System.out.println("Destroy : "+(index-1));
		}catch(ArrayIndexOutOfBoundsException exep){
			
		}
		if(model.getImagesDesPhotos().get(index)==null){
			model.getImagesDesPhotos().setElementAt(Toolkit.getDefaultToolkit().getImage((String) stringsDesPhotos.getElementAt(index) ),index);
			System.out.println("load element : "+(index));
		}
		model.getImagesDesPhotos().setElementAt(Toolkit.getDefaultToolkit().getImage( (String)stringsDesPhotos.getElementAt(index+1)),index+1);
		System.out.println("load element : "+(index+1));
		
		if(e.getSource()==jlSelection) model.setImageFromDiapo(model.getImagesDesPhotos().get(index));
		
	}
	
}
