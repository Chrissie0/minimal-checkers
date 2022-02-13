public class MoveProcess extends GamePlay {
	
	// do moves and jumps, make king pieces
	// Return true if a capture was made and another capture is possible
	public static boolean movePiece(boolean turn, int fromY, int fromX, int toY, int toX, char[][] board) {
		
		boolean capture = false;
		
		// take a step
		board[toY][toX] = board[fromY][fromX];
		board[fromY][fromX] = EMPTY;		
		
		// do capture
		if (fromY - toY == 2 || fromY - toY == -2) {
			capture = true;
			// Moving down and right
			if (toY - fromY > 0 && toX - fromX > 0) { 
				if (turn && (board[toY-1][toX-1] == WHITE || board[toY-1][toX-1] == WKING)) {
					System.out.println("Black captures at " + (char)(toY-1+ASCII_A) + (char)(toX-1+ASCII_1) + ".");
					board[toY-1][toX-1] = EMPTY;
				}
				else if (!turn && (board[toY-1][toX-1] == BLACK || board[toY-1][toX-1] == BKING)) {
					System.out.println("White captures at " + (char)(toY-1+ASCII_A) + (char)(toX-1+ASCII_1) + ".");
					board[toY-1][toX-1] = EMPTY;
				}
			// Moving down and left
			} else if (toY - fromY > 0 && toX - fromX < 0) {
				if (turn && (board[toY-1][toX+1] == WHITE || board[toY-1][toX+1] == WKING)) {
					System.out.println("Black captures at " + (char)(toY-1+ASCII_A) + (char)(toX+1+ASCII_1) + ".");
					board[toY-1][toX+1] = EMPTY;
				}
				else if (!turn && (board[toY-1][toX+1] == BLACK || board[toY-1][toX+1] == BKING)) {
					System.out.println("White captures at " + (char)(toY-1+ASCII_A) + (char)(toX+1+ASCII_1) + ".");
					board[toY-1][toX+1] = EMPTY;
				}
			// Moving up and left
			} else if (toY - fromY < 0 && toX - fromX < 0) {
				if (turn && (board[toY+1][toX+1] == WHITE || board[toY+1][toX+1] == WKING)) {
					System.out.println("Black captures at " + (char)(toY+1+ASCII_A) + (char)(toX+1+ASCII_1) + ".");
					board[toY+1][toX+1] = EMPTY;
				}
				else if (!turn && (board[toY+1][toX+1] == BLACK || board[toY+1][toX+1] == BKING)) {
					System.out.println("White captures at " + (char)(toY+1+ASCII_A) + (char)(toX+1+ASCII_1) + ".");
					board[toY+1][toX+1] = EMPTY;
				}
			// Moving up and right
			} else if (toY - fromY < 0 && toX - fromX > 0) {
				if (turn && (board[toY+1][toX-1] == WHITE || board[toY+1][toX-1] == WKING)) {
					System.out.println("Black captures at " + (char)(toY+1+ASCII_A) + (char)(toX-1+ASCII_1) + ".");
					board[toY+1][toX-1] = EMPTY;
				}
				else if (!turn && (board[toY+1][toX-1] == BLACK || board[toY+1][toX-1] == BKING)) {
					System.out.println("White captures at " + (char)(toY+1+ASCII_A) + (char)(toX-1+ASCII_1) + ".");
					board[toY+1][toX-1] = EMPTY;
				}
			}
		}
		
		// king moves
		// black king
		if (toY == BOARD_SIZE-1) {
			if (board[toY][toX] == BLACK) {
				board[toY][toX] = BKING;
				System.out.println("He became the black king.");
			}
		}
		// white king
		if (toY == 0) {
			if (board[toY][toX] == WHITE) {
				board[toY][toX] = WKING;
				System.out.println("He became the white king.");
			}
		}
		
		// Check if the piece which captured can capture again
		if(capture) {
			for (int r = 0; r < BOARD_SIZE; r++) {
				for (int c = 0; c < BOARD_SIZE; c++) {
					if(validMove(false, true, turn, toY, toX, r, c, board))
						return true;
				}
			}
		}
		
		return false;
		
	}

	// checks if the move they are trying to make is valid and if there's a capture attempt happening
	public static boolean validMove(boolean feedback, boolean checkCapture, boolean turn, int fromY, int fromX, int toY, int toX, char[][] board) {
		
		/*  feedback: prints feedback message, unless otherwise specified
			checkCapture: checks if they can jump with a piece again AFTER having made a capture 
			turn: corresponds to which color's turn we're on */
		
		boolean capture = false;
		
		// cases for invalid moves:
		// 0. they try to move out of the board -- This is handled by the input function
		
		/* 1. they try to move an empty space or the other player's piece
		 	  the feedback messages are now obsolete for these, because the gamePlay method just prints its own message */
		
		if (board[fromY][fromX] == EMPTY) {
			if(feedback)
				System.out.println("You tried to move an empty space!");
			return false;
		}
		if (turn && (board[fromY][fromX] == WHITE || board[fromY][fromX] == WKING)) {
			if(feedback)
				System.out.println("Black player tried to move White pieces.");
			return false;
		}
		if (!turn && (board[fromY][fromX] == BLACK || board[fromY][fromX] == BKING)) {
			if(feedback)
				System.out.println("White player tried to move Black pieces.");
			return false;
		}
		
		// 2. they try to move onto another piece		
		if (board[toY][toX] != EMPTY) {
			if(feedback)
				System.out.println("You tried to move onto another piece.");
			return false;
		}
		
		// 3. they try to not move
		if (fromY - toY == 0 || fromX - toX == 0) {
			if(feedback)
				System.out.println("You are trying to move horizontally, vertically, or to the same location.");
			return false;
		}
		
		// 3.1 fuckey move
		if (Math.abs(fromY - toY) != Math.abs(fromX - toX)) {
			if(feedback)
				System.out.println("You can't move like that.");
			return false;
		}
			
		// 4. they try to move backwards	
		if (turn && toY < fromY || !turn && toY > fromY) {
				
		// 	4.1 unless it's a king
			if (board[fromY][fromX] != BKING && board[fromY][fromX] != WKING) {
				if(feedback)
					System.out.println("You tried to move a non-king backwards.");
				return false;
			}
		}
		
		// 5. they try to move more than 1 space		
		if (fromY - toY > 2 || fromY - toY < -2) {
			if(feedback)
				System.out.println("You are trying to move too far!");
			return false;
		}
		
		if (fromY - toY == 2 || fromY - toY == -2) {
		
		// 	5.1 unless they jump over an enemy			
			if (toY - fromY > 0 && toX - fromX > 0) { // Moving down and right
				if (turn && (board[toY-1][toX-1] == WHITE || board[toY-1][toX-1] == WKING))
					capture = true;
				else if (!turn && (board[toY-1][toX-1] == BLACK || board[toY-1][toX-1] == BKING))
					capture = true;
				else {
					if(feedback)
						System.out.println("There is no piece for you to jump over to move that far. (1)");
					return false;
				}
			} else if (toY - fromY > 0 && toX - fromX < 0) { // Moving down and left
				if (turn && (board[toY-1][toX+1] == WHITE || board[toY-1][toX+1] == WKING))
					capture = true;
				else if (!turn && (board[toY-1][toX+1] == BLACK || board[toY-1][toX+1] == BKING))
					capture = true;
				else {
					if(feedback)
						System.out.println("There is no piece for you to jump over to move that far. (2)");
					return false;
				}
			} else if (toY - fromY < 0 && toX - fromX < 0) { // Moving up and left
				if (turn && (board[toY+1][toX+1] == WHITE || board[toY+1][toX+1] == WKING))
					capture = true;
				else if (!turn && (board[toY+1][toX+1] == BLACK || board[toY+1][toX+1] == BKING))
					capture = true;
				else {
					if(feedback)
						System.out.println("There is no piece for you to jump over to move that far. (3)");
					return false;
				}
			} else if (toY - fromY < 0 && toX - fromX > 0) { // Moving up and right
				if (turn && (board[toY+1][toX-1] == WHITE || board[toY+1][toX-1] == WKING))
					capture = true;
				else if (!turn && (board[toY+1][toX-1] == BLACK || board[toY+1][toX-1] == BKING))
					capture = true;
				else {
					if(feedback)
						System.out.println("There is no piece for you to jump over to move that far. (4)");
					return false;
				}
			}
		}
		
		// If we are checking for capture, only if a capture was made should we return true.
		if(!checkCapture)
			return true;
		else if (checkCapture && capture)
			return true;
		else
			return false;
	}
}
