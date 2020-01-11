package Core;
/**
* The data structure for a game of Connect4.
* 
* Connect4 Game is made of a certain number of Connect4 Columns. Each column represents
* a column from the current state of the game.
* 
* @author: Albina Shelton
* Date: 11/02/2019
*/

public class Connect4 {
/**
* char value for the grid
*/
	char[][] grid;
	int [][] gridGUI;
	
	/**
	 * Create the constructor
	 * returns grids 
	 */
	public Connect4() {
		/**Grid for GUI	`	*/
		gridGUI= new int [6][7];
		for (int i = 0; i < gridGUI.length; i++) {
			for (int y = 0; y < gridGUI[i].length; y++) {
				gridGUI[i][y] = 0;
			}
		}

		
		/**Grid for the board		*/
		grid = new char[6][7];
		for (int ii = 0; ii < grid.length; ii++) {
			for (int yy = 0; yy < grid[ii].length; yy++) {
				grid[ii][yy] = ' ';
			}
		}
	}

	
	/**
	 *boolean isFull method checks if the grid is full
	 * @return
	 */
	public boolean isFull() {
		for (int ii = 0; ii < grid[0].length; ii++) {
			if (grid[0][ii] == ' ')  return false;
		}
		return true;
	}
	
	/**
	 * printBoard method is printing the grid's walls
	 * 
	 */
	public void printBoard() {
		System.out.println("***************");
		
		/**Create walls for the grid		*/
		for (int i = 0; i < grid.length; i++) {
			System.out.printf("|");
			for (int j = 0; j < grid[i].length; j++) {
				System.out.printf("%c|", grid[i][j]);
			}
			System.out.println();
		}
		System.out.println(" 1 2 3 4 5 6 7 ");
		System.out.println("***************");
	}


	/**
	 * This method is declare the winner of the game
	 * by checking each column, row, and two diagonals
	 * @return
	 */
	public boolean isWinner() {
	
		boolean row = false;
		boolean col = false;
		boolean diagonal_1 = false;
		boolean diagonal_2 = false;

		/**Check the row for winner		*/
		for (int ii = 0; ii <= 5; ii++) {
			for (int yy = 0; yy <= 3; yy++) {
				if (grid[ii][yy] == grid[ii][yy + 1] && grid[ii][yy] == grid[ii][yy + 2] 
						&& grid[ii][yy] == grid[ii][yy + 3] && grid[ii][yy] != ' ') {
					row = true;
					break;
				}
			}
		}

		/**Check the column for winner		*/
		for (int ii = 0; ii <= 2; ii++) {
			for (int yy = 0; yy <= 6; yy++) {
				if (grid[ii][yy] == grid[ii + 1][yy] && grid[ii][yy] == grid[ii + 2][yy] 
						&& grid[ii][yy] == grid[ii + 3][yy] && grid[ii][yy] != ' ') {
					col = true;
					break;
				}
			}
		}

		/**Check diagonals for winner		*/
		for (int ii = 0; ii <= 2; ii++) {
			for (int yy = 0; yy <= 3; yy++) {
				if (grid[ii][yy] == grid[ii + 1][yy + 1] && grid[ii][yy] == grid[ii + 2][yy + 2] 
						&& grid[ii][yy] == grid[ii + 3][yy + 3] && grid[ii][yy] != ' ') {
					diagonal_1 = true;
					break;
				}
			}
		}

		/** Check to see if four diagonals in the other direction match 	*/
		for (int ii = 0; ii <= 2; ii++) {
			for (int yy = 3; yy <= 6; yy++) {
				if (grid[ii][yy] == grid[ii + 1][yy - 1] && grid[ii][yy] == grid[ii + 2][yy - 2] 
						&& grid[ii][yy] == grid[ii + 3][yy - 3] && grid[ii][yy] != ' ') {
					diagonal_2 = true;
					break;
				}
			}
		}

		/**True if winner found			*/
		if (row || col || diagonal_1 || diagonal_2) return true;
		else return false;
	} 
	
	/**
	 * 
	 * boolean drop is reading if output in the boundaries
	 * @param player
	 * @param column
	 * @return
	 */
	public boolean drop(char player, int column) {
		column--; 
		/**Checking for boundaries		*/
		if (column < 0 || column > 6) {
			throw new ArrayIndexOutOfBoundsException("You are out of grib. Try a number between 1 and 7.");
		}
		boolean add = false;
		for (int ii = (grid.length - 1); ii >= 0; ii--) {
			if (grid[ii][column] == ' ') {
				grid[ii][column] = player;
				add = true;
				break;
			}
		}  return add;
	}

}