import java.util.Scanner;

/*
 * author: Mohsen Qaysi
 */

public class UI {

	Pool pool = new Pool();
	Frame frame = new Frame();

	public static void main(String[] args) {
		UI test = new UI();
		String input = keyBoardInputsScanner();
		test.keyBoardInputsDetector(input);

	}

	public static String keyBoardInputsScanner() {
		boolean condition = true;
		Scanner userInputer = new Scanner(System.in);
		// System.out.println("Enter: ");
		// String input = userInputer.next();

		while (condition) {
			System.out.println("Enter: ");
			String input = userInputer.next();

			if (input.equalsIgnoreCase("pass")
					|| input.equalsIgnoreCase("quit")
					|| input.equalsIgnoreCase("help")
					|| input.equalsIgnoreCase("exchange")) {
				condition = false;
				return input;

			} else {
				System.out.println("The keyWord you enterd is not detected in our list:\n"
								+ "Please Try again 111:\n");
				// continue;
				condition = true;

			}
		}
		return null;

	}

	public void keyBoardInputsDetector(String input) {
		Scanner letterInput = new Scanner(System.in);

		if (input.equalsIgnoreCase("pass")) {
			System.out.println("pass is used");

		}
		if (input.equalsIgnoreCase("quit")) {
			System.out.println("Game Over ....");

		}
		if (input.equalsIgnoreCase("help")) {
			System.out
					.println("HELP MENUE:"
							+ "\n"
							+ "- <grid ref> e.g. <H8> is the position for the first letter\n"
							+ "- <across/down> is the direction of word placement\n"
							+ "- <word> is the word to be placed), e.g. “A1 A HELLO”\n"
							+ "- <letters> (exchanges these letters from the frame\n\t\twith random letters from the pool).");

		}
		if (input.equalsIgnoreCase("exchange")) {
//			Player player = new Player();
//			player.getFrame();
			frame.refill(pool);
			System.out.println("Frame: " + frame);
			System.out.println("Your Frame is: " + frame);

			System.out.println("Enter the number of letters you want to exchange folloed by letter(s):");
			
			int number = letterInput.nextInt();
			for(int i=0;  i < number; i++){
			String letter = letterInput.next();
			changeLetterInFrame(letter);
			}
//			frameExchanger(Pool pool, number);
		}

	}

	public void changeLetterInFrame(String letter) {

		frame.remove(letter.toUpperCase());
		System.out.println("The letter is: " + letter);
		frame.refill(pool);
		System.out.println("Frame: " + frame);

	}

//	private static StringBuilder frameExchanger(Pool pool, int tileNumberToExchange) {
//		StringBuilder tiles = new StringBuilder("");
//		
//		int numTilesToDraw;
//		String newTiles;
//		numTilesToDraw = 2 - tiles.length();
//		newTiles = pool.drawTiles(numTilesToDraw);
//		tiles = tiles.append(newTiles);
//		return tiles;
//	}
	
	
}

 
