/**
 * @author: Yongzhen Ren
 */
import java.util.Scanner;

public class Scrabble
{
	static Pool pool; ///////////////////////////////////////////////////////// NEED TO BE FIXED LATER!!!
	private static Board board;
	private static UI ui;
	private static Scanner userInput;
	private static Player player1;
	private static Player player2;
	private static final String defaultName1 = "Player1";
	private static final String defaultName2 = "Player2";

	Scrabble()
	// Initialisation.
	{
		pool = new Pool();
		board = new Board();
		ui = new UI();
		userInput = new Scanner(System.in);
		player1 = new Player();
		player2 = new Player();
		player1.getFrame().refill(pool);
		player2.getFrame().refill(pool);
	}

	/**
	 * The function sets names for both players.
	 * 
	 * N.B.: Two players can have the same name;
	 *       if user inputs nothing for name prompt, the default name will be set.
	 */
	public void setName()
	{
		String buffer;

		ui.promptName(defaultName1);
		buffer = userInput.nextLine();
		if ( buffer.compareTo("") == 0 )
		{
			player1.setName(defaultName1);
		}
		else
		{
			player1.setName(buffer);
		}
		ui.promptName(defaultName2);
		buffer = userInput.nextLine();
		if ( buffer.compareTo("") == 0 )
		{
			player2.setName(defaultName2);
		}
		else
		{
			player2.setName(buffer);
		}
	}

	public void eachTurnOfGame(Player player)
	{
		String command;
		String[] arguments;
		int returnState;
		boolean finishOrNot = false;
		// The variable checks if a specific action is finished correctly.
		ui.displayGameBoard(player, board);
		do
		{
			do
			{
				ui.promptCommand(player.getName());
				command = userInput.nextLine();
				// Trim newline character automatically.
				returnState = ui.parseInput(command);
			}
			while ( returnState < 0 );
			
			switch ( returnState )
			{
				case 0: // "QUIT" option.
					ui.displayQuit(player);
					System.exit(0);
					break;
				case 1: // "PASS" option.
					ui.displayPass(player);
					finishOrNot = true;
					break;
				case 2: // "EXCHANGE" option.
					arguments = command.split("\\s");
					if ( player.getFrame().isAvailable(arguments[1]) )
					{
						player.getFrame().remove(arguments[1]);
						player.getFrame().refill(pool);
						finishOrNot = true;
					}
					else
					{
						ui.displayExchangeError();
					}
					break;
				case 3: // Place tiles.
					arguments = command.split("\\s");
					if ( board.putNewWord(arguments[0], arguments[1], arguments[2], player) )
					{
						player.getFrame().refill(pool);
						finishOrNot = true;
					}
					else
					{
						ui.displayPlacementError();
					}
					break;
				default: // Unexpected error.
					ui.displayUnexpectedError();
					break;
			}
		}
		while ( !finishOrNot );
	}

	public static void main(String[] args)
	{
		Scrabble scrabble = new Scrabble();
		scrabble.setName();
		
		while ( !pool.isEmpty() )
		{
			scrabble.eachTurnOfGame(player1);
			scrabble.eachTurnOfGame(player2);
		}
		ui.displayFinalScores(player1, player2);
		userInput.close();
	}
}
