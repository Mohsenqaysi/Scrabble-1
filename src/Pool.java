public class Pool
{
	private static final char BLANK = '@';
	private static final int[] VALUE_TILES = {0,1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; 
	private static final int[] TILE_DISTRIBUTION = {2,9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
	private static final int TILE_DISTRIBUTION_LENGTH = 27;
	// First character ('@') is the blank tile, second character is 'A'.
	
	private StringBuilder pool = new StringBuilder();
	
	Pool()
	{
		reset();
	}

	public void reset()
	{
		char letter;
		String tiles;
		
		pool.setLength(0);
		letter = BLANK;
		for ( int i = 0; i < TILE_DISTRIBUTION_LENGTH; i++ )
		{
			tiles= ""; // tiles here seems to be a input buffer.
			for ( int j = 0; j < TILE_DISTRIBUTION[i]; j++ )
			{
				tiles = tiles + letter;
			}
			pool = pool.append(tiles); // Flush to pool.
			letter++;
		}
	}

	public int size()
	{
		return(pool.length());
	}
	
	public boolean isEmpty()
	{	
		return(pool.length() == 0);
	}

	public int getValue(char letter)
	{
		// Precondition: letter must be upper-case letter or @.
		return(VALUE_TILES[letter - BLANK]);
	}		

	public String drawTiles(int numRequested)
	{
		int index, numGiven;
		String draw;
		
		if ( numRequested > pool.length() )
		{
			numGiven = pool.length();
		}
		else
		{
			numGiven = numRequested;
		}
		draw = "";
		for ( int i = 0; i < numGiven; i++ )
		{
			index = (int) (Math.random() * pool.length());
			/*
			 * random() returns a double value with a positive sign,
			 * greater than or equal to 0.0 and less than 1.0.
			 */
			draw = draw + pool.charAt(index);
			pool = pool.deleteCharAt(index);
		}
		return(draw);
	}
}
