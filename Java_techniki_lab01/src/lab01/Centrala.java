package lab01;

import java.awt.EventQueue;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.rmi.server.RMIClassLoader;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Centrala implements ICentrala
{

	private Registry remoteRegistry;

	private ICentrala centralaStub;

	private IMonitor monitorStub;

	private List<IBramka> bramkaStubsList = new ArrayList<>();

	private final int registryPort = 5053;

	private JFrame frame;

	JComboBox<Integer> cbGates;
	Map<Integer, IBramka> gatesMap = new HashMap<>();
	private IBramka selectedGate;

	JButton btnSignOffGate;
	JButton btnStopGate;

	JLabel labelGateNumber;
	JLabel labelStatus;
	JLabel labelEntryCount;
	JLabel labelExitCount;

	/**
	 * Create the application.
	 */
	public Centrala()
	{
		initRmiRegistry();

		initServerStub();

		initializeVisuals();
	}

	private void initRmiRegistry()
	{
		try
		{
			remoteRegistry = LocateRegistry.createRegistry(registryPort);
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initServerStub()
	{
		try
		{
			centralaStub = (ICentrala) UnicastRemoteObject.exportObject(this, registryPort);

			remoteRegistry.rebind("Centrala", centralaStub);
		}
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeVisuals()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 520, 377);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("Centrala");
		frame.getContentPane().setLayout(null);

		cbGates = new JComboBox<>();
		cbGates.setBounds(28, 60, 201, 22);
		frame.getContentPane().add(cbGates);
		// cbGates.addItem(0000);

		btnStopGate = new JButton("Zatrzymaj bramk\u0119");
		btnStopGate.setBounds(269, 59, 161, 25);
		frame.getContentPane().add(btnStopGate);
		// btnStopGate.setEnabled(false);

		btnSignOffGate = new JButton("Wyrejestruj bramk\u0119");
		btnSignOffGate.setBounds(269, 97, 161, 25);
		frame.getContentPane().add(btnSignOffGate);
		// btnSignOffGate.setEnabled(false);

		JLabel fixLabelRegisteredGates = new JLabel("Zarejestrowane bramki:");
		fixLabelRegisteredGates.setBounds(28, 36, 166, 16);
		frame.getContentPane().add(fixLabelRegisteredGates);

		JLabel fixLabelStatus = new JLabel("Status bramki:");
		fixLabelStatus.setBounds(28, 189, 84, 16);
		frame.getContentPane().add(fixLabelStatus);

		JLabel fixLabelEntryCount = new JLabel("Ilo\u015B\u0107 wej\u015B\u0107:");
		fixLabelEntryCount.setBounds(28, 218, 90, 16);
		frame.getContentPane().add(fixLabelEntryCount);

		JLabel fixLabelExitCount = new JLabel("Ilo\u015B\u0107 wyj\u015B\u0107:");
		fixLabelExitCount.setBounds(28, 247, 84, 16);
		frame.getContentPane().add(fixLabelExitCount);

		JLabel fixLabelGateNumber = new JLabel("Numer bramki: ");
		fixLabelGateNumber.setBounds(28, 140, 90, 16);
		frame.getContentPane().add(fixLabelGateNumber);

		labelGateNumber = new JLabel("*****");
		labelGateNumber.setBounds(120, 140, 122, 16);
		frame.getContentPane().add(labelGateNumber);

		labelStatus = new JLabel("*****");
		labelStatus.setBounds(120, 189, 90, 16);
		frame.getContentPane().add(labelStatus);

		labelEntryCount = new JLabel("*****");
		labelEntryCount.setBounds(120, 218, 56, 16);
		frame.getContentPane().add(labelEntryCount);

		labelExitCount = new JLabel("*****");
		labelExitCount.setBounds(120, 247, 56, 16);
		frame.getContentPane().add(labelExitCount);

		cbGates.addActionListener((e) -> {
			if (cbGates.getSelectedItem() != null)
			{
				selectedGate = gatesMap.get((Integer) cbGates.getSelectedItem());
			}
			else
				selectedGate = null;

			updateView();
		});

		btnStopGate.addActionListener((e) -> {
			IBramka gate = gatesMap.get(cbGates.getSelectedItem());
			try
			{
				gate.zamknijBramke();
				btnStopGate.setEnabled(false);
				updateView();
				if (monitorStub != null)
					monitorStub.koniecznaAktualizacja();
			}
			catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		btnSignOffGate.addActionListener((e) -> {
			IBramka gate = gatesMap.get(cbGates.getSelectedItem());
			try
			{
				gate.zamknijBramke();
				wyrejestrujBramke(gate.getNumer());
			}
			catch (RemoteException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

	}

	private void updateView()
	{

		if (selectedGate != null)
		{
			int entryCount;
			int exitCount;
			String status;

			try
			{
				int[] data = selectedGate.getStatystyka(null, null);
				entryCount = data[0];
				exitCount = data[1];
				status = data[2] == 1 ? "working" : "not working";
				if (data[2] == 1)
					btnStopGate.setEnabled(true);
				labelGateNumber.setText(selectedGate.getNumer() + "");
				labelStatus.setText(status);
				labelEntryCount.setText(entryCount + "");
				labelExitCount.setText(exitCount + "");

				// btnSignOffGate.setEnabled(true);
				// btnStopGate.setEnabled(true);

			}
			catch (RemoteException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			labelGateNumber.setText("****");
			labelStatus.setText("****");
			labelEntryCount.setText("****");
			labelExitCount.setText("****");

			// btnSignOffGate.setEnabled(false);
			// btnStopGate.setEnabled(false);
		}
	}

	@Override
	public int zarejestrujBramke(Object bramka) throws RemoteException
	{

		IBramka gate = (IBramka) bramka;

		int gateNumber = (int) (Math.random() * Integer.MAX_VALUE);

		bramkaStubsList.add(gate);

		gatesMap.put(gateNumber, gate);
		cbGates.addItem(gateNumber);
		cbGates.repaint();

		System.out.println("Succesfully registered gate nr: " + gateNumber);

		updateView();

		if (monitorStub != null)
			monitorStub.koniecznaAktualizacja();

		return gateNumber;

	}

	@Override
	public boolean wyrejestrujBramke(int nrBramki) throws RemoteException
	{
		try
		{
			remoteRegistry.unbind(nrBramki + "");
			System.out.println("Gate nr: " + nrBramki + " signing off");
			IBramka gate = gatesMap.remove(nrBramki);
			bramkaStubsList.remove(gate);
			cbGates.removeItem(nrBramki);
			cbGates.repaint();
			selectedGate = null;
			updateView();
			if (monitorStub != null)
				monitorStub.koniecznaAktualizacja();
			return true;
		}
		catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<IBramka> getZarejestrowaneBramki() throws RemoteException
	{
		return bramkaStubsList;
	}

	@Override
	public void zarejestrujMonitor(Object o) throws RemoteException
	{
		monitorStub = (IMonitor) o;
		remoteRegistry.rebind("Monitor", monitorStub);

		System.out.println("Succesfully registered monitor");

	}

	@Override
	public void wyrejestrujMonitor() throws RemoteException
	{
		monitorStub = null;
		try
		{
			remoteRegistry.unbind("Monitor");
			System.out.println("Succesfully signed off monitor");
		}
		catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Object getMonitor() throws RemoteException
	{
		return monitorStub;
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
					Centrala window = new Centrala();
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
