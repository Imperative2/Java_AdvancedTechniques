package Application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class App
{

	private JFrame frmTicTacToe;
	private JTextField textFieldWidth;
	
	private ButtonGroup buttonGroup = new ButtonGroup();

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
					window.frmTicTacToe.setVisible(true);
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
		frmTicTacToe = new JFrame();
		frmTicTacToe.setTitle("Tic Tac Toe");
		frmTicTacToe.setBounds(100, 100, 450, 300);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTicTacToe.getContentPane().setLayout(null);
		
		JLabel fixLabelBoardWidth = new JLabel("Give board Width:");
		fixLabelBoardWidth.setBounds(90, 59, 122, 16);
		frmTicTacToe.getContentPane().add(fixLabelBoardWidth);
		
		textFieldWidth = new JTextField();
		textFieldWidth.setText("5");
		textFieldWidth.setBounds(202, 56, 116, 22);
		frmTicTacToe.getContentPane().add(textFieldWidth);
		textFieldWidth.setColumns(10);
		
		JLabel fixLabelBoardHeight = new JLabel("Give board Height:");
		fixLabelBoardHeight.setBounds(90, 88, 106, 16);
		frmTicTacToe.getContentPane().add(fixLabelBoardHeight);
		
		JTextField textFieldHeight = new JTextField();
		textFieldHeight.setText("5");
		textFieldHeight.setBounds(202, 88, 116, 22);
		frmTicTacToe.getContentPane().add(textFieldHeight);
		textFieldHeight.setColumns(10);
		
		JRadioButton radioBtnEasy = new JRadioButton("Easy");
		radioBtnEasy.setSelected(true);
		radioBtnEasy.setBounds(80, 156, 56, 25);
		frmTicTacToe.getContentPane().add(radioBtnEasy);
		buttonGroup.add(radioBtnEasy);
		
		JRadioButton radioBtnMedium = new JRadioButton("Medium");
		radioBtnMedium.setBounds(161, 156, 77, 25);
		frmTicTacToe.getContentPane().add(radioBtnMedium);
		buttonGroup.add(radioBtnMedium);
		
		JRadioButton radioBtnHard = new JRadioButton("Hard");
		radioBtnHard.setBounds(253, 156, 65, 25);
		frmTicTacToe.getContentPane().add(radioBtnHard);
		buttonGroup.add(radioBtnHard);
		
		JLabel fixLabelDifficultyLevel = new JLabel("Select difficulty level:");
		fixLabelDifficultyLevel.setFont(new Font("Tahoma", Font.BOLD, 13));
		fixLabelDifficultyLevel.setBounds(126, 123, 188, 16);
		frmTicTacToe.getContentPane().add(fixLabelDifficultyLevel);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(168, 216, 97, 25);
		frmTicTacToe.getContentPane().add(btnStart);
		
		JLabel fixLabelBoardDimensions = new JLabel("Board Dimensions (>=5):");
		fixLabelBoardDimensions.setFont(new Font("Tahoma", Font.BOLD, 13));
		fixLabelBoardDimensions.setBounds(126, 30, 192, 16);
		frmTicTacToe.getContentPane().add(fixLabelBoardDimensions);
		
		
		btnStart.addActionListener((e) ->{
			int sizeX = Integer.parseInt(textFieldWidth.getText());
			int sizeY = Integer.parseInt(textFieldHeight.getText());
			int difficultyLevel;
			
			if(radioBtnEasy.isSelected() == true)
				difficultyLevel = 0;
			else if(radioBtnMedium.isSelected() == true)
				difficultyLevel = 1;
			else
				difficultyLevel = 2;
			
			MyGameWindow gameWindow = new MyGameWindow(sizeX, sizeY, difficultyLevel);
			
			gameWindow.setVisible(true);
			
			
			
		});
	}
}
