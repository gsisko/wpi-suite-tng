package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

@SuppressWarnings("serial")
public class NumValidation extends AbstractCellEditor implements TableCellEditor {

JComponent component = new JTextField();

public Component getTableCellEditorComponent(JTable table, Object value,
boolean isSelected, int row, int col) {

DocumentFilter dfilter = new DataFilter();
	
((AbstractDocument) ((JTextField) component).getDocument()).setDocumentFilter(dfilter);

((JTextField) component).setText((String) value);

return component;
}
public Object getCellEditorValue() {
return ((JTextField) component).getText();
}
}