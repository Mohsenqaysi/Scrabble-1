/*
author Mohsen Qaysi
*/

import java.util.Scanner;

public class BoardTest {
	public static void main(String[] args) {
		String postion = "", direction = "", ans = "";
		boolean empty;
		Frame frame;
		Pool testPool = new Pool();
		Frame testFrame = new Frame();
		Board testBoard = new Board();

		System.out.println("Pool of size: " + testPool.size());
		empty = testFrame.isEmpty();
		System.out.printf("Is frame empty? %b \n", empty); // expect:true
		System.out.println(testBoard);
		// Test toString() method. Expect a blank board.

		Scanner testInput = new Scanner(System.in);

		// keep looping until the pool is Empty
		while (!testPool.isEmpty()) {
			System.out.println("Pool of size: " + testPool.size());

			testFrame.refill(testPool);
			System.out.println("Frame: " + testFrame);
			// Change the position and direction from the keyboard input.
			System.out.printf("Give the position in this formate H8 (is Center): ");
			postion = testInput.next();
			System.out.printf("Give the direction\nA - horizontally\tD - vertically\n");
			// Be careful of your input here:
			direction = testInput.next();
			testBoard.putNewWord(postion.toUpperCase(), direction,testInput.next(), testFrame);
			System.out.println(testBoard);

			System.out.printf("Do you want to keep playing: [y/n]: ");
			ans = testInput.next();

			if (ans.equalsIgnoreCase("y")) {
				continue;
			} else if (ans.equalsIgnoreCase("n")) {
				System.out.println("The game end");
				break;

			} else {
				System.out.println("Error an accesptable input ...  game ended");
				break;
			}

		}
		System.out.println("Pool of size: " + testPool.size());
		System.out.println("Letters left in the Frame: " + testFrame);

		testInput.close();
	}

}