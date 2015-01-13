package util;
/************************************************************************
 *                 Copyright (C) 2005 Mark A. Greenwood                 *
 *      Developed by Mark A. Greenwood <m.greenwood@dcs.shef.ac.uk>     *
 *                                                                      *
 * This program is free software; you can redistribute it and/or modify *
 * it under the terms of the GNU General Public License as published by *
 * the Free Software Foundation; either version 2 of the License, or    *
 * (at your option) any later version.                                  *
 *                                                                      *
 * This program is distributed in the hope that it will be useful,      *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of       *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the        *
 * GNU General Public License for more details.                         *
 *                                                                      *
 * You should have received a copy of the GNU General Public License    *
 * along with this program; if not, write to the Free Software          *
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.            *
 ************************************************************************/


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * This class provides a simple preview of a given font. 
 * @author Mark A. Greenwood
 */
public class FontPreview extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9121895495953229256L;
	/**
	 * A Map instance to hold rendering hints such as antialias.
	 */
	private Map<RenderingHints.Key, Object> hints = new HashMap<RenderingHints.Key, Object>();
	
	/**
	 * Gets the rendering hints used by the preview.
	 * @return the previews rendering hints
	 */
	public Map<RenderingHints.Key, Object> getRenderingHints() { return hints; }

	/**
	 * The sample text to use in the preview.
	 */
	private String sampleText = null;
	
	/**
	 * Sets the sample text to use in the preview.
	 * @param sample the sample text or null to use the font family
	 *        as the sample.
	 */
	public void setSampleText(String sample) { sampleText = sample; }
	
	/**
	 * Gets the current sample text.
	 * @return the sample text or null if the font family is being
	 *         used as the sample text.
	 */
	public String getSampleText() { return sampleText; }
	
	/**
	 * The font color
	 */
	private Color fontColor = Color.BLACK;
	
	/**
	 * Sets the color used to draw the sample text.
	 * @param color the font color
	 */
	public void setFontColor(Color color) { fontColor = color; repaint(); }
	
	/**
	 * Get the font color.
	 * @return the color used to draw the sample.
	 */
	public Color getFontColor() { return fontColor; }
	
	/**
	 * Constructs a new font preview component which uses the
	 * font family as the preview text.
	 */
	public FontPreview() { this(null); }
	
	/**
	 * Constructs a new font preview component with a specific
	 * sample text.
	 * @param sample the sample text to use in the preview
	 */
	public FontPreview(String sample)
	{
		//store the sample text
		sampleText = sample;
		
		//make sure what we draw is what happens
		setOpaque(true);
		
		//set the border of the component to something sensible
		setBorder(UIManager.getBorder("TextField.border"));

		//set a sensible minimum size to make sure everything
		//is layed out correctly
		setSize(50,50);
		setMinimumSize(new Dimension(50,50));
		setPreferredSize(new Dimension(50,50));
	}

	/**
	 * Paints the preview panel to display the sample text.
	 */
	public void paintComponent(Graphics g)
	{
		//cast to Graphics2D so we can use it's features
		Graphics2D g2d = (Graphics2D)g;

		//add the rendering hints
		g2d.addRenderingHints(hints);

		//set the background color
		g2d.setColor(UIManager.getColor("TextField.background"));

		//fill in the preview panel
		g2d.fillRect(0,0,getWidth(),getHeight());

		//get the sample text
		String preview = (sampleText != null && !sampleText.equals("") ? sampleText : g2d.getFont().getFamily());

		//determine the correct X and Y to center the text correctly
		FontMetrics fm = g2d.getFontMetrics();
		int x = (getWidth()/2) - (fm.stringWidth(preview)/2);
		int y = (getHeight()/2) + (fm.getMaxAscent()/2);

		//the sample is too wide for the area so make sure it starts
		//at the left hand side
		if (x < 0) x = 0;

		//draw the baseline in a light grey color
		//TODO should get a sensible color from UIManager
		g2d.setColor(new Color(224,223,227));
		g2d.drawLine(0,y,getWidth(),y);

		//set the font color
		g2d.setColor(fontColor);
		
		//draw the sample text
		g2d.drawString(preview,x,y);
	}
	
	/**
	 * A basic extension component for use with JFontChooser that simply
	 * shows a preview of the currently selected font.
	 * @author Mark A. Greenwood
	 */
	public static class Basic extends JComponent
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6608642797004801616L;
		/**
		 * The preview component that actually does all the work
		 */
		private FontPreview preview = new FontPreview();
		
		/**
		 * Creates a new basic extension component.
		 */
		public Basic()
		{
			//set the layout
			setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			
			//create a Preview: label
			JLabel lblPreview = new JLabel("Preview:");
			lblPreview.setAlignmentX(LEFT_ALIGNMENT);
			preview.setAlignmentX(LEFT_ALIGNMENT);
			
			//add the label and preview area to the component
			add(lblPreview);
			add(preview);
			
			//pad the edge a little
			setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
		}
		
		/**
		 * Updates the font of the preview area.
		 * @param font the new font to preview.
		 */
		public void setFont(Font font)
		{
			//set our own font
			super.setFont(font);
			
			//if there is a preview component then
			//set its font as well
			if (preview != null) preview.setFont(font);
		}
	}
	
	/**
	 * An extended extension component for use with JFontChooser that allows the
	 * user to specify the font color and antitalias options as well as showing
	 * a preview of the currently selected font.
	 * @author Mark A. Greenwood
	 */
	public static class Extended extends JComponent
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2264919166967889243L;

		/**
		 * The preview area
		 */
		private FontPreview preview = new FontPreview();
		
		/**
		 * The check box for antialias
		 */
		private JCheckBox cbAntialias = new JCheckBox("Antialias");
		
		/**
		 * Create a new extended component showing both color and antialias.
		 *
		 */
		public Extended()
		{
			this(true,true);			
		}
		
		/**
		 * Create a new extended extension component.
		 * @param color if true then the user can set the font color
		 * @param antialias if true the user can turn antialiasing on and off
		 */
		public Extended(boolean color, boolean antialias)
		{
			//set the layout
			setLayout(new BorderLayout());
			
			//init the preview component 
			preview.setAlignmentX(LEFT_ALIGNMENT);
			
			//create a panel to hold the extended options
			JPanel options = new JPanel();
			options.setLayout(new BoxLayout(options,BoxLayout.X_AXIS));
			options.setAlignmentX(LEFT_ALIGNMENT);
						
			cbAntialias.setAlignmentX(LEFT_ALIGNMENT);
			cbAntialias.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent e)
				{
					//set the antialias option based on the
					//value if the check box then...
					
					if (cbAntialias.isSelected())
						preview.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					else
						preview.getRenderingHints().put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
					
					//force a repaint of the preview panel
					preview.repaint();
				}
			});
						
			//create and init a font color label
			JLabel lblColor = new JLabel("Font Color:");
			lblColor.setAlignmentX(LEFT_ALIGNMENT);
			
			//create and init the color preview area
			final JLabel colorArea = new JLabel("                       ");
			colorArea.setBorder(UIManager.getBorder("TextField.border"));
			colorArea.setOpaque(true);
			colorArea.setBackground(preview.getFontColor());
			colorArea.setToolTipText("Click to change font color");
			
			//create and init a color chooser
			final JColorChooser cc = new JColorChooser();
			cc.setPreviewPanel(new JPanel());
			
			colorArea.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent me)
				{
					//when the user clicks the color preview show the
					//color chooser
					cc.setColor(preview.getFontColor());
					
					//create a dialog to hold the color chooser
					JDialog dialog = JColorChooser.createDialog(Extended.this.getParent(),
                            "Select Font Color",
                            true,  //modal
                            cc, //the color chooser
                            new ActionListener()
                            {
								public void actionPerformed(ActionEvent ae)
								{
									//if the user clicks OK then update the current color
									colorArea.setBackground(cc.getColor());
									preview.setFontColor(cc.getColor());
								}
                            },
                            null); //don't do anything on cancel
					
					//show the dialog
					dialog.setVisible(true);
					
					//then free up the memory when we have finished with it
					dialog.dispose();
				}
			});					
						
			if (color)
			{
				//if the color option is to be displayed then
				//add it to the options panel
				options.add(lblColor);
				options.add(Box.createHorizontalStrut(2));
				options.add(colorArea);
				options.add(Box.createHorizontalStrut(20));
			}
			
			if (antialias)
			{
				//if the antialias option is being used add it to the options panel				
				options.add(cbAntialias);
			}
			
			//set the border
			options.setBorder(BorderFactory.createEmptyBorder(0,10,0,10));
			
			//create some heading labels
			JLabel lblPreview = new JLabel("Preview:");
			JLabel lblEffects = new JLabel("Effects:");
			lblPreview.setAlignmentX(LEFT_ALIGNMENT);
			lblEffects.setAlignmentX(LEFT_ALIGNMENT);
			
			//create a panel to hold the preview			
			JPanel pp = new JPanel();
			pp.setLayout(new BoxLayout(pp,BoxLayout.Y_AXIS));
			
			//add the preview stuff to the panel
			pp.add(lblPreview);
			pp.add(preview);
					
			//create a panel to hold the effects
			JPanel ep = new JPanel();
			ep.setLayout(new BoxLayout(ep,BoxLayout.Y_AXIS));
			ep.setBorder(BorderFactory.createEmptyBorder(0,0,2,0));
			
			//add the effects stuff to the panel
			ep.add(lblEffects);
			ep.add(options);
			
			//add the effects and preview panels
			add(ep,BorderLayout.NORTH);
			add(pp,BorderLayout.CENTER);
			
			//set the border of this component
			setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
		}
		
		/**
		 * Set the font color.
		 * @param color the new font color.
		 */
		public void setFontColor(Color color) { preview.setFontColor(color); }
		
		/**
		 * Get the current font color.
		 * @return the font color
		 */
		public Color getFontColor() { return preview.getFontColor(); }
		
		/**
		 * Find out if antialising is selected
		 * @return true if the user wants antialiasing false otherwise
		 */
		public boolean isAntialised() { return cbAntialias.isSelected(); }
		
		/**
		 * Set the antialising option.
		 * @param value if true then antialising check box will be
		 *        selected.
		 */
		public void setAntialised(boolean value) { cbAntialias.setSelected(value); }
		
		
		/**
		 * Updates the font of the preview area.
		 * @param font the new font to preview.
		 */
		public void setFont(Font font)
		{
			//set our own font
			super.setFont(font);
			
			//if there is a preview component then
			//set its font as well
			if (preview != null) preview.setFont(font);
		}
	}
}
