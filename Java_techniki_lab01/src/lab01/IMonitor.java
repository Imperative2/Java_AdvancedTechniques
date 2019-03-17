package lab01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitor extends Remote
{
	public void koniecznaAktualizacja() throws RemoteException;
}