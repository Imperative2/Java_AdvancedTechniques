package loader;

public class PortPool
{
	private static int[] portPool =  {6666,5001,5002,5003,5004,5005,5006,5007,5008,5009,50010};
	private static int poolIndex = 0;
	
	public static int getPort()
	{
		System.out.println("Generated port: "+portPool[poolIndex]+"\t"+poolIndex);
		return portPool[poolIndex++];
	}
}
