package classes;

import java.awt.Point;
import annotations.Strategy;
import annotations.Difficulty;


@Strategy(name="Random")
public class StrategyEast
{
	@Difficulty(difficulty = "Easy")
	public static Point getOpponentMove(char[][] fields)
	{
		Point point = new Point();

		int i;
		int j;

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
