/*
Author: Dylan Dowling
*/
import java.util.Scanner;

public class Scrabble {

	public static void main(String[] args) {

		String numOfPlayers;
		Pool pool = new Pool();
		Board board = new Board();
		Scanner input = new Scanner(System.in);
		String postion = "", direction = "", ans = "";
		
		System.out.printf("\n1 player or 2 player game? [1 | 2]): ");
		numOfPlayers = input.next();
		
		if (numOfPlayers.equalsIgnoreCase("1")){
			
			System.out.printf("\nPlayer 1 enter your name: ");
			String player1Name = input.next();
			Player player1 = new Player();
			player1.setName(player1Name);
			Frame player1Frame = player1.getFrame();
			player1Frame.refill(pool);
	
			System.out.println("\n"+ board);
			System.out.println("\nH8 = Center of the board.\n");

			while (!pool.isEmpty()) {
				
				player1Frame.refill(pool);
				System.out.println(player1Name +"'s Frame: " + player1Frame);
				System.out.println("\nPool of size: " + pool.size());
				
				// Position
				System.out.printf("Give start position and press enter (Ex: H8): ");
				postion = input.next();
				
				// Direction
				System.out.printf("Give direction, then press enter  (A = Horizontally   D = Vertically): ");
				direction = input.next();
				
				// Enter new word
				System.out.printf("Enter word or letter. 1st letter must be on board if not center position: ");
				board.putNewWord(postion.toUpperCase(), direction,input.next(), player1Frame);
				System.out.println("\n"+ board);
				
				// Keep playing?
				System.out.printf("\n"+ player1Name + " are you ready to continue?: [y/n]: ");
				ans = input.next();

				if (ans.equalsIgnoreCase("y")) {
					continue;
				} else if (ans.equalsIgnoreCase("n")) {
					System.out.println("As you selected no, the game will now end. Thanks for playing.");		
					break;
				} else {
					System.out.println("Error unacceptable input, I'm ending the gam, stop trying to break me :(");
					break;
				}
			}//end of while
			input.close();
			
		}else if (numOfPlayers.equalsIgnoreCase("2")){
			
			int playerTurnCount=1;
			
			// Setting up player data
			System.out.printf("\nPlayer 1 enter your name: ");
			String player1Name = input.next();
			Player player1 = new Player();
			player1.setName(player1Name);
			Frame player1Frame = player1.getFrame();
			player1Frame.refill(pool);
			
			System.out.printf("\nPlayer 2 enter your name: ");
			String player2Name = input.next();
			Player player2 = new Player();
			player1.setName(player2Name);
			Frame player2Frame = player2.getFrame();
			player2Frame.refill(pool);
			
			System.out.println("\n"+ board);
			System.out.println("\nH8 = Center of the board.\n");
			
			while (!pool.isEmpty()) {	
			
				if(playerTurnCount%2 != 0){
					System.out.printf(player1Name + "'s turn.\n\n");
					player1Frame.refill(pool);
					System.out.println(player1Name +"'s Frame: " + player1Frame);
					System.out.println("\nPool size: " + pool.size());
					
					// Position
					System.out.printf("Give start position and press enter (Ex: H8): ");
					postion = input.next();
					
					// Direction
					System.out.printf("Give direction, then press enter  (A = Horizontally   D = Vertically): ");
					direction = input.next();
					
					// Enter new word
					System.out.printf("Enter word or letter. 1st letter must be on board if not center position: ");
					board.putNewWord(postion.toUpperCase(), direction,input.next(), player1Frame);
					System.out.println("\n"+ board);
					
					System.out.printf("\n"+ player2Name + " are you ready to continue?: [y/n]: ");
					ans = input.next();
					
				}else{
					System.out.printf(player2Name + "'s turn.\n\n");
					player2Frame.refill(pool);
					System.out.println(player2Name +"'s Frame: " + player2Frame);
					System.out.println("\nPool size: " + pool.size());
					
					// Position
					System.out.printf("Give start position and press enter (Ex: H8): ");
					postion = input.next();
					
					// Direction
					System.out.printf("Give direction, then press enter  (A = Horizontally   D = Vertically): ");
					direction = input.next();
					
					// Enter new word
					System.out.printf("Enter word or letter. 1st letter must be on board if not center position: ");
					board.putNewWord(postion.toUpperCase(), direction,input.next(), player2Frame);
					System.out.println("\n"+ board);
					
					// Keep playing?
					System.out.printf("\n"+ player1Name + " are you ready to continue?: [y/n]: ");
					ans = input.next();
				}
			
				
				if (ans.equalsIgnoreCase("y")) {
					playerTurnCount++;
					continue;
				} else if (ans.equalsIgnoreCase("n")) {
					System.out.println("As you selected no, the game will now end. Thanks for playing.");		
					break;
				} else {
					System.out.println("Error unacceptable input, I'm ending the gam, stop trying to break me :(");
					break;
				}
			}//end of while
			input.close();	
		}	
	}
}