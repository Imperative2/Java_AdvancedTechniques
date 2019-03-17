package classes_to_Load;

public interface ClassStructure
{
	void setInput(int port);
	
	void setOutput(String host, int port);
	
	void start(boolean b);
	
	void stop(boolean b);
}
