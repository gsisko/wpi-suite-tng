package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;

@SuppressWarnings("serial")
public class ListOfNotePanel extends JPanel {
	
	private NoteListModel noteListModel;

	
	/**
	 * The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent	The parent of this tab
	 */
	public ListOfNotePanel() {
		


		// Set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//Create the layout
		
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		// Construct list box
		// Construct the list box model
		noteListModel = new NoteListModel();
		
		Note message1 = new Note("message 1");
		message1.setUser(new User("DummyUser","","",-60));
		
		Note message2 =new Note("message 2\n blargarble \n testing 123 \n new line!");
		message2.setUser(new User("DummyUser","","",-60));
		
		Note message3 = new Note("message 3 ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
		message3.setUser(new User("DummyUser","","",-60));
		
		Note message4 = new Note("message 4 the quick brown fox jumps over the lazy dog the quick brown fox jumps over the lazy dog the quick brown fox jumps over the lazy dog the quick brown fox jumps over the lazy dog the quick brown fox jumps over the lazy dog ");
		message4.setUser(new User("DummyUser","","",-60));

		noteListModel.addMessage(message1);
		noteListModel.addMessage(message2);
		noteListModel.addMessage(message3);
		noteListModel.addMessage(message4);
		
		for (int i = 0; i<noteListModel.getSize(); i++)
		{
			NotePanel panel = new NotePanel(noteListModel.getElementAt(i).toString(), noteListModel.getElementAt(i).getMessage());
			this.add(panel);
		}

	
		this.setPreferredSize(new Dimension(580, 300));
		this.revalidate();
		this.repaint();
		

	}
	

}
