package util;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JPanel;

public class JPanelListCellRenderer extends DefaultListCellRenderer{

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		//super.getListCellRendererComponent(list, value, index, isSelected, boolean);
		
		JPanel monPanel = (JPanel)value;
/*		for (MouseListener listener : list.getMouseListeners()) {
			monPanel.addMouseListener(listener);
		}
	*/	
        if(isSelected)        {
        	monPanel.setBackground(list.getSelectionBackground());
        	monPanel.setForeground(list.getSelectionForeground());

        }else{
        	monPanel.setBackground(list.getBackground());
        	monPanel.setForeground(list.getForeground());

        }
		return monPanel;
	}

}
