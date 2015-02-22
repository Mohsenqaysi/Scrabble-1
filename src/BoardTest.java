import java.util.Scanner;

public class BoardTest
{
	public static void main(String[] args)
	{
		
		Board testBoard = new Board();

		Pool testPool = new Pool();
		Frame testFrame = new Frame();
		Scanner testInput = new Scanner(System.in);

		System.out.println(testBoard);
		// Test toString() method. Expect a blank board.
		while ( !testPool.isEmpty() )
		{
			testFrame.refill(testPool);
			System.out.println("Frame: " + testFrame);
			System.out.println("<Starting Position> <Direction> <Word>");
			testBoard.putNewWord(testInput.next(), testInput.next(), testInput.next(), testFrame);
			// We do NOT do any input checking. Be careful with your input.
			System.out.println(testBoard);
		}
		
		testInput.close();
	}
}
