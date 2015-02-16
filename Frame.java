public class Frame {

	private static final int MAX_TILES = 7;
	private StringBuffer tiles = new StringBuffer("");
	
	Frame() {
		reset();
		return;
	}
	
	Frame (Frame originalFrame) {
		this.tiles = originalFrame.tiles;
		return;
	}
	
	public void reset () {
		tiles.setLength(0);
		return;
	}
	
	public int length () {
		return(tiles.length());
	}
	
	public boolean isEmpty () {
		return(tiles.length()==0);
	}
	
	public boolean isFull () {
		return(tiles.length() == MAX_TILES);
	}
	
	public boolean isAvailable (String letters) {
		boolean found;
		int index;
		StringBuffer copyTiles = new StringBuffer(tiles);
		
		if (letters.length() > tiles.length()) {
			found = false;
		}
		else {
			found = true;
			for (int i=0; (i<letters.length()) && found; i++) {
				index = copyTiles.indexOf(Character.toString(letters.charAt(i)));
				if (index == -1) {
					found = false;
				}
				else {
					copyTiles.deleteCharAt(index);
				}
			}
		}
		return(found);
	}
	
	public String getTiles () {
		return(tiles.toString());
	}
	
	public void remove (String letters) {
		// precondition: isAvailable(letters) is true
		int index;		
		for (int i=0; (i<letters.length()); i++) {
			index = tiles.indexOf(Character.toString(letters.charAt(i)));
			tiles.deleteCharAt(index);
		}
		return;
	}

	public void refill (Pool pool) {
		int numTilesToDraw;
		String newTiles;
		numTilesToDraw = MAX_TILES - tiles.length();
		newTiles = pool.drawTiles(numTilesToDraw);
		tiles = tiles.append(newTiles);
		return;
	}
	
	public String toString () {
		return(tiles.toString());
	}
	
}
