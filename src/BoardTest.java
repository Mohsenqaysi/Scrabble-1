import java.util.Scanner;

public class BoardTest {
	public static void main(String[] args) {
		String postion = "", direction = "";
		boolean empty;
		Frame frame;
		Pool testPool = new Pool();
		Frame testFrame = new Frame();
		
		System.out.println("Pool of size: "+testPool.size());
		empty = testFrame.isEmpty();
		System.out.printf("Is frame empty? %b \n",empty);  // expect:true
		Board testBoard = new Board();
		System.out.println(testBoard);
		// Test toString() method. Expect a blank board.
		
		Scanner testInput = new Scanner(System.in);

		testFrame.refill(testPool);
		System.out.println(testFrame);
		// It has to start at position H8 which is the center.
		testBoard.putNewWord("H8", "A", testInput.next(), testFrame);
		testFrame.refill(testPool);
		System.out.println(testBoard);
		System.out.println(testFrame);

		// Change the position and direction from the keyboard input.
		System.out.printf("Give the postion in this formate H8 (is center): ");
		postion = testInput.next();
		System.out.printf("Give the direction\nA - horizontally\tD - vertically\n");
		direction = testInput.next();
		testBoard.putNewWord(postion.toUpperCase(), direction, testInput.next(), testFrame);
		System.out.println("Pool of size: "+testPool.size()); // depends of the taken letters.
		empty = testFrame.isEmpty();
		System.out.printf("Is frame empty? %b \n",empty);  // expect:false
		testFrame.refill(testPool);
		System.out.println(testBoard);
		System.out.println(testFrame);

		// Change the position and direction from the keyboard input.
		System.out.printf("Give the postion in this formate H8 (is center): ");
		postion = testInput.next();
		System.out.printf("Give the direction\nA - horizontally\tD - vertically\n");
		direction = testInput.next();
		testBoard.putNewWord(postion.toUpperCase(), direction, testInput.next(), testFrame);
		System.out.println(testBoard);
		System.out.println(testFrame);

		testInput.close();
	}

}