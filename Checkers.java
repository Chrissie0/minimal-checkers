import java.util.Scanner;

public class Checkers {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		// show the menu
		System.out.println("Checkers \n");
		System.out.println("1. Credits");
		System.out.println("2. How to play"); 
	 	System.out.println("3. Play");
	 	System.out.println("4. Previous winners");
	 	System.out.println("5. Exit");
		
	 	int menu = 0;
		
		do {
			
			if (input.hasNextInt())
				menu = input.nextInt();
			
			input.nextLine();
			
			switch (menu) {
				case 1: System.out.println("Chrissie0@GitHub");
						break;
				case 2: System.out.println(
						"* Checkers is played by two opponents on opposite sides of the gameboard. One player has the black pieces, the other has the white pieces. Players alternate turns.\n"
						+ "* You move by typing the coordinates on the board, e.g. From: C2 To: D3\n"
						+ "* A player may not move an opponent's piece. A move consists of moving a piece diagonally to an adjacent unoccupied square.\n"
						+ "* Regular pieces can only move diagonally forwards.\n"
						+ "* If the adjacent square contains an opponent's piece, and the square immediately beyond it is vacant, the piece may be captured (and removed from the game) by jumping over it.\n"
						+ "* Capturing is not mandatory in this version of the game.\n"
						+ "* Multiple enemy pieces can be captured in a single turn by successive jumps made by the same piece.\n"
						+ "* When a piece reaches the opponent's first row, it becomes a king and gains the ability to move and capture backwards.\n"
						+ "* The game is over when a player loses all their pieces or cannot make a move due to being blocked.\n"
						+ "* You can concede a game by typing 'concede'");
						break;
				case 3:	GamePlay.gamePlay(input);
						System.out.println("Checkers \n");
						System.out.println("1. Developers");
						System.out.println("2. How to play"); 
					 	System.out.println("3. Play");
					 	System.out.println("4. Previous winners");
					 	System.out.println("5. Exit");
						break;
				case 4: FileHandle.readFile();
						break;
				case 5: System.out.println("Goodbye"); 
						input.close();
						System.exit(1);
				default: System.out.println("Incorrect input, try again:");
			}
		} while (menu > 5 || menu < 1 || input.hasNextInt());
		
		input.close();

	}

}