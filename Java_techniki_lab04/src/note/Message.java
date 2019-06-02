package note;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.*;

public class Message
{
	
	private List<String> messagesList = new ArrayList<>();
	private GregorianCalendar current;
	
	public Message()
	{
		
	}
	
	public void addMessage(String text)
	{
		messagesList.add(text);
	}
	
	public List<String> getMessages()
	{
		return messagesList;
	}
	
	public void setCurrent(GregorianCalendar current)
	{
		this.current = current;
	}
	
	public GregorianCalendar getCurrent()
	{
		return current;
	}
	
	public void removeMessage(String message)
	{
		int i;
		for(i=0; i<messagesList.size(); i++)
		{
			String temp = messagesList.get(i);
			if(temp.equals(message) == true) break;
		}
		
		messagesList.remove(messagesList.get(i));
	}

}
