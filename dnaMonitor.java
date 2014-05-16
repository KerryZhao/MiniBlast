/**
 * dnaMonitor --- the monitor class that allows the threads to print in order
 * @author Kerry Zhao (kxz8411)
 * 
 */
public class dnaMonitor {
	// the counter int
	private int upTo = 1;

	// default constructor
	public dnaMonitor() {

	}

	/**
	 * prints out the given string if the number matches upTo
	 * 
	 * @param number
	 *            --- the 'name' of the calling thread
	 * @param string
	 *            --- the string that is to be printed
	 * @return -1 if incremented, else 0
	 */
	public synchronized int print(int number, String string) {
		if (number == upTo) {
			System.out.println(string);
			upTo++;
			notifyAll();
			return -1;
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}

	/**
	 * prints out an empty string; will be called by threads whose turn has not
	 * yet arrived
	 * 
	 * @param number
	 *            ---the 'name' of the calling thread
	 * @return -1 if incremented, else 0
	 */
	public synchronized int print2(int number) {
		if (number == upTo) {
			System.out.print("");
			upTo++;
			notifyAll();
			return -1;
		} else {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}
}