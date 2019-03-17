package lab01;

import java.awt.EventQueue;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Monitor implements IMonitor
{
	private int registryPort = 5053;

	private ICentrala centrala;

	private Registry remoteRegistry;

	private IMonitor monitorStub;

	private boolean registered = false;

	private MyThread timer;

	private JFrame frmMonitor;

	private DefaultListModel<GateModel> gatesModel = new DefaultListModel<>();
	private JList<GateModel> gatesList;

	/**
	 * Create the application.
	 */
	public Monitor()
	{

		connectToRegistry();

		getServerStub();

		initialize();
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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmMonitor = new JFrame();
		frmMonitor.setTitle("Monitor");
		frmMonitor.setBounds(100, 100, 592, 431);
		frmMonitor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMonitor.getContentPane().setLayout(null);

		gatesList = new JList<>(gatesModel);
		gatesList.setBounds(39, 65, 263, 271);
		frmMonitor.getContentPane().add(gatesList);

		JButton btnRegisterMonitor = new JButton("Zarejestruj monitor");
		btnRegisterMonitor.setBounds(49, 349, 172, 25);
		frmMonitor.getContentPane().add(btnRegisterMonitor);

		JButton btnSignOffMonitor = new JButton("Wyrejestruj monitor");
		btnSignOffMonitor.setBounds(306, 349, 172, 25);
		frmMonitor.getContentPane().add(btnSignOffMonitor);

		JButton btnStartGate = new JButton("Uruchom bramk\u0119");
		btnStartGate.addActionListener((t) -> {

		});
		btnStartGate.setBounds(314, 130, 164, 25);
		frmMonitor.getContentPane().add(btnStartGate);

		JButton btnStopGate = new JButton("Zatrzymaj bramk\u0119");
		btnStopGate.setBounds(314, 186, 164, 25);
		frmMonitor.getContentPane().add(btnStopGate);
		btnStopGate.addActionListener((t) -> {
			closeGate();
		});

		JLabel fixLabelRegisteredGates = new JLabel("Zarejestrowane bramki:");
		fixLabelRegisteredGates.setBounds(39, 40, 144, 16);
		frmMonitor.getContentPane().add(fixLabelRegisteredGates);

		btnRegisterMonitor.addActionListener((e) -> {
			sendMonitorStubToServer();
			btnRegisterMonitor.setEnabled(false);
			btnSignOffMonitor.setEnabled(true);
			timer = new MyThread(monitorStub);
			timer.start();
		});

		btnSignOffMonitor.addActionListener((e) -> {
			signOffMonitor();
			btnSignOffMonitor.setEnabled(false);
			btnRegisterMonitor.setEnabled(true);
			timer.changeExitFlag();
		});

	}

	private void closeGate()
	{
		try
		{
			int selectedIndex = gatesList.getSelectedIndex();
			if (selectedIndex < 0)
				return;
			GateModel gateModel = gatesModel.getElementAt(selectedIndex);
			gateModel.getGate().zamknijBramke();
		}
		catch (RemoteException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void sendMonitorStubToServer()
	{
		try
		{
			if (monitorStub == null)
				monitorStub = (IMonitor) UnicastRemoteObject.exportObject(this, 0);
			centrala.zarejestrujMonitor(monitorStub);
			registered = true;
		}
		catch (RemoteException e)
		{
			System.err.println("Error while registering monitor to the server");
			e.printStackTrace();
		}
	}

	private void signOffMonitor()
	{
		try
		{
			centrala.wyrejestrujMonitor();
			registered = false;
		}
		catch (RemoteException e)
		{
			System.err.println("Error while signing off  monitor from the server");
			e.printStackTrace();
		}
	}

	@Override
	public void koniecznaAktualizacja() throws RemoteException
	{
		List<IBramka> gates = centrala.getZarejestrowaneBramki();

		gatesModel.removeAllElements();

		for (IBramka gate : gates)
		{
			GateModel gateModel = new GateModel(gate);
			gatesModel.addElement(gateModel);
			// System.out.println(gateModel);
		}

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
					Monitor window = new Monitor();
					window.frmMonitor.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	static class MyThread extends Thread
	{
		private IMonitor monitorStub;

		private boolean exitFlag = false;

		public MyThread(IMonitor monitorStub)
		{
			this.monitorStub = monitorStub;
		}

		public void changeExitFlag()
		{
			exitFlag = true;
		}

		@Override
		public void run()
		{
			while (exitFlag == false)
			{
				try
				{
					monitorStub.koniecznaAktualizacja();
					// System.out.println(" updating monitor view ");
				}
				catch (RemoteException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try
				{
					Thread.sleep(5000);
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}
}
