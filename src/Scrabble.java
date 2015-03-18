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
	private static final int consecutiveZeroScores = 6;

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

	/**
	 * The function stimulates each turn of the game.
	 * @param player is an object containing information about a specific player.
	 * @return pass: if a pass occurs in this turn.
	 */
	public boolean eachTurnOfGame(Player player)
	{
		String command;
		String[] arguments;
		int returnState;
		boolean finishOrNot = false, passOrNot = false;
		// finishOrNot checks if a specific action is finished correctly.
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
					passOrNot = true;
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
				default: // Unexpected error, usually unreachable.
					ui.displayUnexpectedError();
					break;
			}
		}
		while ( !finishOrNot );
		return passOrNot;
	}
	
	// The function is to check if all elements in array are true.
	private static boolean ifSixZeros(boolean[] array)
	{
		for ( int i = 0; i < consecutiveZeroScores; i++ )
		{
			if ( array[i] == false )
			{
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args)
	{
		boolean[] pass = new boolean[consecutiveZeroScores];
		/*
		 *  The default value for the elements in a boolean[] is false.
		 *  The variable is to use check if there is a consecutive-scores-of-zero in recent 6 turns.
		 */
		int turns = 0;
		Scrabble scrabble = new Scrabble();
		scrabble.setName();
		
		/*
		 * In theory, the game will end normally simply because one of racks is empty
		 * or after six consecutive scores of zero (i.e. three consecutive passes or false-words per player).
		 * The rule is from: http://www.word-buff.com/when-does-a-scrabble-game-end.html
		 */
		do
		{
			if ( scrabble.eachTurnOfGame(player1) )
			// If a pass does occur in the last turn.
			{
				pass[turns % consecutiveZeroScores] = true;
			}
			turns++;
			if ( player1.getFrame().isEmpty() || ifSixZeros(pass) )
			// Check the state of the pool right after each turn.
			{
				break;
			}
			if ( scrabble.eachTurnOfGame(player2) )
			{
				pass[turns % consecutiveZeroScores] = true;
			}
			turns++;
			if ( player2.getFrame().isEmpty() || ifSixZeros(pass) )
			// Check the state of the pool right after each turn.
			{
				break;
			}
		}
		while ( true );
		ui.displayFinalScores(player1, player2);
		userInput.close();
	}
}