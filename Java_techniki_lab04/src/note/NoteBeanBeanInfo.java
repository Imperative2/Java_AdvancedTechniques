package note;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class NoteBeanBeanInfo extends SimpleBeanInfo
{
//	private PropertyDescriptor[] propertyDescriptors;
	
	public BeanDescriptor getBeanDescriptor() {
		return new BeanDescriptor(NoteBean.class, NoteBeanBeanCustomizer.class);
	}


	public PropertyDescriptor[] getPropertyDescriptors()
	{
		 try
		 {
			PropertyDescriptor textFieldSizeDescriptor = new PropertyDescriptor("textFieldSize", NoteBean.class);
			textFieldSizeDescriptor.setPropertyEditorClass(TextFieldSizeEditor.class);
			 
			PropertyDescriptor startDateDescriptor = new PropertyDescriptor("starting", NoteBean.class);
			startDateDescriptor.setPropertyEditorClass(StartDateEditor.class);
			 
			return  new PropertyDescriptor[]
			 {
				 textFieldSizeDescriptor,
				 startDateDescriptor,
				 new PropertyDescriptor("title", NoteBean.class)

			 };
		 }
		 catch (IntrospectionException e)
		 {
			 e.printStackTrace();
			 return null;
		 }
	}
	

}
