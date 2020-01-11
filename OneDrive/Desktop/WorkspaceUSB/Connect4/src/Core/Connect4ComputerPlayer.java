package Core;
/**
 * Class Connect4ComputerPlayer create a logic to play 
 * against computer and generate random moves.
 * 
 * @author: 	Albina Shelton
 * Date:		10/08/2019 	
 */
import java.util.Random;

public class Connect4ComputerPlayer {

	/**
	 * Generate the random move for the computer step 
	 * in the Commect4 game.
	 * 
	 * @return step
	 */
	public int computerPlay() {
		Random rand = new Random();
		int step = rand.nextInt((7-1)+1)+1;
		return step;
	}

}
