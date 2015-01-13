package swingview;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import settings.ChantSettings;

import domaine.Chant;
import domaine.Couplet;


public class PanelEditCouplet extends JPanel implements FocusListener{

	private static final long serialVersionUID = 1L;
	private Couplet couplet;
	private Chant chant;
	final JTextArea jtaContenuCouplet;
	
	public PanelEditCouplet(Couplet theCouplet,Chant chant){
		super();
		this.couplet = theCouplet;
		this.chant = chant;
		setBackground(new JList().getBackground());
		setForeground(new JList().getForeground());
		
		JPanel innerPanel = new JPanel();
		innerPanel.setSize(400, 200);
		innerPanel.setPreferredSize(new Dimension(400,200));
		innerPanel.setMaximumSize(this.getPreferredSize());
		innerPanel.setMinimumSize(this.getPreferredSize());
		innerPanel.setLayout(new BorderLayout());
		
		
        JLabel prenomLabel = new JLabel("ref-cpl : ");
        prenomLabel.setToolTipText("intitullé du couplet");
        final JTextField jtfRefCpl = new JTextField(75);
        jtfRefCpl.setMaximumSize(jtfRefCpl.getPreferredSize());
        jtfRefCpl.addFocusListener(this);

        Box hBoxRefCpl = Box.createHorizontalBox();
        hBoxRefCpl.setBackground(Color.BLACK);
        hBoxRefCpl.add(prenomLabel);
        hBoxRefCpl.add(Box.createHorizontalStrut(5));
        hBoxRefCpl.add(jtfRefCpl);
        
        innerPanel.add(hBoxRefCpl, BorderLayout.NORTH);
        
        
        jtaContenuCouplet= new JTextArea();
        jtaContenuCouplet.addFocusListener(this);
		
        jtfRefCpl.setText(theCouplet.getIndic());
        jtaContenuCouplet.setText(theCouplet.getContenu());
        
        innerPanel.add(new JScrollPane(jtaContenuCouplet), BorderLayout.CENTER);
        
        this.setLayout(new FlowLayout());
        this.setMaximumSize(new Dimension(4000,210));
        this.add(innerPanel);
        this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PanelEditCouplet.this.requestFocus();
				
			}			

		});
        
        
	}
	public Couplet getCouplet() {
		return couplet;
	}
	public void setCouplet(Couplet couplet) {
		this.couplet = couplet;
	}
	public Chant getChant() {
		return chant;
	}
	public void setChant(Chant thisChant) {
		this.chant = thisChant;
	}
	@Override
	public void focusGained(FocusEvent e) {
		setBackground(new JList().getSelectionBackground());
		setForeground(new JList().getSelectionForeground());
		ChantSettings.getInstance().setCurrentlyEditingCouplet(couplet);
	}
	@Override
	public void focusLost(FocusEvent e) {
		setBackground(new JList().getBackground());
		setForeground(new JList().getForeground());
		if (e.getSource() instanceof JTextField ){
			couplet.setIndic(((JTextField)e.getSource()).getText());
		}else if (e.getSource() instanceof JTextArea ){
			couplet.setContenu(((JTextArea)e.getSource()).getText());
		}
		
		//-------clean chant----------
		if (couplet.getIndic().trim().equals("")&&couplet.getContenu().trim().equals("")){
			chant.getCouplets().remove(couplet);
			ChantSettings.getInstance().traiterEvent(new ActionEvent(couplet,1,"delete"));
		}
		
		
		//repaint();

	}
	
	@Override
	public void requestFocus(){
		jtaContenuCouplet.requestFocus();
		
	}
	
	
	


	
	
	
}
