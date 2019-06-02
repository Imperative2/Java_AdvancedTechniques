package note;

import java.awt.Dimension;
import java.beans.Customizer;
import java.util.GregorianCalendar;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ziarenka.ChartBean;

import javax.swing.JButton;

public class NoteBeanBeanCustomizer extends JTabbedPane implements Customizer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldTitle;
	private JTextField textFieldX;
	private JTextField textFieldY;
	private JTextField textFieldStartingDate;
	
	
	
	private NoteBean bean;
	
	public NoteBeanBeanCustomizer() 
	{
		
		JPanel panel = new JPanel();
		addTab("Title", null, panel, null);
		panel.setLayout(null);
		
		JLabel fixLabelNewTitle = new JLabel("New title: ");
		fixLabelNewTitle.setBounds(45, 67, 91, 16);
		panel.add(fixLabelNewTitle);
		
		textFieldTitle = new JTextField();
		textFieldTitle.setBounds(119, 64, 116, 22);
		panel.add(textFieldTitle);
		textFieldTitle.setColumns(10);
		
		JButton btnTitleUpdate = new JButton("Update");
		btnTitleUpdate.setBounds(96, 119, 97, 25);
		panel.add(btnTitleUpdate);
		
		JPanel panel_1 = new JPanel();
		addTab("Area size", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel fixLabelDimX = new JLabel("Dimension x:");
		fixLabelDimX.setBounds(58, 67, 74, 16);
		panel_1.add(fixLabelDimX);
		
		JLabel fixLabelDimY = new JLabel("Dimension y:");
		fixLabelDimY.setBounds(58, 115, 74, 16);
		panel_1.add(fixLabelDimY);
		
		textFieldX = new JTextField();
		textFieldX.setBounds(157, 64, 116, 22);
		panel_1.add(textFieldX);
		textFieldX.setColumns(10);
		
		textFieldY = new JTextField();
		textFieldY.setBounds(157, 112, 116, 22);
		panel_1.add(textFieldY);
		textFieldY.setColumns(10);
		
		JButton btnUpdateArea = new JButton("Update Area size");
		btnUpdateArea.setBounds(92, 147, 136, 25);
		panel_1.add(btnUpdateArea);
		
		JPanel panel_2 = new JPanel();
		addTab("Starting date", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel fixLabelStartingDate = new JLabel("Starting date:");
		fixLabelStartingDate.setBounds(52, 70, 91, 16);
		panel_2.add(fixLabelStartingDate);
		
		textFieldStartingDate = new JTextField();
		textFieldStartingDate.setBounds(168, 67, 116, 22);
		panel_2.add(textFieldStartingDate);
		textFieldStartingDate.setColumns(10);
		
		JButton btnUpdateDate = new JButton("Update starting date");
		btnUpdateDate.setBounds(75, 102, 167, 25);
		panel_2.add(btnUpdateDate);
		
		btnTitleUpdate.addActionListener((e) -> {
			if (bean == null)
				return;
			bean.setTitle(textFieldTitle.getText());
			firePropertyChange("title", "d", textFieldTitle.getText());
		});
		
		btnUpdateArea.addActionListener((e) -> {
			if (bean == null)
				return;
			Dimension dim = new Dimension(Integer.parseInt(textFieldX.getText()), Integer.parseInt(textFieldY.getText()));
			bean.setTextFieldSize(dim);
		});
		
		btnUpdateDate.addActionListener((e) ->{
			
			String[] data = textFieldStartingDate.getText().split("-");
			GregorianCalendar newCalendar = new GregorianCalendar(Integer.parseInt(data[0]),
					Integer.parseInt(data[1]), Integer.parseInt(data[2]));
			bean.setStarting(newCalendar);
		});
	}

	@Override
	public void setObject(Object obj)
	{
		bean = (NoteBean) obj;

		String title = bean.getTitle();
		textFieldTitle.setText(title);

		Dimension dim = bean.getTextFieldSize();
		textFieldX.setText(dim.getWidth()+"");
		textFieldY.setText(dim.getHeight()+"");

		GregorianCalendar start = bean.getStarting();
		String cd = start.get(start.DAY_OF_MONTH)+"-"+start.get(start.MONTH)+"-"+start.get(start.YEAR);
		textFieldStartingDate.setText(cd);
		
	}
}
