package UI;

import Core.Connect4;
import Core.Connect4ComputerPlayer;
import java.util.Scanner;
/**
 * The driver class for Connect4 game.Contains methods that make the choice
 * for a player for game: play against computer or against another player. 
 * Generate players turns and showing the result of games. 
 * 
 * @author: 	Albina Shelton
 * Date:		11/02/2019 	
 */
public class Connect4TextConsole {
	
	static Connect4 game = new Connect4();
	static Connect4ComputerPlayer comp = new Connect4ComputerPlayer();
	
	/**Generate the scanner for the game		*/
	static Scanner scan = new Scanner(System.in);
	
	public Connect4TextConsole(Connect4 connect4) {
		this.game = connect4;
	}

	/**
	 * The main method for Connect4 game.
	 * @param args
	 */
	public static void start() {
		
		String userInput = "";
		/** Counter for games		*/
		int Round = 1;
		int chose = 0;

		do {
			/** New game object	*/
			

			/** Define the game type		*/
			System.out.println("****Round " + Round + "****\n");
			System.out.println("Enter 'C' if you want to play against computer.");
			System.out.println("Enter 'P' if you want to play against another player.");
			userInput = scan.next();

			boolean turn = true;
			do {

				/** Game against another player		*/
				if (userInput.equals("P")) {
					chose = 1;
					System.out.println("The game against two players:");
					do {
						turn = !turn;
						game.printBoard();
						char player;
						/**player's turn 		*/
						if (turn) {
							player = 'O';
						} else {
							player = 'X';
						}
						System.out.print("Player " + player + "- your turn. Choose a column (1-7): ");

						boolean status = false;
						while (!status) {
							try {
								status = game.drop(player, scan.nextInt());
								if (!status) {
									System.out.println("Not right move. Try again!");
									System.out.print("Try Again: ");
								}
							} catch (Exception e) {
								System.out.println("Invalid input.");
								System.out.print("Try Again: ");
								scan.nextLine();
							}
						}
						System.out.println();

					} while (!game.isFull() && !game.isWinner());
					game.printBoard();
					
					/** Defining the winner		*/
					if (game.isWinner()) {
						System.out.printf("The %s player has won.", (turn ? "O" : "X"));
					}
				}
				
				/** game against computer		*/
				else if (userInput.equals("C")) {
					chose = 1;
					System.out.println("Game against computer.");
					do {

						turn = !turn;
						game.printBoard();
						char player;
						/**computer's move		*/
						if (turn) {
							player = 'O';
							boolean status = false;
							while (!status) {
								try {
									int num = comp.computerPlay();
									System.out.println("Computers is playing with 'O' in column: " + num);
									status = game.drop(player, num);
									if (!status) {
										System.out.println("Computer will try again!");
										System.out.print("Computer Again: ");
									}
								} catch (Exception e) {
									System.out.println("Invalid input for computer.");
									System.out.print("Computer try Again: ");
									comp.computerPlay();
								}
							}
							/**player's move		*/
							System.out.println();
						} else {
							player = 'X';
							System.out.println("Player it is your turn.Choose a column (1-7): ");

							boolean status = false;
							while (!status) {
								try {
									status = game.drop(player, scan.nextInt());
									if (!status) {
										System.out.println("Not right move. Try again!");
										System.out.print("Try Again: ");
									}
								} catch (Exception e) {
									System.out.println("Invalid input.");
									System.out.print("Try Again: ");
									scan.nextLine();
								}
							}
						}
						System.out.println();
					} while (!game.isFull() && !game.isWinner());
					game.printBoard();
					
					/** Defining the winner		*/
					if (game.isWinner()) {
						System.out.printf("%s won!", (turn ? "The Computer has" : "You have"));
					}
				}

				/** invalid input for choosing the game		*/
				else {
					System.out.println("Invalid input.");
					System.out.println("'C' - against computer. 'P'-against another player");
					userInput = scan.next();
				}

			} while (chose == 0);
	
			System.out.println("\n***********************************");
			System.out.println("Do you want to play a new game (Y / N)? " + scan.nextLine());
			userInput = scan.nextLine();

			Round++;/** Counter for Rounds		*/
		} while (userInput.equals("Y"));

		System.out.println("Thank you, goodbye!");
		scan.close();

	}
}

