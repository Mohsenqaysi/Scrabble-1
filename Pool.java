public class Pool {

	private static final char BLANK = '@';
	private static final int[] VALUE_TILES = {0,1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; 
	private static final int[] TILE_DISTRIBUTION = {2,9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1};
	private static final int TILE_DISTRIBUTION_LENGTH = 27;
	// first character ('@') is the blank tile, second character is 'A'
	
	private StringBuffer pool = new StringBuffer();;
	
	Pool () {
		reset();
		return;
	}

	
	
	public void reset () {
		char letter;
		String tiles;
		
		pool.setLength(0);
		letter = BLANK;
		for (int i=0; i<TILE_DISTRIBUTION_LENGTH; i++) {
			tiles= "";
			for (int j=0; j<TILE_DISTRIBUTION[i]; j++) {
				tiles = tiles + letter;
			}
			pool = pool.append(tiles);
			letter++;
		}
		
		return;
	}
	
	
	
	public int size () {
		return(pool.length());
	}
	
	
	
		public boolean isEmpty () {	
		return(pool.length()==0);
	}
	
		
		
	public int getValue (char letter) {
		// precondition: letter must be upper-case letter or @
		return(VALUE_TILES[letter-BLANK]);
	}		
	
	
	
	public String drawTiles (int numRequested) {
		int index, numGiven;
		String draw;
		
		if (numRequested > pool.length())
			numGiven = pool.length();
		else {
			numGiven = numRequested;
		}
		draw = "";
		for (int i=0; i<numGiven; i++) {
			index = (int) (Math.random()*pool.length());
			draw = draw + pool.charAt(index);
			pool = pool.deleteCharAt(index);
		}
		
		return(draw);
	}
}
