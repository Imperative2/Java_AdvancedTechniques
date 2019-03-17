package lab01;

import java.rmi.RemoteException;

public class GateModel
{
	private IBramka gate;
	private int entryCount = 0;
	private int exitCount = 0;
	private String status = "";
	private int gateNumber = 0;
	
	
	public GateModel(IBramka gate)
	{
		this.gate = gate;
		try
		{
			int[] data =gate.getStatystyka(null, null);
			entryCount = data[0];
			exitCount = data[1];
			status = data[2] == 1? " working ":" not working";
			gateNumber = data[3];

		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String toString()
	{
		return gateNumber+"   "+entryCount+"   "+exitCount+" "+status;
	}


	public IBramka getGate()
	{
		return gate;
	}


	public void setGate(IBramka gate)
	{
		this.gate = gate;
	}


	public int getEntryCount()
	{
		return entryCount;
	}


	public void setEntryCount(int entryCount)
	{
		this.entryCount = entryCount;
	}


	public int getExitCount()
	{
		return exitCount;
	}


	public void setExitCount(int exitCount)
	{
		this.exitCount = exitCount;
	}


	public String getStatus()
	{
		return status;
	}


	public void setStatus(String status)
	{
		this.status = status;
	}


	public int getGateNumber()
	{
		return gateNumber;
	}


	public void setGateNumber(int gateNumber)
	{
		this.gateNumber = gateNumber;
	}
	
	
}
