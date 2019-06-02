package note;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;

public class StartDatePanel extends JPanel
{
	 private PropertyEditorSupport editor;
	 private JTextField tfDate;
	private static final long serialVersionUID = 1L;
	 
	public StartDatePanel(PropertyEditorSupport ed)
	{
		editor = ed;
		setBorder(new LineBorder(new Color(0, 0, 0), 7));
		setLayout(null);
		
		this.setPreferredSize(new Dimension(280,110));
		
		JLabel lblEnterStartingDate = new JLabel("ENTER STARTING DATE:");
		lblEnterStartingDate.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblEnterStartingDate.setBounds(53, 13, 166, 16);
		add(lblEnterStartingDate);
		
		tfDate = new JTextField();
		tfDate.setBounds(53, 39, 182, 22);
		add(tfDate);
		tfDate.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(76, 68, 97, 25);
		add(btnUpdate);
		
		btnUpdate.addActionListener((e) ->{
			String[] data = tfDate.getText().split("-");
	//		System.out.println(data[0]+" "+data[1]+" "+data[2]);
			int year = Integer.parseInt(data[0]);
			int month = Integer.parseInt(data[1]);
			int dayOfMonth = Integer.parseInt(data[2]);
			GregorianCalendar date = new GregorianCalendar(year, month, dayOfMonth);
			editor.setValue(date);
			editor.firePropertyChange();
		});
		
	}
}
