import java.util.Scanner;

public class BoardTest
{
	public static void main(String[] args)
	{
		
		Board testBoard = new Board();
		//System.out.println(testBoard);
		// Test toString() method. Expect a blank board.

		Pool testPool = new Pool();
		Frame testFrame = new Frame();
		Scanner testInput = new Scanner(System.in);

		testFrame.refill(testPool);
		System.out.println(testFrame);
		
		testBoard.putNewWord("H8", "D", testInput.next(), testFrame);
		
		testFrame.refill(testPool);
		System.out.println(testBoard);
		System.out.println(testFrame);
		
		testBoard.putNewWord("H8", "D", testInput.next(), testFrame);

		System.out.println(testBoard);
		
		testInput.close();

	}
}