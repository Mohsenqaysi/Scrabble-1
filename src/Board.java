/**
 * Implement a class called Board that:
 * 1. Allows the board to be reset;
 * 2. Stores the current tile positions;
 * 3. Stores the square values (e.g. triple word score);
 * 4. Allows a word placement to be checked to determine if it is legal or not;
 * 5. Allows a word to be placed on the board;
 * 6. Displays the current board using ASCII characters on the console;
 * 7. Calculate scores for each player.
 * 
 * @author Yongzhen Ren & Dylan Dowling
 */

public class Board
{
	private class Cell
	{
		public char letter = ' '; // Set to blank before a tile is put.
		public boolean wordOrLetter;
		/*
		 * true: double-word or triple-word; false: double-letter or triple-letter.
		 * When times is equal to 1, this option will be IGNORED.
		 */
		public int times;
		// The variable can only contain 1, 2, or 3.
	}
	
	private static final int LENGTH_OF_BOARD = 15;
	private static final int CENTRE_INDEX = LENGTH_OF_BOARD / 2;
	private Cell[][] board;

	Board()
	{
		reset();
		/*
		 *  The layout of Scrabble board is available in ./Scrabble/doc/ directory and comes from:
		 *  http://upload.wikimedia.org/wikipedia/commons/c/c2/Blank_Scrabble_board_with_coordinates.svg
		 */
	}

	public void reset()
	{
		board = new Cell[LENGTH_OF_BOARD][LENGTH_OF_BOARD];
		for ( int i = 0; i < LENGTH_OF_BOARD; i++ )
		{
			for ( int j = 0; j < LENGTH_OF_BOARD; j++ )
			{
				board[i][j] = new Cell();
			}
		}
		/*
		 * The whole board is divided into 4 sub-squares evenly.
		 * The code below is to assign values of double/triple word/letter to specific cells.
		 */
		for ( int i = 0; i <= CENTRE_INDEX; i++ )
		{
			for ( int j = 0; j <= CENTRE_INDEX; j++ )
			{
				if ( (i == j) && (((i >= 1) && (i <= 4)) || (i == 7)) )
				// 2x WS (double word).
				{
					set(i, j, true, 2);
				}
				else
				{
					if ( ((i == 0) && (j == 0)) ||
						 ((i == 0) && (j == 7)) || ((i == 7) && (j == 0)) )
					// 3x WS (triple word).
					{
						set(i, j, true, 3);
					}
					else
					{
						if ( ((i == 0) && (j == 3)) || ((i == 3) && (j == 0)) ||
							 ((i == 2) && (j == 6)) || ((i == 6) && (j == 2)) ||
							 ((i == 3) && (j == 7)) || ((i == 7) && (j == 3)) ||
							 ((i == 6) && (j == 6)) )
						// 2x LS (double letter).
						{
							set(i, j, false, 2);
						}
						else
						{
							if ( ((i == 5) && (j == 1)) || ((i == 1) && (j == 5)) ||
								 ((i == 5) && (j == 5)) )
							// 3x LS (triple letter).
							{
								set(i, j, false, 3);
							}
							else
							{
								set(i, j, true, 1);
								// Once again, the value true here does not matter at all.
							}
						}
					}
				}
			}
		}
	}
	
	private void set(int i, int j, boolean wordOrLetter, int times)
	// According to the position of one cell, set the same value for all four symmetric positions on the board.
	{
		setOneCell(i, j, wordOrLetter, times); // Top left.
		setOneCell(LENGTH_OF_BOARD - 1 - i, j, wordOrLetter, times); // Bottom left.
		setOneCell(i, LENGTH_OF_BOARD - 1 - j, wordOrLetter, times); // Top right.
		setOneCell(LENGTH_OF_BOARD - 1 - i, LENGTH_OF_BOARD - 1 - j, wordOrLetter, times); // Bottom right.
	}
	
	private void setOneCell(int i, int j, boolean wordOrLetter, int times)
	{
		board[i][j].wordOrLetter = wordOrLetter;
		board[i][j].times = times;
	}

