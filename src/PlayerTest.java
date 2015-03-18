public class PlayerTest {

	public static void main (String[] args) {
		Pool pool = new Pool();
		Player player1 = new Player(), player2 = new Player();
		Frame frame;
		String drawnLetters;
		boolean empty, available;
		int numLetters, value;
		
		// Test draw random tiles
		System.out.println(pool.size());
		numLetters = 1;
		drawnLetters = pool.drawTiles(numLetters);
		System.out.printf("Draw %s\n",drawnLetters);
		System.out.println(pool.size());

		numLetters = 7;
		drawnLetters = pool.drawTiles(numLetters);
		System.out.printf("Draw %s\n",drawnLetters);
		System.out.println(pool.size());

		// Test tile values
		numLetters = 7;
		drawnLetters = pool.drawTiles(numLetters);
		System.out.printf("Draw %s\n",drawnLetters);
		for (int i=0; i<numLetters; i++) {
			value = pool.getValue(drawnLetters.charAt(i));
			System.out.printf("Value of %s is %d\n",drawnLetters.charAt(i),value);
		}
		System.out.println(pool.size());
		
		// Test pool empty
		pool.reset();
		empty = pool.isEmpty();
		System.out.printf("Pool empty? %b\n",empty);	// expect:false	 
		numLetters = 1;
		for (int i=0; i<99; i++) {
			drawnLetters = pool.drawTiles(numLetters);
			System.out.print(drawnLetters);
		}
		System.out.println();
		System.out.println(pool.size());
		empty = pool.isEmpty();
		System.out.printf("Pool empty? %b\n",empty);	// expect:false
		drawnLetters = pool.drawTiles(numLetters);
		System.out.println(drawnLetters);               // expect: EMPTY
		System.out.println(pool.size());                // expect:0
		empty = pool.isEmpty();
		System.out.printf("Pool empty? %b\n",empty);	// expect:true
		drawnLetters = pool.drawTiles(7);
		System.out.printf("Draw %s\n",drawnLetters);    // expect: EMPTY
				
		// Test player names
		player1.setName("Chris");
		player2.setName("Jack");
		System.out.println(player1.getName());     // expect:Chris
		System.out.println(player2.getName());     // expect:Jack
		
		// Test scoring
		System.out.println(player1.getScore());    // expect:0
		player1.addScore(10);
		System.out.println(player1.getScore());    // expect:10
		
		// Test frames
		pool.reset();
		frame = player1.getFrame();
		empty = frame.isEmpty();
		System.out.printf("Is empty? %b \n",empty);  // expect:true
		System.out.println(frame);                   // expect:empty
		available = frame.isAvailable("A");
		System.out.println(available);               // expect: false
		frame.refill(pool);          
		System.out.println(frame);                   // expect: 7 letters
		empty = frame.isEmpty();                          
		System.out.printf("Is empty? %b\n",empty);   // expect: false
		System.out.println(pool.size());			 // expect: 93
		drawnLetters = frame.getTiles();
		available = frame.isAvailable(drawnLetters.substring(0,6));
		System.out.println(available);               // expect: true
		available = frame.isAvailable("x");
		System.out.println(available);               // expect: false
		frame.remove(drawnLetters.substring(0,3));
		System.out.println(frame);                   // expect: 4 left
		frame = player2.getFrame();
		frame.refill(pool);
		System.out.println(pool.size());			 // expect: 89
		System.out.println(frame);                   // expect: 7 left              
		frame = player1.getFrame();
		System.out.println(frame);                   // expect: 4 left
	}
}