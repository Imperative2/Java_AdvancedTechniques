package loader;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class App
{
	private URLClassLoader classLoader1 = null;
	private URLClassLoader classLoader2 = null;
	private URLClassLoader classLoader3 = null;

	private String classesURL = "file:/C:\\Users\\Student235044\\eclipse-workspace\\Java_techniki_lab02_Classes\\bin/";

	private List<Class<?>> classesList = new ArrayList<>();

	private JFrame frame;
	private JTextField textFieldInput;
	private JTextField textFieldOutput;

	private DefaultListModel<ClassModel> classModelLoaded = new DefaultListModel<>();
	private DefaultListModel<ClassModel> classModelUsed = new DefaultListModel<>();
	private JList<ClassModel> listLoadedClasses;
	private JList<ClassModel> listUsedClasses;


	private Socket firstSocket;

	private Socket socketIn;

	private ServerSocket serverSocket;

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
					App window = new App();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App()
	{
		try
		{
			loadClasses();
		}
		catch (ClassNotFoundException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initializeVisuals();
	}

	public void loadClasses() throws ClassNotFoundException, MalformedURLException
	{
		classLoader1 = new URLClassLoader(new URL[] { new URL(classesURL) });
		Class<?> invert = classLoader1.loadClass("classes_to_Load.Invert");
		classesList.add(invert);
		classLoader1 = null;

		classLoader2 = new URLClassLoader(new URL[] {
				new URL("file:/C:\\Users\\Student235044\\eclipse-workspace\\Java_techniki_lab02_ClassesB\\bin/") });
		Class<?> lowerCase = classLoader2.loadClass("classes_to_Load.LowerCase");
		classesList.add(lowerCase);

		classLoader3 = new URLClassLoader(new URL[] { new URL(classesURL) });
		Class<?> upperCase = classLoader3.loadClass("classes_to_Load.UpperCase");
		classesList.add(upperCase);

		for (Class<?> loadedClass : classesList)
		{
			System.out.println(loadedClass.getName() + " loaded by " + loadedClass.getClassLoader());
		}
		System.gc();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeVisuals()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		listLoadedClasses = new JList<>(classModelLoaded);
		listLoadedClasses.setBounds(12, 26, 105, 215);
		frame.getContentPane().add(listLoadedClasses);
		for (Class<?> clazz : classesList)
		{
			classModelLoaded.addElement(new ClassModel(clazz));
			classModelUsed.addElement(new ClassModel(clazz));
		}

		listUsedClasses = new JList<>(classModelUsed);
		listUsedClasses.setBounds(305, 26, 115, 215);
		frame.getContentPane().add(listUsedClasses);

		textFieldInput = new JTextField();
		textFieldInput.setBounds(154, 41, 116, 22);
		frame.getContentPane().add(textFieldInput);
		textFieldInput.setColumns(10);

		textFieldOutput = new JTextField();
		textFieldOutput.setBounds(154, 87, 116, 22);
		frame.getContentPane().add(textFieldOutput);
		textFieldOutput.setColumns(10);
		textFieldOutput.setEditable(false);

		JButton btnProcess = new JButton("Process");
		btnProcess.setBounds(154, 170, 116, 25);
		frame.getContentPane().add(btnProcess);

		JLabel fixLabelInput = new JLabel("Input:");
		fixLabelInput.setBounds(154, 24, 56, 16);
		frame.getContentPane().add(fixLabelInput);

		JLabel fixLabelOutput = new JLabel("Output:");
		fixLabelOutput.setBounds(154, 69, 56, 16);
		frame.getContentPane().add(fixLabelOutput);

		JLabel fixLabelLoadedClasses = new JLabel("Loaded classes:");
		fixLabelLoadedClasses.setBounds(12, 8, 105, 16);
		frame.getContentPane().add(fixLabelLoadedClasses);

		JLabel fixLabedUsedClasses = new JLabel("Using classes:");
		fixLabedUsedClasses.setBounds(305, 8, 115, 16);
		frame.getContentPane().add(fixLabedUsedClasses);

		listUsedClasses.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int selectedIndex = listUsedClasses.getSelectedIndex();
				System.out.println(selectedIndex);
				if (selectedIndex < 0)
					return;
				classModelUsed.removeElementAt(selectedIndex);

			}

		});

		listLoadedClasses.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				int selectedIndex = listLoadedClasses.getSelectedIndex();
				System.out.println(selectedIndex);
				if (selectedIndex < 0)
					return;
				classModelUsed.addElement(listLoadedClasses.getSelectedValue());

			}
		});

		btnProcess.addActionListener((e) -> {

			int port = PortPool.getPort();
			int port1 = PortPool.getPort();
			int port2 = PortPool.getPort();
			int port3 = PortPool.getPort();

			Object[] objects = new Object[classModelUsed.getSize()];

			int startPort = 7999;
			int endPort = startPort + objects.length;

			PrintWriter out = null;

			try
			{
				serverSocket = new ServerSocket(endPort);
				System.out.println("App socket running on port : " + endPort);
				MyThread myThread = new MyThread();
				myThread.start();

			}
			catch (IOException er)
			{
				// TODO Auto-generated catch block
				er.printStackTrace();
			}

			for (int i = 0; i < classModelUsed.getSize(); i++)
			{
				objects[i] = makeObject(classModelUsed.getElementAt(i).getLoadedClass());
			}

			for (int i = 0; i < objects.length; i++)
			{
				invokeMethodSetInput(objects[i], startPort + i);
			}

			if (firstSocket == null)
			{
				try
				{
					firstSocket = new Socket("127.0.0.1", startPort);
					firstSocket.getPort();
					System.out.println("is connected " + firstSocket.isConnected() + " " + firstSocket.getPort());
					out = new PrintWriter(firstSocket.getOutputStream(), true);
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			for (int i = 0; i < objects.length; i++)
			{
				invokeMethodSetOutput(objects[i], startPort + i + 1);
			}

			for (int i = 0; i < objects.length; i++)
			{
				invokeMethodStart(objects[i]);
			}

			out.println(textFieldInput.getText());
			out.flush();

			classLoader1 = null;
			classLoader2 = null;
			classLoader3 = null;

			for (Object obj : objects)
			{
				obj = null;
			}

			classesList.clear();

			classModelLoaded.removeAllElements();
			classModelUsed.removeAllElements();

			System.gc();

		});

	}

	public Object makeObject(Class<?> clazz)
	{
		try
		{
			Constructor<?> constructor = clazz.getConstructor();
			Object obj = constructor.newInstance();
			return obj;
		}
		catch (NoSuchMethodException | SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void invokeMethodSetInput(Object obj, int port)
	{

		try
		{
			Method method = obj.getClass().getMethod("setInput", int.class);
			method.invoke(obj, port);
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void invokeMethodSetOutput(Object obj, int port)
	{
		Method method;
		try
		{
			method = obj.getClass().getMethod("setOutput", String.class, int.class);
			method.invoke(obj, "127.0.0.1", port);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void invokeMethodStart(Object obj)
	{
		Method method;
		try
		{
			method = obj.getClass().getMethod("start", boolean.class);
			method.invoke(obj, false);
		}
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class MyThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{

				System.out.println("We are in sockt Thread");
				// System.out.println(serverSocket.isBound()+"sdfsdffd");
				// socketIn = serverSocket.accept();
				socketIn = serverSocket.accept();
				System.out.println("waiting");
				// BufferedReader sc = new BufferedReader(new
				// InputStreamReader(socketIn.getInputStream()));
				BufferedReader sc = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
				// if (sc == null)
				// System.out.println("bad scaner");
				System.out.println(sc);
				textFieldOutput.setText(sc.readLine());

			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
