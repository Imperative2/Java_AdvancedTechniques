package note;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextArea;

public class NoteBean extends JPanel implements Serializable
{
	private String title = "";
	private Dimension textFieldSize;
	private GregorianCalendar starting;
	private GregorianCalendar current;


	private JLabel labelTitle;
	private JLabel lblDate;
	private JTextArea textArea;
	
	private List<Message> messagesList = new ArrayList<>();
	private int index = 0;
	private List<JButton> buttonsList = new ArrayList<>();
	private List<JLabel> labelsList = new ArrayList<>();

	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private VetoableChangeSupport vetoSupport
	 = new VetoableChangeSupport(this);

	public NoteBean()
	{
		starting = new GregorianCalendar(1997, 07, 03);
		current = starting;
		
		setLayout(null);
		labelTitle = new JLabel();
		labelTitle.setFont(new Font("Tahoma", Font.BOLD, 27));
		labelTitle.setBounds(12, 0, 247, 33);
		labelTitle.setText("sdfsdf");
		
		
		Message initMessage = new Message();
		initMessage.setCurrent(current);
		
		messagesList.add(initMessage);

		this.setPreferredSize(new Dimension(300, 400));
		this.add(labelTitle);

		JButton btnPrev = new JButton("<<");
		btnPrev.setBounds(12, 43, 56, 25);
		add(btnPrev);

		JButton btnNext = new JButton(">>");
		btnNext.setBounds(232, 43, 56, 25);
		add(btnNext);
		
		lblDate = new JLabel(getCurrentDateString());
		lblDate.setBounds(101, 47, 89, 16);
		add(lblDate);
		
		textArea = new JTextArea();
		textArea.setBounds(43, 197, 216, 25);
		add(textArea);
		
		JButton btnAddMessage = new JButton("Add Message");
		btnAddMessage.setBounds(91, 362, 109, 25);
		add(btnAddMessage);
		
		btnPrev.addActionListener((e) -> {
			
			for(JButton button : buttonsList)
			{
				button.setVisible(false);
			}
			
			for(JLabel label : labelsList)
			{
				label.setVisible(false);
			}
			
			buttonsList.clear();
			labelsList.clear();
			
			
			
			Message oldMessage = messagesList.get(index);
			if(index-1 < 0)
			{
				index = 0;
				GregorianCalendar oldDate = oldMessage.getCurrent();
				GregorianCalendar newDate = (GregorianCalendar)oldDate.clone();
				newDate.add(GregorianCalendar.DAY_OF_YEAR, -1);
				System.out.println("old date: "+oldDate.get(GregorianCalendar.DAY_OF_YEAR));
				System.out.println("new  date: "+newDate.get(GregorianCalendar.DAY_OF_YEAR));
				
				Message newMessage = new Message();
				messagesList.add(0, newMessage);
				newMessage.setCurrent(newDate);
				updateVisuals();
				return;
			}
			else
			{
				index = index -1;
				updateVisuals();
			}
				
		});
		
		
		btnNext.addActionListener((e) -> {
			
			for(JButton button : buttonsList)
			{
				button.setVisible(false);
			}
			
			for(JLabel label : labelsList)
			{
				label.setVisible(false);
			}
			
			buttonsList.clear();
			labelsList.clear();
			
			
			
			Message oldMessage = messagesList.get(index);
			if(index+1 == messagesList.size())
			{
				index =  messagesList.size();
				GregorianCalendar oldDate = oldMessage.getCurrent();
				GregorianCalendar newDate = (GregorianCalendar)oldDate.clone();
				newDate.add(GregorianCalendar.DAY_OF_YEAR, +1);
				System.out.println("old date: "+oldDate.get(GregorianCalendar.DAY_OF_YEAR));
				System.out.println("new  date: "+newDate.get(GregorianCalendar.DAY_OF_YEAR));
				
				Message newMessage = new Message();
				messagesList.add( newMessage);
				newMessage.setCurrent(newDate);
				updateVisuals();
				return;
			}
			else
			{
				index = index +1;
				updateVisuals();
			}
				
		});
		
		
		btnAddMessage.addActionListener((e) ->{
			String text = textArea.getText();
			Message message = messagesList.get(index);
			message.addMessage(text);
			updateVisuals();
		});
		
		
	}
	
	public String getCurrentDateString()
	{
		String cd = current.get(current.DAY_OF_MONTH)+"-"+current.get(current.MONTH)+"-"+current.get(current.YEAR);
		return cd;
	}
	
	
	public void updateVisuals()
	{

		Message message = messagesList.get(index);
		List<String> messages = message.getMessages();
		current = message.getCurrent();
		lblDate.setText(getCurrentDateString());
		
		
//		labelTitle.setText(getCurrentDateString());
		
		for(int i = 0; i<messages.size();i++)
		{
			JLabel lblNewLabel_1 = new JLabel(messages.get(i));
			lblNewLabel_1.setBounds(43, 80+i*30, 150, 20);
			this.add("l"+i, lblNewLabel_1);
			lblNewLabel_1.setVisible(true);
			JButton button = new JButton("Remove");
			button.setBounds(162, 76+i*30, 97, 25);
			this.add("b"+i, button);
			button.setVisible(true);
			
			buttonsList.add(button);
			labelsList.add(lblNewLabel_1);
			

			
			
			button.addActionListener((e) ->{
				JButton b = (JButton)e.getSource();
				String text = lblNewLabel_1.getText();
				Message temp = messagesList.get(index);
				temp.removeMessage(text);
				buttonsList.remove(button);
				b.setVisible(false);
				labelsList.remove(lblNewLabel_1);
				this.remove(lblNewLabel_1);
				updateVisuals();
			});
			
			this.repaint();
		}
	}

	public void setTitle(String title)
	{
		this.title = title;
		labelTitle.setText(title);
	}

	public String getTitle()
	{
		return title;
	}
	
	public void setTextFieldSize(Dimension dim)
	{
		changeSupport.firePropertyChange("textFieldSize", textFieldSize, dim);
		textFieldSize = dim;
		textArea.setBounds(35, 124, dim.width, dim.height);
		
	}

	public Dimension getTextFieldSize()
	{
		return textFieldSize;
	}
	
	public void setStarting(GregorianCalendar date)
	{
		this.starting = date;
		updateVisuals();
	}

	public GregorianCalendar getStarting()
	{
		return starting;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener)
	{
		changeSupport.removePropertyChangeListener(listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners()
	{
		return changeSupport.getPropertyChangeListeners();
	}



	public void addVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoSupport.addVetoableChangeListener(listener);
	}

	public void removeVetoableChangeListener(VetoableChangeListener listener)
	{
		vetoSupport.removeVetoableChangeListener(listener);
	}
}
