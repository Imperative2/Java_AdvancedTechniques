package note;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

public class StartDateEditor extends PropertyEditorSupport
{
	@Override
	public Component getCustomEditor()
	{
		return new StartDatePanel(this);
	}
	
	@Override
	public boolean supportsCustomEditor()
	{
		return true;
	}
	
	@Override
	public boolean isPaintable()
	{
		return false;
	}
	
	public String getAsText()
	{
		return new String("A");
	}
	
	public void setAsText(String s)
	{
		setValue(new String("B"));
	}
	
	public String getJavaInitializationString()
	{
		return "date:";
	}
}
