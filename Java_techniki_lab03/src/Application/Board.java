package Application;

import java.awt.Point;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import annotations.Strategy;
import annotations.Difficulty;


public class Board
{
	private char[][] boardState;
	private int sizeX;
	private int sizeY;
	
	private URLClassLoader classLoader = null;
	
	private List<Class<?>> classesList = new ArrayList<>();
	private List<Method> methodsList = new ArrayList<>();
 	
	
	public Board(int sizeX, int sizeY)
	{
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		boardState = new char[sizeY][sizeX];
		
		for(int i=0; i<sizeX; i++)
			for(int j=0; j<sizeY; j++)
				boardState[j][i] = 'e';
	}
	
	public void makeMove(int x, int y, boolean opponent)
	{
		if(opponent == true)
			boardState[y][x] = 'O';
		else
			boardState[y][x] = 'X';
	}
	
	public Point opponentGetMove(int strategy)
	{
		Method method = methodsList.get(strategy);
		
		try
		{
			Point point = (Point)method.invoke(null, boardState);
			System.out.println("Point.x"+point.getX()+"   Point.y "+point.getY());
			return point;
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int checkIfWon()
	{
		//Vertical check
		for(int i=0; i<sizeX; i++)
		{
			for(int j=0; j<=sizeY-5; j++)
			{
				int playerSum =0;
				int opponentSum = 0;
				
				for(int k=0; k<5; k++)
				{
					if(boardState[j+k][i] == 'X')
						playerSum++;
					else if (boardState[j+k][i] == 'O')
						opponentSum++;
				}
				if(playerSum == 5)
					return 1;
				else if(opponentSum == 5)
					return -1;
			}
		}
		
		//Horizontal check
		for(int i=0; i<=sizeX-5; i++)
		{
			for(int j=0; j<sizeY; j++)
			{
				int playerSum =0;
				int opponentSum = 0;
				
				for(int k=0; k<5; k++)
				{
					if(boardState[j][i+k] == 'X')
						playerSum++;
					else if (boardState[j][i+k] == 'O')
						opponentSum++;
				}
				if(playerSum == 5)
					return 1;
				else if(opponentSum == 5)
					return -1;
			}
		}
		
		//Left Diagonal check (left up right down)
		for(int i=0; i<=sizeX-5; i++)
		{
			for(int j=0; j<=sizeY-5; j++)
			{
				int playerSum =0;
				int opponentSum = 0;
				
				for(int k=0; k<5; k++)
				{
					if(boardState[j+k][i+k] == 'X')
						playerSum++;
					else if (boardState[j+k][i+k] == 'O')
						opponentSum++;
				}
				if(playerSum == 5)
					return 1;
				else if(opponentSum == 5)
					return -1;
			}
		}
		
		//Right Diagonal check (right up left down)
		for(int i=0; i<=sizeX-5; i++)
		{
			for(int j=0; j<=sizeY-5; j++)
			{
				int playerSum =0;
				int opponentSum = 0;
				
				for(int k=0; k<5; k++)
				{
					if(boardState[j+4-k][i+k] == 'X')
						playerSum++;
					else if (boardState[j+4-k][i+k] == 'O')
						opponentSum++;
				}
				if(playerSum == 5)
					return 1;
				else if(opponentSum == 5)
					return -1;
			}
		}
		
		return 0;
	}
	
	public char[][] getBoardState()
	{
		return boardState;
	}
	
	public void loadClass()
	{
		
		File classesDir = new File("C:\\GIT\\Java_AdvancedTechniques\\Java_techniki_lab03\\bin\\classes");
		File[] classesFiles = classesDir.listFiles();
		
		try
		{

			classLoader = new URLClassLoader(new URL[] {
					new URL("file:/C:\\GIT\\Java_AdvancedTechniques\\Java_techniki_lab03\\bin/") });
		
			
			for(File file: classesFiles)	
			{
	
				String className = "classes."+file.getName().replaceAll(".class", "");
//				System.out.println("ssss  "+className);
					
				Class<?> clazz = classLoader.loadClass(className);
				classesList.add(clazz);
				
				System.out.println(clazz.getName());
				
				Annotation[] annotations = clazz.getAnnotations();
				for (Annotation annotation : annotations)
				{
					System.out.println(annotation.toString());
					if (annotation instanceof Strategy)
					{
						Strategy myAnnotation = (Strategy) annotation;
						System.out.println("name: " + myAnnotation.name());
						
						
						Method[] methods = clazz.getMethods();
						
						for(Method method : methods)
						{
							
							Annotation[] methodsAnnotations = method.getAnnotations();
							
							for(Annotation methodAnnotation : methodsAnnotations)
							{
								System.out.println(methodAnnotation);
								if(methodAnnotation instanceof Difficulty)
								{
									Difficulty myMethodAnnotation = (Difficulty) methodAnnotation;
									System.out.println("name: "+myMethodAnnotation.difficulty());
									
									methodsList.add(method);
								}
							}
						}
						
						
					}
				}
	
	
			}
		
		}
		catch (MalformedURLException | ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
	
	
}
