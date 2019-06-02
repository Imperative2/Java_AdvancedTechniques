package Application;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyGameWindow extends JFrame
{

	private Board gameBoard;
	private int sizeX;
	private int sizeY;

	private int difficultyLevel;

	private JButton[][] buttonsArray;

	private JPanel panel;

	public MyGameWindow(int sizeX, int sizeY, int difficultyLevel)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.difficultyLevel = difficultyLevel;

		gameBoard = new Board(sizeX, sizeY);
		gameBoard.loadClass();

		buttonsArray = new JButton[sizeY][sizeX];

		this.setBounds(600, 200, 420, 478);
		this.setTitle("Game " + sizeX + " x " + sizeY);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setLayout(null);

		panel.setBounds(0, 0, 420, 448);

		this.getContentPane().add(panel);

		JButton btnNextStrategy = new JButton("Next Strategy");
		btnNextStrategy.setBounds(130, 410, 120, 20);
		panel.add(btnNextStrategy);

		btnNextStrategy.addActionListener((e) -> {
			this.difficultyLevel = (this.difficultyLevel + 1) % 3;
			System.out.println("Changed difficulty level");
		});

		for (int i = 0; i < sizeY; i++)
		{
			for (int j = 0; j < sizeX; j++)
			{
				MyButton button = new MyButton(j, i);

				// JButton bbb = new JButton();

				button.setBounds(0 + j * (400 / sizeX), 0 + i * (400 / sizeY), 400 / sizeX, 400 / sizeY);
				// bbb.setBounds(0+j*(400/sizeX),0+i*(400/sizeY),400/sizeX,400/sizeY);
				panel.add(button);
				// panel.add(bbb);
				button.setSelected(true);

				panel.repaint();

				buttonsArray[i][j] = button;

				button.addActionListener((e) -> {
					MyButton btn = (MyButton) e.getSource();
					int x = btn.getX();
					int y = btn.getY();

					System.out.println("button at: " + x + "  " + y + " clicked");

					gameBoard.makeMove(x, y, false);
					btn.setText("X");
					System.out.println(gameBoard.checkIfWon());

					btn.setEnabled(false);

					if (gameBoard.checkIfWon() == 1)
					{
						btn.setEnabled(false);

						int clickedOK = JOptionPane.showOptionDialog(null, "You WON!!!", "CONGRATULATIONS!!!",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
						if (clickedOK == 0)
						{
							this.dispose();
						}

					}

					// opponent move

					Point point = gameBoard.opponentGetMove(difficultyLevel);

					buttonsArray[point.y][point.x].setEnabled(false);
					buttonsArray[point.y][point.x].setText("O");

					gameBoard.makeMove(point.x, point.y, true);

					if (gameBoard.checkIfWon() == -1)
					{
						btn.setEnabled(false);

						int clickedOK = JOptionPane.showOptionDialog(null, "You LOST!!!", "YOU SUCK!!!",
								JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
						if (clickedOK == 0)
						{
							this.dispose();
						}

					}
				});

			}
		}

		this.repaint();
		this.setVisible(true);
	}
}
