package note;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

public class TextFieldSizeEditor extends PropertyEditorSupport
{
	@Override
	public Component getCustomEditor()
	{
		return new TextFieldSizePanel(this);
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
		return null;
	}
	
	public String getJavaInitializationString()
	{
		return "" + getValue();
	}
}
