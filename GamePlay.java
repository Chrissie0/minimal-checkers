import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class GamePlay {
	
	final static int BOARD_SIZE = 8;
	final static char BLACK = 'b';
	final static char WHITE = 'w';
	final static char BKING = 'B';
	final static char WKING = 'W';
	final static char EMPTY = ' ';
	final static int ASCII_A = 65;
	final static int ASCII_1 = 49;
	
	public static void gamePlay(Scanner input) {
		
		int[] from = new int[2];
		int[] to = new int[2];
		char [][]board = DrawBoard.initboard(BOARD_SIZE);
		boolean turn = true;
		boolean repeat = false;
		boolean validPiece = false;
		boolean concede = false;
		String player1;
		String player2;
		SimpleDateFormat date = new SimpleDateFormat("MMMM dd, yyyy");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		
		Date startDate = new Date();
		Date startTime = new Date();
		
		// get player names
		System.out.print("Enter player 1 (black): ");
		player1 = input.nextLine();
		System.out.print("Enter player 2 (white): ");
		player2 = input.nextLine();
		
		System.out.println("The game between " +  player1 + " and " + player2 + " started on " + date.format(startDate) + " at " + time.format(startTime));
		
		DrawBoard.display(board);
		
		// play game
		do {
			
			if (turn)
				System.out.println(player1 + "'s (black) turn!");
			else
				System.out.println(player2 + "'s (white) turn!");
		
			// skips "from" if they can make a new jump
			if (!repeat) {
				System.out.print("From: ");
				getMove(input, from);
			}
			
			// concede condition for "From: "
			if (from[0] == from[1] && from[0] == -1) {

				to[0] = from[0];
				to[1] = from[1];
				
				concede = true;
				
			} else {

				// making sure they pick a piece they can move
				for (int r = 0; r < BOARD_SIZE; r++) {
					for (int c = 0; c < BOARD_SIZE; c++) {
						if (MoveProcess.validMove(false, false, turn, from[0], from[1], r, c, board)) {
							validPiece = true;
						}
					}
				}
			}

			if (validPiece && !concede) {
				System.out.print("To: ");
				getMove(input, to);
			} else
				System.out.println("You can't move from that square.");
			
			// concede condition for "To: "
			if (to[0] == to[1] && to[0] == -1) {
				
				concede = true;
				
			} else if (validPiece) {
				// change turn
				if (MoveProcess.validMove(true, false, turn, from[0], from[1], to[0], to[1], board)) {
					if (!MoveProcess.movePiece(turn, from[0], from[1], to[0], to[1], board)) {
						turn = !turn;
						repeat = false;
						validPiece = false;
					// give player another jump if he jumped already and there's another removable enemy piece
					} else {
						from[0] = to[0];
						from[1] = to[1];
						repeat = true;
						if (turn)
							System.out.println(player1 + " (black) can move piece at " + (char)(to[1]+ASCII_A) + (char)(to[1]+ASCII_1) + " again.");
						else
							System.out.println(player2 + " (white) can move piece at " + (char)(to[1]+ASCII_A) + (char)(to[1]+ASCII_1) + " again.");
					}					
	
					DrawBoard.display(board);
				}
			}
			
		} while (!checkWinner(concede, turn, board, player1, player2, startDate, startTime, date, time)); 		
		
	}
	
	// takes move input, checks if there's a typo, takes concede input
	public static void getMove(Scanner input, int[] arr) {
		
		boolean isValid = true;
		String in;
		
		do {
			// get input for moves
			in = input.nextLine();
			in = in.toUpperCase();
			
			// check if they typed concede 
			if (in.equals("CONCEDE")) {
				arr[0] = -1;
				arr[1] = -1;
				isValid = true;
			// Check if the string is a 2 character sequence
			} else if(!(in.length() == 2)) {
				isValid = false;
			// Convert value of the characters to int 
			} else {
				arr[0] = ((int)in.charAt(0) - ASCII_A);
				arr[1] = ((int)in.charAt(1) - ASCII_1);
				
				// Make sure we have values that aren't out of bounds for our array.
				if (arr[0] > BOARD_SIZE || arr[0] < 0 || arr[1] > BOARD_SIZE || arr[1] < 0)
					isValid = false;
				else
					isValid = true;
			}
			if (!isValid)
				System.out.println("Input invalid, type again: ");

		} while (!isValid);
		
	}
	
	// checks win/lose conditions, carries out concede, writes down who won
	// return true if a player won
	public static boolean checkWinner(boolean concede, boolean turn, char[][] board, String player1, String player2, Date startDate, Date startTime, SimpleDateFormat date, SimpleDateFormat time) {
		
		boolean winner = false;
		boolean canMove = false;
		int black = 0;
		int white = 0;
		Date endTime = new Date();
		
		// Check how many pieces each player has, and if the active player has any valid moves left
		for (int r = 0; r < BOARD_SIZE; r++) {
			for (int c = 0; c < BOARD_SIZE; c++) {
				// Check for all of black's pieces
				if (board[r][c] == BLACK || board[r][c] == BKING){
					black += 1;
					if (turn) {
						for (int r2 = 0; r2 < BOARD_SIZE; r2++) {
							for (int c2 = 0; c2 < BOARD_SIZE; c2++) {
								if(MoveProcess.validMove(false, false, turn, r, c, r2, c2, board))
									canMove = true;
							}
						}
					}
				// Check for all of white's pieces
				} else if (board[r][c] == WHITE || board[r][c] == WKING){
					white += 1;
					if(!turn) {
						for (int r2 = 0; r2 < BOARD_SIZE; r2++) {
							for (int c2 = 0; c2 < BOARD_SIZE; c2++) {
								if(MoveProcess.validMove(false, false, turn, r, c, r2, c2, board))
									canMove = true;
							}
						}
					}
				}
			}
		}

		// decide what to do with all that information
		String winDate = date.format(startDate);
		String sTime = time.format(startTime);
		String eTime = time.format(endTime);
		if (black == 0 || (turn && !canMove) || (turn && concede == true)) {
			// if they typed concede
			if (turn && concede == true)
				System.out.println(player1 + " conceded.");
			// if there are no more pieces on the board
			else if (black == 0)
				System.out.println("Black has no more pieces left.");
			// if neither then there are no more valid moves to make, very rare but can happen
			else
				System.out.println("Black has no more valid moves left.");
			System.out.println("Game over. " + player2 + " won.");
			// sending it off to FileHandle because I didn't wanna import File again
			FileHandle.saveOutcome(winDate, sTime, eTime, player2, player1);
			// writeFile(date.format(startDate) + " - " + player2 + " (won) vs " + player1 + " - " + time.format(startTime) + " to " + time.format(endTime));
			winner = true;
		}
		// same stuff for white
		else if (white == 0 || (!turn && !canMove) || (!turn && concede == true)) {
			if (!turn && concede)
				System.out.println(player2 + " conceded.");
			else if (white == 0)
				System.out.println("White has no more pieces left.");
			else
				System.out.println("White has no more valid moves left.");
			System.out.println("Game over. " + player1 + " won.");
			FileHandle.saveOutcome(winDate, sTime, eTime, player1, player2);
			// writeFile(date.format(startDate) + " - " + player1 + " (won) vs " + player2 + " - " + time.format(startTime) + " to " + time.format(endTime));
			winner = true;
		}
		
		return winner;
	}

}