	/**
	 * The method is to try to put a new word on the board and calculate the score according to current placement.
	 * 
	 * @param startingPosition is a string with such format: [Letter (A/a - O/o)][Number (1 - 15)],
	 * where letter means column index and number means row index; e.g: "A4", "F15", "O2".
	 * @param direction holds two possible values: A/a - across (horizontally) or D/d - down (vertically).
	 * @param word is a string inputed by user with length 1 - 15.
	 * @param frame is a rack storing letters that can be used currently.
	 * 
	 * @return successOrNot is a variable that shows if the placement can be done successfully.
	 */
	public boolean putNewWord(String startingPosition, String direction, String word, Player player)
	{
		boolean successOrNot;
		int column = startingPosition.toUpperCase().charAt(0) - 'A';
		// Convert an upper case letter to index.
		int row = Integer.parseInt(startingPosition.substring(1)) - 1;
		// Convert index on the board to one in the array by subtracting 1.
		word = word.toUpperCase();
		direction = direction.toUpperCase();
		// Convert strings to upper case ones.
		
		if ( isPlacementLegal(row, column, direction, word, player.getFrame()) )
		{
			if ( direction.equals("A") )
			// Put the word horizontally.
			{
				for ( int i = 0; i < word.length(); i++ )
				{
					board[row][i + column].letter = word.charAt(i);
					player.addScore(checkAroundPlacedTilesAndAddScore(i,row, column, word.length()));
					player.addScore(Scrabble.pool.getValue(word.charAt(i))); /////////////////////////////////////
				}
			}
			else
			{
				if ( direction.equals("D") )
				// Put the word vertically.
				{
					for ( int i = 0; i < word.length(); i++ )
					{
						board[i + row][column].letter = word.charAt(i);
						player.addScore(checkAroundPlacedTilesAndAddScore(i,row, column, word.length()));
						player.addScore(Scrabble.pool.getValue(word.charAt(i))); /////////////////////////////////////
					}
				}
			}
			successOrNot = true;
		}
		else
		{
			successOrNot = false;
		}
		return successOrNot;
	}
	
