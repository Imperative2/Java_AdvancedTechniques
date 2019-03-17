package lab01;

import java.awt.EventQueue;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.rmi.server.RMISocketFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.JButton;

public class Bramka implements IBramka
{

	private ICentrala centrala;

	private Registry remoteRegistry;

	private int gateNumber = 11;

	private int registryPort = 5053;

	private int entryCounter = 0;

	private int exitCounter = 0;

	private boolean isWorking = false;

	private JFrame frame;
	private JLabel labelGateNumber;
	private JLabel labelExitCount;
	private JLabel labelEntryCount;

	private JButton btnStart;
	private JButton btnStop;

	/**
	 * Create the application.
	 */
	public Bramka()
	{

		connectToRegistry();

		getServerStub();

		sendGateStubToServer();

		initializeVisuals();

	}

	public void connectToRegistry()
	{
		try
		{
			remoteRegistry = LocateRegistry.getRegistry(registryPort);
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			System.err.println("Bramka couldnt get RMI registry");
			e.printStackTrace();
		}

	}

	private void getServerStub()
	{
		try
		{
			centrala = (ICentrala) remoteRegistry.lookup("Centrala");
		}
		catch (AccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void sendGateStubToServer()
	{
		try
		{

			IBramka gate = (IBramka) UnicastRemoteObject.exportObject(this, 0);
			gateNumber = centrala.zarejestrujBramke(gate);
		}
		catch (RemoteException e)
		{
			System.err.println("Error while registering gate to the server");
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeVisuals()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Bramka : " + gateNumber);
		frame.toFront();
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent t)
			{
				if (centrala != null)
				{
					try
					{
						centrala.wyrejestrujBramke(gateNumber);
						 System.out.println("gate "+gateNumber+" signing off");
						//frame.dispose();
						System.exit(0);
					}
					catch (RemoteException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();

					}

				}

			}
		});

		JLabel fixLabelGateNumber = new JLabel("Bramka nr: ");
		fixLabelGateNumber.setBounds(28, 25, 95, 16);
		frame.getContentPane().add(fixLabelGateNumber);

		labelGateNumber = new JLabel(gateNumber + "");
		labelGateNumber.setBounds(103, 25, 200, 16);
		frame.getContentPane().add(labelGateNumber);

		JLabel fixLabelEntryCount = new JLabel("Ilo\u015B\u0107 wej\u015B\u0107:");
		fixLabelEntryCount.setBounds(28, 54, 73, 16);
		frame.getContentPane().add(fixLabelEntryCount);

		labelEntryCount = new JLabel("0");
		labelEntryCount.setBounds(113, 54, 56, 16);
		frame.getContentPane().add(labelEntryCount);

		JLabel fixLabelExitCount = new JLabel("Ilo\u015B\u0107 wyj\u015B\u0107:");
		fixLabelExitCount.setBounds(28, 83, 73, 16);
		frame.getContentPane().add(fixLabelExitCount);

		labelExitCount = new JLabel("0");
		labelExitCount.setBounds(113, 83, 56, 16);
		frame.getContentPane().add(labelExitCount);

		btnStart = new JButton("Start");
		btnStart.setBounds(47, 133, 97, 25);
		frame.getContentPane().add(btnStart);

		btnStop = new JButton("Stop");
		btnStop.setBounds(206, 133, 97, 25);
		frame.getContentPane().add(btnStop);
		btnStop.setEnabled(false);

		JButton btnEnter = new JButton("We");
		btnEnter.setBounds(47, 193, 97, 25);
		frame.getContentPane().add(btnEnter);

		JButton btnExit = new JButton("Wy");
		btnExit.setBounds(206, 193, 97, 25);
		frame.getContentPane().add(btnExit);

		btnStart.addActionListener((e) -> {
			btnStop.setEnabled(true);
			btnStart.setEnabled(false);
			isWorking = true;
		});

		btnStop.addActionListener((e) -> {
			btnStart.setEnabled(true);
			btnStop.setEnabled(false);
			isWorking = false;
		});

		btnEnter.addActionListener((e) -> {
			if (isWorking == true)
			{
				entryCounter++;
				labelEntryCount.setText(entryCounter + "");
			}
		});

		btnExit.addActionListener((e) -> {
			if (isWorking == true)
			{
				exitCounter++;
				labelExitCount.setText(exitCounter + "");
			}
		});

	}


	@Override
	/**
	 * @return int[4]  [0] - entryCounter, [1] - exitCounter, [2] - status, [3] - gateNumber
	 */
	public int[] getStatystyka(Date pocz, Date kon) throws RemoteException
	{
		int status = isWorking == true ? 1 : 0;
		int[] gateStatistics = { entryCounter, exitCounter, status, gateNumber };
		return gateStatistics;
	}

	@Override
	public boolean zamknijBramke() throws RemoteException
	{
		isWorking = false;
		btnStart.setEnabled(true);
		btnStop.setEnabled(false);
		return true;
	}

	@Override
	public int getNumer() throws RemoteException
	{
		return gateNumber;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{

		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				try
				{
					Bramka window = new Bramka();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
