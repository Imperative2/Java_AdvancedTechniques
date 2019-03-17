package loader;

public class ClassModel
{
	private Class<?> loadedClass;
	private String name;
	
	
	public  ClassModel(Class<?> loadedClass)
	{
		this.loadedClass = loadedClass;
		this.name = loadedClass.getName().replaceAll("^\\w+.", "");
	}
	
	public Class<?> getLoadedClass()
	{
		return loadedClass;
	}
	
	public String toString()
	{
		return name;
	}
	
	
}

