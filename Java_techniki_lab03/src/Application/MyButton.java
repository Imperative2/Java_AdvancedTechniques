package Application;

import javax.swing.JButton;

public class MyButton extends JButton
{
	int x;
	int y;

	public MyButton(int x, int y)
	{
		super();
		
		
		this.x = x;
		this.y = y;
		
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
