package main;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Main
{

	private JFrame frame;
	private JTextArea textArea;
	private JLabel label;
	
    private ScriptEngineManager engineManager = new ScriptEngineManager();
    private ScriptEngine engine = engineManager.getEngineByName("nashorn");
    
	private File file = null;


	/**
	 * Launch the application.
	 */
	public static void i()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{	
				try
				{
					Main window = new Main();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run()
			{
				
				
				try
				{
					Main window = new Main();
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
	public Main()
	{

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 594, 758);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 350, 576, 362);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setBounds(0, 0, 564, 349);
		panel.add(fileChooser);
		
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Javascript", "js");
		    fileChooser.setFileFilter(filter);
		
		fileChooser.addActionListener((e) -> {
			file = fileChooser.getSelectedFile();
			try
			{
				engine.eval(new FileReader(file.getAbsolutePath()));
				Invocable invocable = (Invocable) engine;
				System.out.println("Loaded: "+file.getAbsolutePath());
				Object result = invocable.invokeFunction("fun1",textArea.getText());
				System.out.println(result);
				System.out.println(result.getClass());
				label.setText((String) result);
			}
			catch (FileNotFoundException | ScriptException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (NoSuchMethodException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 576, 353);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label = new JLabel("*text will appear here*");
		label.setBounds(122, 13, 291, 174);
		panel_1.add(label);
		
		textArea = new JTextArea();
		textArea.setBounds(122, 200, 291, 125);
		panel_1.add(textArea);
	}
}
