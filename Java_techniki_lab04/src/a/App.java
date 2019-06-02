package a;

import java.awt.EventQueue;

import javax.swing.JFrame;
import ziarenka.ChartBean;
import java.awt.BorderLayout;
import note.NoteBean;
import java.awt.Dimension;


public class App
{

	private JFrame frame;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 560);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		NoteBean noteBean = new NoteBean();
		noteBean.setTitle("wer");
		noteBean.setBounds(46, 28, 300, 400);
		frame.getContentPane().add(noteBean);
	}
}
