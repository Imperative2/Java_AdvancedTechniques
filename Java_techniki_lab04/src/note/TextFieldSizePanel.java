package note;

import java.beans.PropertyEditorSupport;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Dimension;

public class TextFieldSizePanel extends JPanel
{
	 private PropertyEditorSupport editor;
	 private JTextField tfX;
	 private JTextField tfY;
	 
	public TextFieldSizePanel(PropertyEditorSupport ed)
	{
		editor = ed;
		setLayout(null);
		
		
		this.setPreferredSize(new Dimension(285, 205));
		
		JLabel lblDimensionX = new JLabel("Dimension x:");
		lblDimensionX.setBounds(44, 63, 113, 16);
		add(lblDimensionX);
		
		tfX = new JTextField();
		tfX.setBounds(128, 60, 116, 22);
		add(tfX);
		tfX.setColumns(10);
		
		JLabel lblDimensionY = new JLabel("Dimension y:");
		lblDimensionY.setBounds(44, 97, 80, 16);
		add(lblDimensionY);
		
		tfY = new JTextField();
		tfY.setBounds(128, 95, 116, 22);
		add(tfY);
		tfY.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(86, 150, 97, 25);
		add(btnUpdate);

		
		btnUpdate.addActionListener((e) -> {
			Dimension dim = new Dimension(Integer.parseInt(tfX.getText()),Integer.parseInt(tfY.getText()));
			editor.setValue(dim);
			editor.firePropertyChange();
		});
	}
}
