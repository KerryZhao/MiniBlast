/**
 * MiniBlast.java --- program that creates the threads and runs the main. Creates and uses
 * 		a monitor to handle synchronization.
 * @author Kerry Zhao (kxz8411)
 * 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MiniBlast {

	// an arraylist to house the strings from the database file
	private static ArrayList<String> readIn = new ArrayList<String>();
	// an arraylist that holds the threads
	private static ArrayList<Thread> threadList = new ArrayList<Thread>();
	// an arraylist that holds the runnables
	private static ArrayList<dnaRunnable> runnableList = new ArrayList<dnaRunnable>();
	// the monitor
	private static dnaMonitor monitor = new dnaMonitor();

	// the main method
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Error: Incorrect amount of arguments");
			System.exit(0);
		} else {
			try {
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
				while (br.ready()) {
					String s = br.readLine();
					readIn.add(s);
				}
				br.close();
				for (int i = 0; i < readIn.size(); i++) {
					dnaRunnable runny = new dnaRunnable(args[1], readIn.get(i),
							i + 1, monitor);
					runnableList.add(runny);
				}
				for (dnaRunnable runny : runnableList) {
					Thread x = new Thread(runny);
					threadList.add(x);
					x.start();
				}
			} catch (FileNotFoundException e) {
				System.err.println("FileNotFoundException");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.exit(0);
	}

}