import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandle {
	final static String FILENAME = "oldgames.txt";
	
	// write previous game's outcome to a file
	public static void writeFile(String gameSummary) {
		
		File f = new File(FILENAME);
		
		try {
			FileWriter write = new FileWriter(f,true);
			write.append(gameSummary + "\n");
			write.close();
		} catch (IOException e) {
			System.out.println("There was an error: \n" + e.getMessage());
		}
	}

	// read previous games file
	public static void readFile() {
		
		try {
			File f = new File(FILENAME);
			Scanner read = new Scanner(f);
			while (read.hasNextLine()) {
				System.out.println(read.nextLine());
			}
			read.close();
			
		} catch (Exception e) {
			System.out.println("There were no previous games found. \n" + e.getMessage());
		}
		
	}
	
	// Save match outcome
	public static void saveOutcome(String winDate, String sTime, String eTime, String winner, String loser) {
		writeFile(winDate + " - " + winner + " (won) vs " + loser + " - " + sTime + " to " + eTime);
	}
}