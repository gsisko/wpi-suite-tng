package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class NotePanel extends JPanel{
	
	private JTextArea content;
	private String title;
	
	public NotePanel(String noteTitle, String message){
		title = noteTitle;
		
		this.setBackground(Color.white);
		this.setOpaque(true);
		
        content =  new JTextArea(message);
        
        content.setWrapStyleWord(true);
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
		content.setFont(content.getFont().deriveFont(9));
        
		TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), noteTitle);
		titleBorder.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION);
		titleBorder.setTitlePosition(TitledBorder.BOTTOM);
		titleBorder.setTitleFont(content.getFont().deriveFont(Font.ITALIC));
		titleBorder.setTitleColor(Color.gray);

		setBorder(  BorderFactory.createCompoundBorder(	(BorderFactory.createEmptyBorder(5, 5, 5, 5)),
														BorderFactory.createCompoundBorder(titleBorder,
																							(BorderFactory.createEmptyBorder(5, 5, 5, 5)) )  ));

		add(content);
		this.add(Box.createRigidArea(new Dimension(0,5)));
	}
	
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the content
	 */
	public JTextArea getContent() {
		return content;
	}

}
