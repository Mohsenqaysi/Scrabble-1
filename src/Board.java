public class Board
{
	private static final int LENGTH_OF_BOARD = 15;
	private static final int CENTRE_INDEX = LENGTH_OF_BOARD / 2;
	private Cell[][] board;

	class Cell
	{
		public char letter;
		public boolean wordOrLetter;
		/*
		 * true: double-word or triple-word; false: double-letter or triple-letter.
		 * When times is equal to 1, this option will be ignored.
		 */
		public int times; // times can only be 1, 2, or 3.
	}

	public Board()
	{
		reset();
		/*
		 *  The layout of Scrabble board comes from:
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
		} // Initialise Cell class.
		/*
		 * The whole board is divided into 4 sub-board.
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
		board[i][j].letter = ' '; // Set to blank before a tile is put.
		board[i][j].wordOrLetter = wordOrLetter;
		board[i][j].times = times;
	}

	public void putNewWord(String startingPosition, String direction, String word, Frame frame)
	/*
	 * startingPosition is a string with such format: <Letter (A/a - O/o)><Number (1 - 15)>,
	 * where letter means column index. and number means row index, e.g: "A4", "F15", "O2";
	 * direction contains two possible values: A/a - across and D/d - down;
	 * word is a word with length 1 - 15.
	 * Here we assume that user's input is free from any semantic errors all the time.
	 * Precondition: isPlacementLegal(word) is true.
	 */
	{
		int column = startingPosition.toUpperCase().charAt(0) - 'A';
		// Convert an upper case letter to index.
		int row = startingPosition.charAt(1) - '0' - 1;
		// Convert index on the board to one in the array by subtracting 1.
		word = word.toUpperCase(); // Convert word to upper case one.
		
		if ( isPlacementLegal(row, column, direction, word, frame) )
		{
			if ( direction.equalsIgnoreCase("A") )
			{
				for ( int i = column; i < column + word.length(); i++ )
				{
					board[row][i].letter = word.charAt(i - column);
				}
			}
			else
			{
				for ( int i = row; i < row + word.length(); i++ )
				{
					board[i][column].letter = word.charAt(i - row);
				}
			}
		}
		else
		{
			System.out.println("Cannot put the word on the board.");
		}
	}
	
	/*
	 * The function checks if the current word can be placed on the board legally.
	 * If it can be, remove relative letters from the frame and return true;
	 * if it cannot, return false.
	 */
	private boolean isPlacementLegal(int row, int column, String direction, String word, Frame frame)
	{
		String tilesShouldInFrame = word;
		/*
		 * Create a copy of word, used in isAvailable() method.
		 * tilesShouldInFrame contains all letters used in this move (excluding some on the board already),
		 * which should appear in the frame currently.
		 */
		int timesOfSharingLetters = 0;
		// The variable shows times of sharing letters with previous words on the board.
		boolean isFirstWord; // The variable records if it is the first word.

		if ( board[CENTRE_INDEX][CENTRE_INDEX].letter == ' ' )
		// If it is the first placement.
		{
			isFirstWord = true;
			if ( !isAtStarSquare(row, column, word.length()) )
			{
				System.out.println("[ERROR] The first word should be placed on the star square at the center of the board.");
				return false;
			}
		}
		else
		{
			isFirstWord = false;
		}

		if ( !isInDictionary(word) )
		{
			System.out.println("[ERROR] The word inputted cannot be found in the dictionary.");
			return false;
		}

		if ( !isWithinBounds(row, column, direction, word.length()) )
		{
			System.out.println("[ERROR] The placement is out of the bound of the board.");
			return false;
		}

		if ( direction.equalsIgnoreCase("A") ) // Put the word horizontally.
		{
			for ( int i = 0; i < word.length(); i++ )
			{
				if ( board[row][i + column].letter != ' ' )
				// If it is already occupied.
				{
					if ( board[row][i + column].letter != word.charAt(i) )
					// Overlay incorrectly.
					{
						System.out.println("[ERROR] The current placement overlays the previous one on the board incorrectly.");
						return false;
					}
					else
					{
						int tempIndex = tilesShouldInFrame.indexOf((word.charAt(i)));
						tilesShouldInFrame = tilesShouldInFrame.substring(0, tempIndex) + tilesShouldInFrame.substring(tempIndex + 1);
						// Remove the overlaid letter from tileShouldInFrame.
						timesOfSharingLetters++; // Overlay correctly.
					}
				}
			}
		}
		else
		{
			if ( direction.equalsIgnoreCase("D") ) // Put the word vertically.
			{
				for ( int i = 0; i < word.length(); i++ )
				{
					if ( board[i + row][column].letter != ' ' )
					// If it is already occupied.
					{
						if ( board[i + row][column].letter !=  word.charAt(i) )
						{
							System.out.println("[ERROR] The current placement overlays the previous one on the board incorrectly.");
							return false;
						}
						else
						{
							int tempIndex = tilesShouldInFrame.indexOf((word.charAt(i)));
							tilesShouldInFrame = tilesShouldInFrame.substring(0, tempIndex) + tilesShouldInFrame.substring(tempIndex + 1);
							// Remove the overlaid letter from tileShouldInFrame.
							timesOfSharingLetters++; // Overlay correctly.
						}
					}
				}
			}
		}

		if ( (timesOfSharingLetters == 0) && !isFirstWord )
		{
			System.out.println("[ERROR] The word has to connect with words already on the board.");
			return false;
		}

		if ( timesOfSharingLetters == word.length() )
		// User just inputs the same word in the same position instead of extending it into a new word.
		{
			System.out.printf("%d\n", timesOfSharingLetters);
			System.out.println("[ERROR] The same word exists in the same position.");
			return false;
		}

		if ( !frame.isAvailable(tilesShouldInFrame.toString()) )
		{
			System.out.println("[ERROR] Letter inputted does not exist in the frame.");
			return false;
		}
		else
		{
			if ( direction.equalsIgnoreCase("A") )
			{
				
			}
			else
			{
				if ( direction.equalsIgnoreCase("D") )
				{
					
				}
			}
		}

		frame.remove(tilesShouldInFrame); // Remove these letters in the frame.
		return true;
	}

	private boolean isAtStarSquare(int row, int column, int length)
	{
		if ( ((row == CENTRE_INDEX) && (column <= CENTRE_INDEX) && (column + length >= CENTRE_INDEX)) ||
			 ((column == CENTRE_INDEX) && (row <= CENTRE_INDEX) && (row + length >= CENTRE_INDEX)) )
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
		if ( (direction.equalsIgnoreCase("A") && (column + length - 1 > LENGTH_OF_BOARD)) ||
			 (direction.equalsIgnoreCase("D") && (row + length - 1 > LENGTH_OF_BOARD)) )
		// Check whether the placement is within the bounds of the board.
		//////////////////////////////////////////////////////////////////////////////////
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	private boolean isInDictionary(String word)
	// Leave it blank temporarily at this stage.
	{
		return true;
	}
	
	public String getBoard()
	{
		StringBuilder currentBoard = new StringBuilder("");
		int i;
		currentBoard.append("     "); // Five spaces before the sequence.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		// A sequence of A - O.
		{
			currentBoard.append(((char) (i + 'A')));
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
			currentBoard.append(((char) (i + 'A')));
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
