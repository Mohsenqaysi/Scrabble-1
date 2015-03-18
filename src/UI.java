/**
 * @author: Yongzhen Ren
 */
public class UI
{
	public void promptName(String name)
	{
		System.out.printf("[%s] Please enter your name: ", name);
	}
	
	public void promptCommand(String name)
	{
		System.out.printf("[%s] Please input your command: ", name);
	}

	public void displayGameBoard(Player player, Board board)
	{
		System.out.println(board);
		System.out.printf("%s's score: %d\n", player.getName(), player.getScore());
		System.out.printf("%s's frame: %s\n", player.getName(), player.getFrame());
	}

	public void displayFinalScores(Player player1, Player player2)
	{
		System.out.println("Final scores:");
		System.out.printf("%s's score: %d\n", player1.getName(), player1.getScore());
		System.out.printf("%s's score: %d\n", player2.getName(), player2.getScore());
		if ( player1.getScore() > player2.getScore() )
		{
			System.out.printf("%s is winner!\n", player1.getName());
		}
		if ( player1.getScore() < player2.getScore() )
		{
			System.out.printf("%s is winner!\n", player2.getName());
		}
		if ( player1.getScore() == player2.getScore() )
		{
			System.out.println("The game ended in a tie.");
		}
	}
	
	public void displayPass(Player player)
	{
		System.out.printf("%s chooses to pass.\n", player.getName());
	}
	
	public void displayQuit(Player player)
	{
		System.out.printf("%s gives up. Game over.\n", player.getName());
	}
	
	public void displayExchangeError()
	{
		System.out.println("[ERROR] Cannot exchange tiles in the frame.");
		System.out.println("Please check your input again.");
	}
	
	public void displayPlacementError()
	{
		System.out.println("[ERROR] Cannot put the word on the board.");
		System.out.println("Please try again.");
	}
	
	private void displayOptionError()
	{
		System.out.println("Invalid input. Please enter HELP for further information.");
	}
	
	public void displayUnexpectedError()
	{
		System.out.println("[ERROR] An unexpected error occurs.");
	}
	
	private void displayHelpInfo()
	{
		String helpInfo = "<Grid Ref> <Across / Down> <Word> (Put a new word on the board)\n"
						+ "    Grid Ref: the position for the first letter in <word>,\n"
						+ "              whose format is [Letter (A - O)][Number (1 - 15)],\n"
						+ "    Across / Down: the direction of word placement\n"
						+ "                   whose possible input is [A / D]\n"
						+ "    Word: the word (length 1 - 15) to be placed.\n"
						+ "    For example, \"A1 A HELLO\".\n"
						+ "EXCHANGE <A Sequence of Letters>\n    Exchange these letters from the frame with random letters from the pool.\n"
						+ "PASS\n    Pass current move.\n"
						+ "HELP\n    Display help information (the one that you are reading now >.<').\n"
						+ "QUIT\n    Quit the game immediately.\n"
						+ "N.B.: all input here is case-insensitive.\n";
		System.out.println(helpInfo);
	}

	/**
	 * The function parses user's input and return the corresponding value.
	 * @param input 
	 * @return -1 - Display help information or 
	 */
	public int parseInput(String input)
	{
		// Java uses (?i) to make a regex case-insensitive instead of "/ ... /i".
		if ( input.matches("(?i)^\\s*HELP\\s*$") )
		// "HELP" option.
		{
			displayHelpInfo();
			return -1;
		}
		if ( input.matches("(?i)^\\s*QUIT\\s*$") )
		// "QUIT" option.
		{
			return 0;
		}
		if ( input.matches("(?i)^\\s*PASS\\s*$") )
		// "PASS" option.
		{
			return 1;
		}
		if ( input.matches("(?i)^\\s*EXCHANGE\\s+[@A-Z]{1,7}\\s*$") )
		// "EXCHANGE" option. N.B.: user can exchange the blank tile although no one will do that.
		{
			return 2;
		}
		if ( input.matches("(?i)^\\s*[A-O](1[0-5]|[1-9]|0[1-9])\\s+[AD]\\s+[@A-Z]{1,15}\\s*$") )
		// Place tiles. N.B.: user can put blank tiles on the board *directly*.
		{
			return 3;
		}
		else
		{
			displayOptionError();
			return -1;
		}
	}
}