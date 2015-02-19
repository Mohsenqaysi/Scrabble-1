public class Board
{
	private static final int lengthOfBoard = 15;
	private Cell[][] board;

	private class Cell
	// The class cannot be extended.
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
	}

	public void reset()
	{
		board = new Cell[lengthOfBoard][lengthOfBoard];
		for ( int i = 0; i < lengthOfBoard; i++ )
		{
			for ( int j = 0; j < lengthOfBoard; j++ )
			{
				board[i][j] = new Cell();
			}
		}
		
		for ( int i = 0; i <= lengthOfBoard / 2; i++ )
		{
			for ( int j = 0; j <= lengthOfBoard / 2; j++ )
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
								set(i, j, true, 1); /////////////////////////
							}
						}
					}
				}
			}
		}
	}
	
	private void setOneCell(int i, int j, boolean wordOrLetter, int times)
	{
		board[i][j].letter = ' ';
		board[i][j].wordOrLetter = wordOrLetter;
		board[i][j].times = times;
	}
	
	private void set(int i, int j, boolean wordOrLetter, int times)
	// According to the position of one cell, set the same value for all four symmetric positions on the board.
	{
		setOneCell(i, j, wordOrLetter, times);
		setOneCell(lengthOfBoard - 1 - i, j, wordOrLetter, times);
		setOneCell(i, lengthOfBoard - 1 - j, wordOrLetter, times);
		setOneCell(lengthOfBoard - 1 - i, lengthOfBoard - 1 - j, wordOrLetter, times);
	}
	
	public String getBoard()
	{
		String currentBoard = new String();
		int i;
		currentBoard += "     "; // Five spaces.
		for ( i = 0; i < lengthOfBoard; i++ )
		{
			currentBoard += ((char) (i + 'A'));
			currentBoard += ' ';
		}
		currentBoard += "\n   *********************************\n";
		for ( i = 0; i < lengthOfBoard; i++ )
		{
			currentBoard += String.format("%2d", i + 1);
			currentBoard += " * ";
			for ( int j = 0; j < lengthOfBoard; j++ )
			{
				currentBoard += board[i][j].letter; //////////////////////////////
				currentBoard += ' ';
			}
			currentBoard += ("* " + (Integer.toString(i + 1)) + '\n');
		}
		currentBoard += "   *********************************\n";
		currentBoard += "     ";
		for ( i = 0; i < lengthOfBoard; i++ )
		{
			currentBoard += ((char) (i + 'A'));
			currentBoard += ' ';
		}
		return currentBoard;
	}
	
	private boolean isInDictionary(String word)
	// Leave it blank temporarily at this stage.
	{
		return true;
	}
	
	private boolean isPlacementLegal(String startingPosition, String direction, int lengthOfWord)
	{
		return true;
	}
	
	public void putNewWord(String startingPosition, String direction, String word)
	/*
	 * startingPosition is a string with such format: <Letter (A/a - O/o)><Index (1 - 15)>, e.g: "A4", "F16", "O2";
	 * direction contains two possible values: A/a - across and D/d - down;
	 * word is a word with length 1 - 7.
	 * Here we assume that user's input is free from any semantic errors all the time.
	 */
	{
		if ( isInDictionary(word) && isPlacementLegal(startingPosition, direction, word.length()) )
		{
			
		}
		else
		{
			
		}
	}
	
	public String toString()
	// Stores the current tile positions.
	{
		return getBoard();
	}
}