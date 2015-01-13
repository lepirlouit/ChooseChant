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
import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;



public class JFontChooser extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5781388177194138895L;

	/**
	 * Return value from showDialog method if OK is chosen.
	 **/
	public static final int OK_OPTION = 1;

	/**
	 * Return value from showDialog method if CANCEL is chosen.
	 **/
	public static final int CANCEL_OPTION = -1;

	/**
	 * This variable holds the return value from the dialog
	 * which we initially want to be set to cancel so that
	 * users don't try to get a valid Font from us, before
	 * they have chosen one.
	 */
	private int returnValue = CANCEL_OPTION;
	
	/**
	 * The ListPane instances which gives all the possible font names
	 * and is resbonsible for validating the users input.
	 */
	private ListPanel names = new ListPanel(this, "Font Name:",KeyEvent.VK_F,new PlainDocument(),GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames(),true);
	
	/**
	 * The ListPane instances which gives all the possible font styles
	 * and is resbonsible for validating the users input.
	 */
	private ListPanel styles = new ListPanel(this, "Font Style:",KeyEvent.VK_Y,new PlainDocument(),new String[]{"Regular","Italic","Bold","Bold Italic"},true);
	
	/**
	 * The ListPane instances which gives some likely font sizes.
	 */
	private ListPanel sizes = new ListPanel(this, "Size:",KeyEvent.VK_S,new NumericDocument(),new String[]{"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"},false);

	/**
	 * The component used to extend the basic font selector with a preview
	 * and maybe other things like color options, etc.
	 */
	private JComponent extension = new FontPreview.Basic();

	/**
	 * The currently selected font.
	 */
	
	private JButton jbtColor=null;
	private Color color = null;
	
	public Color getColor(){return color;}
	
	private Font font = null;
	
	/**
	 * Get the currently selected font.
	 */
	public Font getFont() { return font; }
	
	/**
	 * Specify the font that this chooser should display.
	 */
	public void setColor(Color c){
		this.color = c;
	}
	public void setFont(Font font)
	{
		//store the actual font
		this.font = font;
		
		//tell the extension panel that the font has changed
		extension.setFont(font);
	
		//update the font name list to reflect the current font
		names.setData(font.getFamily());
		
		//update the size list to reflect the current size
		sizes.setData(font.getSize()+"");
	
		//figure out what the string representation of the
		//font style is then update the list to reflect this
		switch(font.getStyle())
		{
			case Font.PLAIN:
				styles.setData("Regular");
				break;
			case Font.BOLD|Font.ITALIC:
				styles.setData("Bold Italic");
				break;
			case Font.BOLD:
				styles.setData("Bold");
				break;
			case Font.ITALIC:
				styles.setData("Italic");
				break;
		}
	}
	
	/**
	 * Used to build a String which can be decoded using
	 * <font>Font.decode(Font f)</code> to represent the current font.
	 * @return an encoded form of the current font. The format is FAMILY-STYLE-SIZE. 
	 */
	public String getEncodedFont()
	{
		//start building the encoded form by getting the family name
		String fKey = font.getFamily();
		
		//add on a string representation of the style
		switch(font.getStyle())
		{
			case Font.PLAIN:
				fKey+="-PLAIN-";
				break;
			case Font.BOLD|Font.ITALIC:
				fKey+="-GRASITALIQUE-";
				break;
			case Font.BOLD:
				fKey+="-GRAS-";
				break;
			case Font.ITALIC:
				fKey+="-ITALIQUE-";
				break;
		}
		
		//add on the size
		fKey+=font.getSize();
		
		//return the encoded form
		return fKey;
	}

	/**
	 * Create a new JFontChooser instance with the selected font
	 * defaulting to that used by JTextField.
	 */
	public JFontChooser()
	{
		//create a new instance using the TextField font
		//this(UIManager.getFont("TextField.font"));
	}
	
	/**
	 * Creates a new JFontChooser instance initialised to the
	 * font provided.
	 * @param font the initial font for the JFontChooser instance.
	 */
	public JFontChooser(Font font,Color c)
	{
		//store the fton
		setFont(font);
		setColor(c);	
		//create a panel to hold the three main selection lists
		JPanel lists = new JPanel();

		//set the layout of thre panel so the boxes flow from
		//left to right
		lists.setLayout(new BoxLayout(lists,BoxLayout.X_AXIS));

		//add the three selection lists to the panel
		lists.add(names);
		lists.add(Box.createHorizontalStrut(10));
		lists.add(styles);
		lists.add(Box.createHorizontalStrut(10));
		lists.add(sizes);

		//set the layout of the chooser itself
		setLayout(new BorderLayout());
		
		//add the selection lists to the top
		add(lists,BorderLayout.NORTH);
		
		//add the extension component to the center
		add(extension,BorderLayout.CENTER);
	}

	/**
	 * Allows the user to change the extension component (the bit that usually
	 * shows a preview of the font) enabling things like choosing color as
	 * well as family, style, and size. To reset to the original preview
	 * pass <code>null</code> as the component, or to remove just pass do
	 * <code>setExtensionComponent(new JPanel())</code>.
	 * @param extension the extension panel to display or null to reset
	 *        to the basic preview panel.
	 */
	public void setExtensionComponent(JComponent extension)
	{
		//remove the current extension component
		remove(this.extension);
		
		if (extension == null)
		{
			//if the supplied argument is null then revert
			//to the basic extension component
			this.extension = new FontPreview.Basic();
		}
		else
		{
			//otherwise store the new user-supplied extension component
			this.extension = extension;
		}
		
		//make sure the extension knows what the current font is
		this.extension.setFont(font);
		
		//add the extension panel to the bottom of the font chooser
		add(this.extension,BorderLayout.CENTER);
	}
	
	public int showDialog(Component parent)
	{
		//create a new dialog to embed the chooser in		
		final JDialog dialog = new JDialog();
		
		jbtColor=new JButton("  ");
		jbtColor.setBackground(color);
		jbtColor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Color currentColor = jbtColor.getBackground();
			    Color temp = JColorChooser.showDialog(null,"Choisisez une couleure",currentColor);
			    if (temp!=null){
			    	jbtColor.setBackground(temp);
			    	color=temp;
			    }
			}
		});
		
		
		//create an OK button with the correct text and mnemonic
		JButton btnOK = new JButton(UIManager.getString("OptionPane.okButtonText"),UIManager.getIcon("OptionPane.okIcon"));
		btnOK.setMnemonic(UIManager.getInt("OptionPane.okButtonMnemonic"));
		
		//add a listener for when the user clicks OK
		btnOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//remember that the user clicked OK
				returnValue = OK_OPTION;
				
				//get rid of the dialog
				dialog.setVisible(false);
			}
		});

		//create a cancel button with the correct text and mnemonic
		JButton btnCancel = new JButton(UIManager.getString("OptionPane.cancelButtonText"),UIManager.getIcon("OptionPane.cancelIcon"));
		btnCancel.setMnemonic(UIManager.getInt("OptionPane.cancelButtonMnemonic"));
		
		//add a listener for when the user clicks cancel
		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				//remember the user clicked cancel
				returnValue = CANCEL_OPTION;
				
				//get rid of the dialog
				dialog.setVisible(false);
			}
		});

		//create a panel to hold the buttons
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons,BoxLayout.X_AXIS));
		buttons.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		//add the buttons to the panel
		buttons.add(Box.createHorizontalGlue());
		buttons.add(jbtColor);
		buttons.add(btnOK);
		buttons.add(btnCancel);

		//set the dialog title
		dialog.setTitle("Select Font");
		
		//make the OK button the default button for the dialog
		dialog.getRootPane().setDefaultButton(btnOK);

		//create a place holder for the chooser
		JPanel placeHolder = new JPanel();
		
		//add some padding around the edges
		placeHolder.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		//store the chooser in its place holder
		placeHolder.add(this);
		
		//set the layout of the dialog
		dialog.getContentPane().setLayout(new BorderLayout());
		
		//add the chooser (in its place holder) to the dialog
		dialog.getContentPane().add(placeHolder,BorderLayout.CENTER);
		
		//add the button panel to the dialog
		dialog.getContentPane().add(buttons,BorderLayout.SOUTH);

		//instead of having a window listener to see if we clossed
		//rather than pressed cancel just assume that we will
		//cancel
		returnValue = CANCEL_OPTION;
		
		//make sure the dialog is modal
		dialog.setModal(true);

		//size everything correctly
		dialog.pack();
		
		//make sure the user can't resize the dialog
		dialog.setResizable(false);
		
		//set the location relative to the parent we were given
		dialog.setLocationRelativeTo(parent);

		//make the dialog visible
		dialog.setVisible(true);

		//now release the memory associated with the dialog
		dialog.dispose();

		//return whether the user clicked OK or Cancel
		return returnValue;
	}
	
	/**
	 * Called when the font has changed to determine what it has changed to.
	 */
	private void fontChanged()
	{
		//get the font name...
		String name = names.getData();
		
		//...style...
		String style = styles.getData();
		
		//...and size from the selection lists
		String size = sizes.getData();

		//massage the style value to one of the correct ones
		if (style == null || style.equals("Regular")) style = "Plain";

		//build the key redy for decoding
		String key = name+"-"+style.replaceAll(" ","")+"-"+size;
		
		//decode the key to create a font
		font = Font.decode(key);

		//tell the extension what the font is so it can update
		//any preview it maybe drawing
		extension.setFont(font);
	}

	/**
	 * This models a document which can only contain numbers, useful for allowing
	 * the user to enter an integer size for something in a JTextField rather
	 * than using an input mask/
	 */
	private static class NumericDocument extends PlainDocument
	{
        /**
		 * 
		 */
		private static final long serialVersionUID = 5659060935334009784L;

		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
        {
 			//if there is nothing to insert then just return 
			if (str == null || str.equals("")) return;

			//create an empty string to hold the chars we will
			//eventually insert
			String toInsert = "";

			//get the actual chars the user is tring to insert
			char[] chars = str.toCharArray();

			for (int i = 0; i < chars.length; i++)
			{
				//for each character only add it if it is a digit
				if (Character.isDigit(chars[i])) toInsert += chars[i];
			}

			//assuming there is actually something to insert then do so
			if (!toInsert.equals(""))  super.insertString(offs, toInsert, a);
		}
	}
	
	/**
	 * This class provides a JList and JTextField where the contents of the
	 * text field are validated against the data in the list.
	 */
	private static class ListPanel extends JComponent
	{
		//TODO
		//1) Instead of not selecting the entry whos prefix is in the text field
		//   we could offer auto-completion of some form

		/**
		 * 
		 */

		private static final long serialVersionUID = -4465367445411941628L;

		/**
		 * The color to use to highlight that there is an error in one of
		 * the selection areas.
		 */
		private static final Color ERR_COLOR = new Color(255,128,128);
		
		/**
		 * A flag so we know if another method is currently updating
		 * the text or the list. Should probably do some clever synchronization
		 * instead but this works so hey!
		 */
		private boolean updating = false;

		/**
		 * The text field into which the user can enter data
		 */
		private JTextField txt = null;

		/**
		 * The currently selected and validated data.
		 */
		private String selected = null;
		
		/**
		 * Gets this ListPanels currently selected and validated data item.
		 * @return the currently selected/validated item.
		 */
		protected String getData() { return selected; }
		
		/**
		 * Sets the currently selected data item
		 * @param data the item to select.
		 */
		protected void setData(String data)
		{
			//if data is null then don't do anything
			if (data == null) return;
			
			//make sure the text field knows about the new data item
			txt.setText(data);
			
			//store the new item for later
			selected = data;
		}

		/**
		 * Creates a new ListPanel instance to display the data.
		 * @param parent the font chooser this will become a part of
		 * @param title the title of the data being displayed
		 * @param mnemonic the mnemonic used to select the text field
		 * @param doc the Document used to control editing
		 * @param data the array of data to display in the list
		 * @param validate true of only values in the data are valid
		 */
		protected ListPanel(final JFontChooser parent, String title, int mnemonic, Document doc, final String[] data, final boolean validate)
		{
			//create and setup the panels label
			JLabel lbl = new JLabel(title);
			lbl.setAlignmentX(LEFT_ALIGNMENT);
			lbl.setDisplayedMnemonic(mnemonic);
			
			//create and init the text field
			txt = new JTextField(doc,"",10);
			txt.setAlignmentX(LEFT_ALIGNMENT);
			lbl.setLabelFor(txt);

			//create and init the list box
			final JList lst = new JList(data);
			lst.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			lst.addListSelectionListener(new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					if (!updating)
					{
						//if we are not already in the process of updating then...
						
						//we are now
						updating = true;
						
						//set the textfield to the selected list item
						txt.setText((String)lst.getSelectedValue());
						
						//make sure the background is the normal color
						txt.setBackground(UIManager.getColor("TextField.background"));
						
						//store the selected/validated data item
						selected = txt.getText();
						
						//inform the chooser that the font has changed
						parent.fontChanged();
						
						//we have finished updating
						updating = false;
					}
				}
			});

			txt.addFocusListener(new FocusListener()
			{
				public void focusGained(FocusEvent e) {}

				public void focusLost(FocusEvent e)
				{
					//see if the entered text is in the list box
					int index = indexOf(data,txt.getText());

					if (txt.getText().equals("") || (validate && index < 0))
					{
						//if the selcted text is blank or we are validating
						//and it's not in the list then show the err color
						txt.setBackground(ERR_COLOR);
					}
					else
					{
						//make sure the textfield looks OK
						txt.setBackground(UIManager.getColor("TextField.background"));
						
						//store the currently entered text
						selected = txt.getText();
						
						//tell the parent the font has changed
						parent.fontChanged();
						
						if (validate)
						{
							//if we are validating then make sure the selected item is both
							//selected and visible to the user
							lst.setSelectedIndex(index);
							lst.ensureIndexIsVisible(index);
						}
					}
				}
			});
			
			doc.addDocumentListener(new DocumentListener()
			{
				public void changedUpdate(DocumentEvent e){ updated(); }
				public void insertUpdate(DocumentEvent e){ updated(); }
				public void removeUpdate(DocumentEvent e){ updated(); }

				private void updated()
				{
					if (!updating)
					{
						// if we were not updating then...
						
						//we are now
						updating = true;
						
						if (txt.getText().equals(""))
						{
							//if there is no text in the document then
							
							//clear the list selection
							lst.clearSelection();
							
							//we aren't updating
							updating = false;
							
							//return without doing anything else
							return;
						}

						//get the index of the closest match in the list
						int index = lst.getNextMatch(txt.getText(),0,Position.Bias.Forward);
						
						//if we are not validating then select whatever the user has entered
						if (!validate) selected = txt.getText();
						
						if (index != -1)
						{
							//there is a partial match against the list so...
							
							//asure the entry is visible
							lst.ensureIndexIsVisible(index);
							
							//make sure the text field looks normal
							txt.setBackground(UIManager.getColor("TextField.background"));
							
							if (data[index].equalsIgnoreCase(txt.getText()))
							{
								//if this is an exact match then...
								
								//actually select the list item
								lst.setSelectedIndex(index);
								
								//actual select the item
								selected = (String)lst.getSelectedValue();
							}
							else
							{
								//it's not an exact match so don't select the list item
								lst.clearSelection();
							}
						}
						else
						{
							//there is no match so clear the list selection
							lst.clearSelection();
						}
						
						//tell the user the font has changed
						parent.fontChanged();
						
						//finish updating
						updating = false;
					}
				}
			});

			//set the layout to flow downwards
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			//setup a scroll pane to hold the list
			JScrollPane scroll = new JScrollPane(lst);
			scroll.setAlignmentX(LEFT_ALIGNMENT);

			//add the label, textfield and scroller to the panel
			add(lbl);
			add(txt);
			add(Box.createVerticalStrut(2));
			add(scroll);
		}

		/**
		 * Finds the index within an array of a given key
		 * @param data the array to search through
		 * @param key the key we are looking for
		 * @return the index of key within the data array or -1
		 */
		private int indexOf(String[] data, String key)
		{
			for (int i = 0 ; i < data.length ; ++i)
			{
				//if this item is the key then return the index
				if (data[i].equalsIgnoreCase(key)) return i;
			}

			//couldn't find the key so return -1
			return -1;
		}
	}
}
