public class Board
{
	private static final int LENGTH_OF_BOARD = 15;
	private Cell[][] board;

	private class Cell
	{
		public char letter;
		public boolean wordOrLetter;
		/*
		 * Double-word or triple-word: true; double-letter or triple-letter: false.
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
		}
		
		/*
		 * The whole board is divided into 4 sub-board.
		 * The code below is to assign values of double/triple word/letter to specific cells.
		 */
		for ( int i = 0; i <= LENGTH_OF_BOARD / 2; i++ )
		{
			for ( int j = 0; j <= LENGTH_OF_BOARD / 2; j++ )
			{
				if ( (i == j) && (((i >= 1) && (i <= 4)) || (i == 7)) )
				// 2x WS.
				{
					set(i, j, true, 2);
				}
				else
				{
					if ( ((i == 0) && (j == 0)) ||
						 ((i == 0) && (j == 7)) || ((i == 7) && (j == 0)) )
					// 3x WS.
					{
						set(i, j, true, 3);
					}
					else
					{
						if ( ((i == 0) && (j == 3)) || ((i == 3) && (j == 0)) ||
							 ((i == 2) && (j == 6)) || ((i == 6) && (j == 2)) ||
							 ((i == 3) && (j == 7)) || ((i == 7) && (j == 3)) ||
							 ((i == 6) && (j == 6)) )
						// 2x LS.
						{
							set(i, j, false, 2);
						}
						else
						{
							if ( ((i == 5) && (j == 1)) || ((i == 1) && (j == 5)) ||
								 ((i == 5) && (j == 5)) )
							// 3x LS.
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
		setOneCell(i, j, wordOrLetter, times);
		setOneCell(LENGTH_OF_BOARD - 1 - i, j, wordOrLetter, times);
		setOneCell(i, LENGTH_OF_BOARD - 1 - j, wordOrLetter, times);
		setOneCell(LENGTH_OF_BOARD - 1 - i, LENGTH_OF_BOARD - 1 - j, wordOrLetter, times);
	}
	
	private void setOneCell(int i, int j, boolean wordOrLetter, int times)
	{
		board[i][j].letter = ' '; // Set to blank at the first time.
		board[i][j].wordOrLetter = wordOrLetter;
		board[i][j].times = times;
	}

	public void putNewWord(String startingPosition, String direction, String word, Frame frame)
	/*
	 * startingPosition is a string with such format: <Letter (A/a - O/o)><Index (1 - 15)>, e.g: "A4", "F16", "O2";
	 * direction contains two possible values: A/a - across and D/d - down;
	 * word is a word with length 1 - 7.
	 * Here we assume that user's input is free from any semantic errors all the time.
	 * Precondition: isPlacementLegal(word) is true.
	 */
	{
		word = word.toUpperCase(); // Convert word to upper case.
		int columnIndex = startingPosition.toUpperCase().charAt(0) - 'A';
		// Convert an upper case letter to index.
		int  rowIndex= startingPosition.charAt(1) - '0';
		
		if ( isPlacementLegal(rowIndex, columnIndex, direction, word, frame) )
		{
			// PUT HERE!
			if ( direction.equalsIgnoreCase("A") )
			{
				for ( int i = columnIndex; i < columnIndex + word.length(); i++ )
				{
					board[rowIndex][i].letter = word.charAt(i - columnIndex);
				}
			}
			else
			{
				for ( int i = rowIndex; i < rowIndex + word.length(); i++ )
				{
					board[i][columnIndex].letter = word.charAt(i - rowIndex);
				}
			}
			frame.remove(word);
		}
		else
		{
			System.out.println("Cannot put the word on the board.");
		}
	}
	
	private boolean isPlacementLegal(int row, int column, String direction, String word, Frame frame)
	{
		final int CENTRE_INDEX = LENGTH_OF_BOARD / 2;
		boolean flag = true; // Assume that it is legal.
		String wordBackUp = word; // Create a copy of word, used in isAvailable() method.
		
		if ( board[CENTRE_INDEX][CENTRE_INDEX].letter == ' ' )
		// If the word is the first one, then it should be put at the centre of the board.
		{
			if ( ((row == CENTRE_INDEX) && (column <= CENTRE_INDEX) && (column + word.length() >= CENTRE_INDEX)) ||
				 ((column == CENTRE_INDEX) && (row <= CENTRE_INDEX) && (row + word.length() >= CENTRE_INDEX)) )
			{
				;
			}
			else
			{
				return false;
			}
		}
		
		if ( direction.equalsIgnoreCase("A") ) // Put the word horizontally.
		{
			int endingColumnIndex = column + word.length();
			if ( endingColumnIndex >= LENGTH_OF_BOARD )
			// Check whether the placement is within the bounds of the board.
			{
				System.out.println("A");
				flag = false;
			}
			else
			{
				for ( int i = column; i <= endingColumnIndex; i++ )
				{
					if ( board[row][i].letter != ' ' )
					// If it is already occupied.
					{
						if ( board[row][i].letter != word.charAt(i - column) )
						// Wrong overlay.
						{
							System.out.println("B");
							flag = false;
							break;
						}
						else
						{
							wordBackUp = wordBackUp.substring(0, i - column) + wordBackUp.substring(i - column + 1);
							System.out.println(wordBackUp);
							// Remove the overlaid letter from wordBackUp.
						}
					}
					/*
					 *  If the to-be-placed word does not use any letter already put on the board,
					 *  do something.
					 */
				}
			}
		}
		else
		{
			if ( direction.equalsIgnoreCase("D") ) // Put the word vertically.
			{
				int endingRowIndex = row + word.length();
				if ( endingRowIndex >= LENGTH_OF_BOARD )
				// Check whether the placement is within the bounds of the board.
				{
					System.out.println("C");
					flag = false;
				}
				else
				{
					for ( int i = row; i <= row + word.length(); i++ )
					{
						if ( board[i][column].letter != ' ' )
						// If it is already occupied.
						{
							if ( board[i][column].letter !=  word.charAt(i - row) )
							{
								flag = false;
								System.out.println("D");
								break;
							}
							else
							{
								wordBackUp = wordBackUp.substring(0, i - row) + wordBackUp.substring(i - row + 1);
								// Remove the overlaid letter from wordBackUp.
							}
						}
					}
				}
			}
		}
		if ( !frame.isAvailable(wordBackUp) )
		{
			flag = false;
			System.out.println("E");
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
		return flag;
	}

	private boolean isInDictionary(String word)
	// Leave it blank temporarily at this stage.
	{
		return true;
	}
	
	public String getBoard()
	{
		String currentBoard = new String();
		int i;
		currentBoard += "     "; // Five spaces before the sequence.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		// A sequence of A - O.
		{
			currentBoard += ((char) (i + 'A'));
			currentBoard += ' ';
		}
		currentBoard += "\n   *********************************\n"; // Top border.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		{
			currentBoard += String.format("%02d", i + 1);
			// Make numbers beside the left border align right.
			currentBoard += " * "; // Left border.
			for ( int j = 0; j < LENGTH_OF_BOARD; j++ )
			{
				currentBoard += board[i][j].letter;
				currentBoard += ' ';
			}
			currentBoard += ("* " + String.format("%02d", i + 1) + '\n'); // Right border.
			// Make numbers beside the right border align left.
		}
		currentBoard += "   *********************************\n"; // Bottom border.
		currentBoard += "     "; // Five spaces before the sequence.
		for ( i = 0; i < LENGTH_OF_BOARD; i++ )
		// A sequence of A - O.
		{
			currentBoard += ((char) (i + 'A'));
			currentBoard += ' ';
		}
		return currentBoard;
	}

	public String toString()
	// Output the current tile positions.
	{
		return getBoard();
	}
}