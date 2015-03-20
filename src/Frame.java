public class Frame
{
	private static final int MAX_TILES = 7;
	private StringBuilder tiles = new StringBuilder("");
	
	Frame()
	{
		reset();
	}
	
	public void reset()
	{
		tiles.setLength(0);
	}

	public boolean isEmpty()
	{
		return (tiles.length() == 0);
	}
	
	public boolean isAvailable(String letters)
	{
		// Precondition: letters must be upper-case letter or @.
		boolean found = true;
		int index;
		StringBuffer copyTiles = new StringBuffer(tiles);

		if ( letters.length() > tiles.length() )
		{
			found = false;
		}
		else
		{
			for ( int i = 0; i < letters.length() && found; i++ )
			{
				index = copyTiles.indexOf(Character.toString(letters.charAt(i)));
				if ( index == -1 )
				{
					found = false;
				}
				else
				{
					copyTiles.deleteCharAt(index);
				}
			}
		}
		return found;
	}
	
	public String remove(String letters)
	{
		// Precondition: isAvailable(letters) is true and letters must be upper-case letter or @.
		int index;
		letters = letters.toUpperCase();
		// Make all letters upper case.
		for ( int i = 0; i < letters.length(); i++ )
		{
			index = tiles.indexOf(Character.toString(letters.charAt(i)));
			tiles.deleteCharAt(index);
		}
		return letters;
	}
	
	public void exchange(Pool pool, String letters)
	{
		pool.putBackToPool(remove(letters));
		refill(pool);
	}
	
	public void refill(Pool pool)
	{
		int numTilesToDraw;
		String newTiles;
		numTilesToDraw = MAX_TILES - tiles.length();
		newTiles = pool.drawTiles(numTilesToDraw);
		tiles.append(newTiles);
	}
	
	public String toString()
	{
		return tiles.toString();
	}
}