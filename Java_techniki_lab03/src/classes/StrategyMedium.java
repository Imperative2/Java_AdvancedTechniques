package classes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import annotations.Difficulty;
import annotations.Strategy;
@Strategy(name="After Opponent")
public class StrategyMedium
{
	@Difficulty(difficulty="Medium")
	public static Point getOpponentMove(char[][] fields)
	{
		Point point = new Point();

		int i;
		int j;
		
		if(fields[0][0]=='e')
		{
			point.x=0;
			point.y=0;
			return point;
		}
		if(fields[1][1]=='e' || fields[0][0]=='O')
		{
			point.x=1;
			point.y=1;
			return point;
		}
		if(fields[2][2]=='e' || fields[1][1]=='O')
		{
			point.x=2;
			point.y=2;
			return point;
		}
		if(fields[3][3]=='e' || fields[2][2]=='O')
		{
			point.x=3;
			point.y=3;
			return point;
		}
		if(fields[4][4]=='e' || fields[3][3]=='O')
		{
			point.x=4;
			point.y=4;
			return point;
		}
		if(fields[1][0]=='e')
		{
			point.x=0;
			point.y=1;
			return point;
		}
		if(fields[2][0]=='e')
		{
			point.x=0;
			point.y=2;
			return point;
		}
		if(fields[3][0]=='e')
		{
			point.x=0;
			point.y=3;
			return point;
		}
		if(fields[4][0]=='e')
		{
			point.x=0;
			point.y=4;
			return point;
		}
		

		do
		{
			i = (int) (Math.random() * fields.length);
			j = (int) (Math.random() * fields[0].length);

		} while (fields[i][j] != 'e');

		point.x = j;
		point.y = i;

		return point;

	}
}