	private int checkAroundPlacedTilesAndAddScore(int i, int row, int column, int wordLength)
	{
		int tempScoreMult = 0;
		int tempScoreAdd = 0;
		
		// Checking left.
		if (board[row][i + column - 1].letter != ' ' )
		{
			for ( int j = 1; board[row][i + column - j].letter != ' '; j++ )
			{
				tempScoreAdd += Scrabble.pool.getValue(board[row][i + column - j].letter);
				// Checking for triple word square, across
				if  (board[row][i + column - j].wordOrLetter && board[row][i + column - j].times == 3 )
				{
					tempScoreMult += 3;
					board[row][i + column - j].times = 5; // Considered used now so set to variable out of range to break condition
					//System.out.println("Got here 1");
				}
				// Checking for double word, across
				if ( board[row][i + column - j].wordOrLetter && board[row][i + column - j].times == 2 )
				{
					tempScoreMult += 2;
					board[row][i + column - j].times = 5;
				}
				
				// Checking triple letter, across
				if ( !board[row][i + column - j].wordOrLetter && board[row][i + column - j].times == 3 )
				{
					tempScoreAdd += (Scrabble.pool.getValue(board[row][i + column - j].letter) * 2);
					board[row][i + column - j].times = 5;
					//System.out.println("Got here 3");
				}
				
				// Checking double letter, across
				if ( !board[row][i + column - j].wordOrLetter && board[row][i + column - j].times == 2 )
				{
					tempScoreAdd += Scrabble.pool.getValue(board[row][i + column - j].letter);
					board[row][i + column - j].times = 5;
					//System.out.println("Got here 4");
				}
			}
		}
		
		// Checking right.
		if ( board[row][i + column + 1].letter != ' ' )
		{
			for ( int j = 1; board[row][i + column + wordLength + j].letter != ' '; j++ )
			{
				tempScoreAdd += Scrabble.pool.getValue(board[row][i + column + wordLength + j].letter);
				
				// Checking for triple word square, across
				if  (board[row][i + column + wordLength + j].wordOrLetter && board[row][i + column + wordLength + j].times == 3 )
				{
					tempScoreMult += 3;
					board[row][i + column + wordLength + j].times = 5; // Considered used 
					//System.out.println("Got here 5");
				}
				// Checking for double word, across
				if ( board[row][i + column + wordLength + j].wordOrLetter && board[row][i + column + wordLength + j].times == 2 )
				{
					tempScoreMult += 2;
					board[row][i + column + wordLength + j].times = 5;
					//System.out.println("Got here 6");
				}
				
				// Checking triple letter, across
				if ( !board[row][i + column + wordLength + j].wordOrLetter && board[row][i + column  + wordLength + j].times == 3 )
				{
					tempScoreAdd += (Scrabble.pool.getValue(board[row][i + column + wordLength + j].letter) * 2);
					board[row][i + column + wordLength + j].times = 5;
					//System.out.println("Got here 7");
				}
				
				// Checking double letter, across
				if ( !board[row][i + column + wordLength + j].wordOrLetter && board[row][i + column + wordLength + j].times == 2 )
				{
					tempScoreAdd += Scrabble.pool.getValue(board[row][i + column + wordLength + j].letter);
					board[row][i + column + wordLength + j].times = 5;
					//System.out.println("Got here 8");
				}
			}
		}
		
		// Checking above
		if ( board[i + row - 1][column].letter != ' ' )
		{
			for ( int j = 1; board[i + row - wordLength - j][column].letter != ' '; j++ )
			{
				tempScoreAdd += Scrabble.pool.getValue(board[i + row - wordLength - j][column].letter);
				
				// Checking for triple word, down
				if ( board[i + row - wordLength - j][column].wordOrLetter && board[i + row - wordLength - j][column].times == 3 )
				{
					tempScoreMult += 3;
					board[i + row - wordLength - j][column].times = 5;
					//System.out.println("Got here 9");
				}
				// Checking for double word, down
				if ( board[i + row - wordLength - j][column].wordOrLetter && board[i + row - wordLength - j][column].times == 2 )
				{
					tempScoreMult += 2;
					board[i + row - wordLength - j][column].times = 5;
					//System.out.println("Got here 10");
				}
				
				// Checking triple letter, down
				if ( !board[i + row - wordLength - j][column].wordOrLetter && board[i + row - wordLength - j][column].times == 3 )
				{
					tempScoreAdd += (Scrabble.pool.getValue(board[i + row - wordLength - j][column].letter) * 2);
					board[i + row - wordLength - j][column].times = 5;
					//System.out.println("Got here 11");
				}
						
				// Checking double letter, down
				if ( !board[i + row - wordLength - j][column].wordOrLetter && board[i + row - wordLength - j][column].times == 2 )
				{
					tempScoreAdd += Scrabble.pool.getValue(board[i + row - wordLength - j][column].letter);
					board[i + row - wordLength - j][column].times = 5;
					//System.out.println("Got here 12");
				}
			}
		}
		
		// Checking below.
		if ( board[i + row + 1][column].letter != ' ' )
		{
			for (int j=1; board[i + row + j][column].letter != ' '; j++)
			{
				tempScoreAdd += Scrabble.pool.getValue(board[i + row + j][column].letter);
				
				// Checking for triple word, down
				if ( board[i + row + j][column].wordOrLetter && board[i + row + j][column].times == 3 )
				{
					tempScoreMult += 3;
					board[i + row + j][column].times = 5;
					//System.out.println("Got here 13");
				}
				// Checking for double word, down
				if ( board[i + row + j][column].wordOrLetter && board[i + row + j][column].times == 2 )
				{
					tempScoreMult += 2;
					board[i + row + j][column].times = 5;
					//System.out.println("Got here 14");
				}
				
				// Checking triple letter, down
				if ( !board[i + row + j][column].wordOrLetter && board[i + row + j][column].times == 3 )
				{
					tempScoreAdd += (Scrabble.pool.getValue(board[i + row + j][column].letter) * 2);
					board[i + row + j][column].times = 5;
					//System.out.println("Got here 15");
				}
						
				// Checking double letter, down
				if ( !board[i + row + j][column].wordOrLetter && board[i + row + j][column].times == 2 )
				{
					tempScoreAdd += Scrabble.pool.getValue(board[i + row + j][column].letter);
					board[i + row + j][column].times = 5;
					//System.out.println("Got here 16");
				}
			}
		}
		
		if ( tempScoreMult < 1 )
		{
			tempScoreMult = 0;
		}
		else
		{
			tempScoreAdd *= tempScoreMult;
		}
		
		return tempScoreAdd;
	}
	
