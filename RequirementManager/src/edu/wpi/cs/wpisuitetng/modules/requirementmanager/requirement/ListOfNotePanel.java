package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

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
		
		int totalHeight = 0;
		
		for (int i = 0; i<noteListModel.getSize(); i++)
		{
			String message = noteListModel.getElementAt(i).getMessage();
			NotePanel panel = new NotePanel(noteListModel.getElementAt(i).toString(),message );
			int maxnumlinesinmessage;
			if (message.length() <=22)
				maxnumlinesinmessage = 1;
			else
				maxnumlinesinmessage = (message.length() / 22 );
			maxnumlinesinmessage+=2;
			
			String newLine = "\n";
			int lastIndex = 0;
			int count =0;

			while(lastIndex != -1){

			       lastIndex = message.indexOf(newLine,lastIndex);

			       if( lastIndex != -1){
			             count ++;
			             lastIndex+=newLine.length();
			      }
			}
			maxnumlinesinmessage += count;
			
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			
			totalHeight += (maxnumlinesinmessage * 20);
			panel.setMaximumSize(new Dimension(((int)(dim.width) -600), (maxnumlinesinmessage * 20)));
			panel.revalidate();
			panel.repaint();
			
			this.add(panel);
		}

		this.setBackground(Color.WHITE);
		
		this.setPreferredSize(new Dimension(50, totalHeight));
		this.revalidate();
		this.repaint();
		

	}
	

}
