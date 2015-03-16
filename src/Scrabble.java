/*
Author: Dylan Dowling & Pranav Kashyap
*/
import java.util.Scanner;

public class Scrabble {
	
	public static int player1Score;
	public static int player2Score;
	public static Pool pool = new Pool();
	public static Board board = new Board();

	public static void main(String[] args) {

		String numOfPlayers;
		
		Scanner input = new Scanner(System.in);
		String postion = "", direction = "", ans = "";
		
		// playerTurnCount used for player ID purpose
		int playerTurnCount=1;
		int player1IncrementVariable=0;
		int player2IncrementVariable=0;
		
		System.out.printf("\n1 player or 2 player game? [1 | 2]): ");
		numOfPlayers = input.next();
		
		if (numOfPlayers.equalsIgnoreCase("1")){
			
			System.out.printf("\nPlayer 1 enter your name: ");
			String player1Name = input.next();
			Player player1 = new Player();
			player1.setName(player1Name);
			Frame player1Frame = player1.getFrame();
			player1Frame.refill(pool);
			
			//int player1Score = 0;
	
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
				System.out.printf("Enter word or letter. Include letters from the board the complete your word: ");
				board.putNewWord(postion.toUpperCase(), direction,input.next(), player1Frame, playerTurnCount);
				
				// Calculating the score
				player1IncrementVariable = player1Score - player1IncrementVariable;
				player1.addScore(player1IncrementVariable);
				player1IncrementVariable = player1Score + player1IncrementVariable;
				
				System.out.println("\n"+ board);
				System.out.println("\n"+ player1.getName() +"'s current score is: "+ player1.getScore());
				
				// Keep playing?
				System.out.printf("\n"+ player1Name + " are you ready to continue?: [y/n]: ");
				ans = input.next();

				if (ans.equalsIgnoreCase("y")) {
					playerTurnCount += 2;
					continue;
				} else if (ans.equalsIgnoreCase("n")) {
					System.out.println("As you selected no, the game will now end. Thanks for playing.");		
					break;
				} else {
					System.out.println("Error unacceptable input, I'm ending the game, stop trying to break me :(");
					break;
				}
			}//end of while
			input.close();
			
		}else if (numOfPlayers.equalsIgnoreCase("2")){
			
			
			
			// Setting up player data
			System.out.printf("\nPlayer 1 enter your name: ");
			String player1Name = input.next();
			Player player1 = new Player();
			player1.setName(player1Name);
			Frame player1Frame = player1.getFrame();
			player1Frame.refill(pool);
			//int player1Score=0;
			
			System.out.printf("\nPlayer 2 enter your name: ");
			String player2Name = input.next();
			Player player2 = new Player();
			player1.setName(player2Name);
			Frame player2Frame = player2.getFrame();
			player2Frame.refill(pool);
			//int player2Score=0;
			
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
					System.out.printf("Enter word or letter. Include letters from the board the complete your word: ");
					board.putNewWord(postion.toUpperCase(), direction,input.next(), player1Frame, playerTurnCount);
					
					// Calculating the score
					player1IncrementVariable = player1Score - player1IncrementVariable;
					player1.addScore(player1IncrementVariable);
					player1IncrementVariable = player1Score + player1IncrementVariable;
					
					System.out.println("\n"+ board);
					System.out.println("\n"+ player1.getName() +"'s current score is: "+ player1.getScore());
					
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
					System.out.printf("Enter word or letter. Include letters from the board the complete your word: ");
					board.putNewWord(postion.toUpperCase(), direction,input.next(), player2Frame, playerTurnCount);
					
					// Calculating the score
					player2IncrementVariable = player2Score - player2IncrementVariable;
					player2.addScore(player2IncrementVariable);
					player2IncrementVariable = player2Score + player2IncrementVariable;
					
					System.out.println("\n"+ board);
					System.out.println("\n"+ player2.getName() +"'s current score is: "+ player2.getScore());
					
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
