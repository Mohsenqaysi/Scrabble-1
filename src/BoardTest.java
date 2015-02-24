/**
 * @author Mohsen Qaysi
 * Updated by Yongzhen Ren.
 */

import java.util.Scanner;

public class BoardTest
{
	public static void main(String[] args)
	{
		String postion, direction, ans;
		Pool testPool = new Pool();
		Frame testFrame = new Frame();
		Board testBoard = new Board();
		Scanner testInput = new Scanner(System.in);

		System.out.println(testBoard);
		// Test toString() method. Expect a blank board.

		// Keep looping until the pool is empty or user inputs "Y" after one turn.
		while ( !testPool.isEmpty() )
		{
			System.out.println("The size of the pool: " + testPool.size());
			testFrame.refill(testPool);
			System.out.println("Frame: " + testFrame);
			// Input the position and direction from the keyboard.
			System.out.printf("Input the position in such format <Letter><Number>: ");
			// Since there is NO input checking, be careful with input.
			postion = testInput.next();
			System.out.printf("Give the direction\nA/a - horizontally\tD/d - vertically\n");
			direction = testInput.next();
			testBoard.putNewWord(postion, direction, testInput.next(), testFrame);
			System.out.println(testBoard);

			System.out.printf("Do you want to keep playing: [Y/N]: ");
			ans = testInput.next();
			if ( ans.equalsIgnoreCase("Y") )
			{
				continue;
			}
			else
			{
				if ( ans.equalsIgnoreCase("N") )
				{
					System.out.println("Game over.");
					break;
				}
				else
				{
					System.out.println("[ERROR] An unacceptable input. Terminated.");
					break;
				}
			}
		}
		System.out.println("The size of the pool: " + testPool.size());
		System.out.println("Letters left in the Frame: " + testFrame);
		testInput.close();
	}
}