public class DrawBoard extends GamePlay {
	
	// put pieces on board
	public static char[][] initboard(int size) {
		
		char [][]board = new char[size][size];
		char []token = {BLACK, WHITE};
		
		// figure out whether to put a B or W or a space
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				if ((r <= 2) || (r >= 5)) {
					if (((r%2==0) && (c%2 != 0)) || ((r%2 != 0) && (c%2 == 0)))
						board[r][c] = token[r/5];
					else
						board[r][c] = EMPTY;
				} else {
					board[r][c] = EMPTY;
				}
			}
		}
		
		return board;
	}
	
	// draw the board
	public static void display(final char [][]board) {
		
		char []letter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
		
		// draw numbers on top of the table
		System.out.printf("\n%4s", " ");
		for (int i = 0; i < board.length; i++)
			System.out.printf(" %2d  ", (i + 1));
		System.out.printf("\n%44s\n", "========================================");
		
		// draw letters on left side
		for (int i = 0; i < board.length; i++) {
			System.out.printf("%2c  ", letter[i]);
			
			// put tokens or spaces in slots
			for (int j = 0; j < board.length; j++) {
				System.out.printf("|%2c |", board[i][j]);
			}

			System.out.println();
			System.out.printf("%44s\n", "========================================");
		}
	}
}
