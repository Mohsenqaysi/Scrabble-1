import java.util.Scanner;

public class BoardTest
{
	public static void main(String[] args)
	{
		Board testBoard = new Board();
		// System.out.println(testBoard);
		// Test toString() method. Expect a blank board.
		
		Pool testPool = new Pool();
		Frame testFrame = new Frame();

		testFrame.refill(testPool);
		System.out.println(testFrame);
		Scanner input = new Scanner(System.in);
		
		testBoard.putNewWord("C7", "D", input.next(), testFrame);
		testFrame.refill(testPool);
		System.out.println(testBoard);
		
		input.close();

	}
}