package lab01;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

public interface IBramka extends Remote
{
	// do pobierania informacji o statystyce – do wykorzystania przez Monitor
	// statystyka to tablica z dwiema wartoœciami: liczba wejœæ, liczba wyjœæ
	public int[] getStatystyka(Date pocz, Date kon) throws RemoteException;

	// do zdalnego zatrzymywania bramek
	public boolean zamknijBramke() throws RemoteException;

	// do pobrania numeru bramki
	public int getNumer() throws RemoteException;
}
