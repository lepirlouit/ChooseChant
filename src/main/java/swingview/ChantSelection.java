package swingview;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;


import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import settings.ChantSettings;

import domaine.Chant;
import domaine.Couplet;


public class ChantSelection extends JPanel implements ActionListener,
		ListSelectionListener, DocumentListener {
	private ChantSettings model;

	/**
	 * 
	 */
	private static final long serialVersionUID = -1315790387511307369L;

	private JList jLTitres = new JList();

	private JList jLCouplets = new JList();

	private JPanel editorPanel;

	private JTextField jTFtitre = new JTextField();
	private JTextField jtfRecheche= new JTextField();
	private JCheckBox jcbIncouplets= new JCheckBox();

	private JTextArea jTACouplet = new JTextArea();

	private ChantParam panCom;

	public ChantSelection() {

		/* construction du listcouplet */

		jLCouplets.setCellRenderer(new DefaultListCellRenderer () {
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				super.getListCellRendererComponent(list, value, index,
						isSelected, cellHasFocus);
				Couplet c = (Couplet)value;
				super.setText(c.getIndic());
				String contenu = c.getContenu();
				contenu=contenu.replace("\n", "<BR>");
				this.setToolTipText("<HTML><center/>"+contenu+"</HTML>");
				return this;
			}
		});
		jLTitres.setCellRenderer(new DefaultListCellRenderer () {
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

		jLTitres.addListSelectionListener(this);
		jLCouplets.addListSelectionListener(this);
		jTFtitre.getDocument().addDocumentListener(this);
		jTACouplet.getDocument().addDocumentListener(this);

		this.setLayout(new GridLayout(2, 2));
		jLTitres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneTitres = new JScrollPane(jLTitres);
		// scrollPaneTitres.setPreferredSize(new Dimension(300,350));
		
		jtfRecheche.getDocument().addDocumentListener(this);
		
		jtfRecheche.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {jtfRecheche.setText("");}
			@Override
			public void focusLost(FocusEvent e) {}
		});
		JPanel recherchePanel = new JPanel();
		recherchePanel.setLayout(new BorderLayout());
		recherchePanel.add(jtfRecheche,BorderLayout.CENTER);
		jcbIncouplets.setSelected(true);
		recherchePanel.add(jcbIncouplets,BorderLayout.EAST);
		
		JPanel titresPanel = new JPanel();
		titresPanel.setLayout(new BorderLayout());
		titresPanel.add(scrollPaneTitres,BorderLayout.CENTER);
		titresPanel.add(recherchePanel,BorderLayout.NORTH);
		
		this.add(titresPanel);

		jLCouplets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPaneCouplets = new JScrollPane(jLCouplets);
		// scrollPaneCouplets.setPreferredSize(new Dimension(300,150));
		this.add(scrollPaneCouplets);

		// couplet.setPreferredSize(new Dimension(300,150));
		editorPanel = new JPanel();
		editorPanel.setLayout(new BorderLayout());

		editorPanel.add(jTFtitre, BorderLayout.NORTH);
		editorPanel.add(jTACouplet);

		this.add(editorPanel);

		this.add(panCom());

	}

	private JPanel panCom() {
		panCom = new ChantParam();

		return panCom;

	}

	public void setModel(ChantSettings model) {
		this.model = model;
		this.model.addActionListener(this);
		panCom.setModel(model);
	}

	// raction la saisie du rayon
	public void actionPerformed(ActionEvent e) {
		if (model == null)
			return; // si pas de model alors rien faire
		// si l'event est créé par le model:
		if (e.getSource() == model) {
			if (e.getActionCommand().equals("ChargementTermine")) {
				jLTitres.setListData(model.getTitres().toArray() );
				jLTitres.setSelectedIndex(0);
				Chant c = (Chant)jLTitres.getSelectedValue();
				jLCouplets.setListData(c.getCouplets().toArray());
				jLCouplets.setSelectedIndex(0);
				model.setTitre(c.getTitre());
				return;
			}

		}
	}

	public void valueChanged(ListSelectionEvent lse) {
		/*
		 * pour titres
		 * 
		 */
		if ((lse.getSource() == jLTitres) && (!lse.getValueIsAdjusting())
				&& (jLTitres.getSelectedIndex() != -1)) {
			Chant c = (Chant)jLTitres.getSelectedValue();
			jLCouplets.setListData(c.getCouplets().toArray());
			jLCouplets.setSelectedIndex(0);
			jTFtitre.setText(c.getTitre());
			model.setTitre(jTFtitre.getText());
			return;
		}

		/*
		 * 
		 * 
		 * pour couplets
		 * 
		 */
		if ((lse.getSource() == jLCouplets) && (!lse.getValueIsAdjusting())
				&& (jLCouplets.getSelectedIndex() != -1)) {
			jTACouplet.setText(((Couplet)jLCouplets.getSelectedValue()).getContenu());
			model.setContenu(jTACouplet.getText());
		}

	}

	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == jtfRecheche.getDocument())
			jLTitres.setListData(model.getTitres(jtfRecheche.getText(),jcbIncouplets.isSelected()).toArray());
		if (e.getDocument() == jTFtitre.getDocument())
			model.setTitre(jTFtitre.getText());
		if (e.getDocument() == jTACouplet.getDocument())
			model.setContenu(jTACouplet.getText());
	}

	public void insertUpdate(DocumentEvent e) {
		this.changedUpdate(e);
	}

	public void removeUpdate(DocumentEvent e) {
		this.changedUpdate(e);
	}

}