	/**
	 * The method checks if the current word can be placed on the board legally.
	 * 
	 * @return if it can be, remove relative letters from the frame and return true;
	 * if it cannot be placed, return false.
	 */
	private boolean isPlacementLegal(int row, int column, String direction, String word, Frame frame)
	{
		String tilesShouldInFrame = word;
		/*
		 * tilesShouldInFrame should contain all letters used in this move (excluding some on the board already),
		 * which needs to appear in the frame currently and will be used in isAvailable() method.
		 */
		int timesOfSharingLetters = 0;
		// The variable shows times of sharing letters with previous words on the board.
		boolean isFirstPlacement; // The variable records if it is the first placement.

		if ( board[CENTRE_INDEX][CENTRE_INDEX].letter == ' ' )
		// If it is the first placement.
		{
			isFirstPlacement = true;
			if ( !isAtStarSquare(row, column, direction, word.length()) )
			// If the first placement is not on the star square.
			{
				System.out.println("[ERROR] The first word should be placed on the star square at the centre of the board.");
				return false;
			}
		}
		else
		{
			isFirstPlacement = false;
		}

		if ( !isWithinBounds(row, column, direction, word.length()) )
		// If the placement is out of the bounds of the board.
		{
			System.out.println("[ERROR] The placement is out of the bounds of the board.");
			return false;
		}

		// Further checking on whether a conflict with previous tiles on the board will arise.
		if ( direction.equals("A") )
		// Put the word horizontally.
		{
			for ( int i = 0; i < word.length(); i++ )
			{
				if ( board[row][i + column].letter != ' ' )
				// If it is already occupied.
				{
					if ( board[row][i + column].letter == word.charAt(i) )
					// Overlay correctly.
					{
						int tempIndex = tilesShouldInFrame.indexOf(word.charAt(i));
						// Get the index of the letter needed to be removed in tilesShouldInFrame.
						tilesShouldInFrame = tilesShouldInFrame.substring(0, tempIndex) + tilesShouldInFrame.substring(tempIndex + 1);
						// Remove the overlaid letter from tileShouldInFrame.
						timesOfSharingLetters++; // Record the successful checking.
					}
					else
					// A conflict occurs.
					{
						System.out.println("[ERROR] The placement overlays previous ones on the board incorrectly.");
						return false;
					}
				}
			}
		}
		else
		{
			if ( direction.equals("D") )
			// Put the word vertically.
			{
				for ( int i = 0; i < word.length(); i++ )
				{
					if ( board[i + row][column].letter != ' ' )
					// If it is already occupied.
					{
						if ( board[i + row][column].letter ==  word.charAt(i) )
						// Overlay correctly.
						{
							int tempIndex = tilesShouldInFrame.indexOf(word.charAt(i));
							// Get the index of the letter needed to be removed in tilesShouldInFrame.
							tilesShouldInFrame = tilesShouldInFrame.substring(0, tempIndex) + tilesShouldInFrame.substring(tempIndex + 1);
							// Remove the overlaid letter from tileShouldInFrame.
							timesOfSharingLetters++; // Record the successful checking.
						}
						else
						// A conflict occurs.
						{
							System.out.println("[ERROR] The placement overlays previous ones on the board incorrectly.");
							return false;
						}
					}
				}
			}
		}

		if ( (timesOfSharingLetters == 0) && !isFirstPlacement )
		// If current placement does not use any tiles on the board previously and it is not the first move.
		{
			System.out.println("[ERROR] The word has to connect with words already on the board.");
			return false;
		}

		if ( timesOfSharingLetters == word.length() )
		// If user just inputs the same word in the same position instead of extending it into a new word.
		{
			System.out.println("[ERROR] The same word exists in the same position.");
			return false;
		}

		if ( !frame.isAvailable(tilesShouldInFrame) )
		// If some letters used in the move are not available in the frame.
		{
			System.out.println("[ERROR] Letter inputted does not exist in the frame.");
			return false;
		}

		frame.remove(tilesShouldInFrame); // Remove these letters in the frame.
		return true;
	}

	private boolean isAtStarSquare(int row, int column, String direction, int length)
	{
		if ( (direction.equals("A") && (row == CENTRE_INDEX) && (column <= CENTRE_INDEX) &&
			 (column + length - 1 >= CENTRE_INDEX)) ||
			 (direction.equals("D") && (column == CENTRE_INDEX) && (row <= CENTRE_INDEX) &&
			 (row + length - 1 >= CENTRE_INDEX)) )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isWithinBounds(int row, int column, String direction, int length)
	{
		if ( (direction.equals("A") && (column + length - 1 >= LENGTH_OF_BOARD)) ||
			 (direction.equals("D") && (row + length - 1 >= LENGTH_OF_BOARD)) )
		// Check whether the placement is within the bounds of the board.
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public String getBoard()
	{
		StringBuilder currentBoard = new StringBuilder("");
		int i;
		currentBoard.append("     "); // Five spaces before the sequence.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		// A sequence of A - O.
		{
			currentBoard.append((char) (i + 'A'));
			currentBoard.append(' ');
		}
		currentBoard.append("\n   _________________________________\n"); // Top border.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		{
			currentBoard.append(String.format("%02d", i + 1));
			currentBoard.append(" | "); // Left border.
			for ( int j = 0; j < LENGTH_OF_BOARD; j++ )
			{
				currentBoard.append(board[i][j].letter);
				currentBoard.append(' ');
			}
			currentBoard.append("| " + String.format("%02d", i + 1) + '\n'); // Right border.
		}
		currentBoard.append("   ‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾\n"); // Bottom border.
		currentBoard.append("     "); // Five spaces before the sequence.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		// A sequence of A - O.
		{
			currentBoard.append((char) (i + 'A'));
			currentBoard.append(' ');
		}
		return currentBoard.toString();
	}

	public String toString()
	// Output the current tile positions.
	{
		return getBoard();
	}
}