/**
 * dnaRunnable.java --- class that overloads the threads' run() method
 * @author Kerry Zhao (kxz8411)
 */
import java.util.ArrayList;

public class dnaRunnable<query> implements Runnable {
	private String query;
	private String queryComplement;
	private String dbString;
	private String queryNot = "";
	private int numericalOrder;
	private ArrayList<Integer> indices = new ArrayList<Integer>();
	private ArrayList<Integer> indices2 = new ArrayList<Integer>();
	private int total;
	private dnaMonitor monitor;

	/**
	 * constructor
	 * 
	 * @param query
	 *            --- the query
	 * @param dbString
	 *            --- the sequence that is given to the thread
	 * @param numericalOrder
	 *            --- the 'name' of the thread
	 * @param monitor
	 *            --- the shared monitor
	 */
	public dnaRunnable(String query, String dbString, int numericalOrder,
			dnaMonitor monitor) {
		this.query = query;
		this.dbString = dbString;
		this.numericalOrder = numericalOrder;
		this.monitor = monitor;
	}

	// the overloaded run method
	public void run() {
		// creates the queryComplement
		for (int i = 0; i < query.length(); i++) {
			if (query.charAt(i) == 'C') {
				queryNot = queryNot + "G";
			} else if (query.charAt(i) == 'G') {
				queryNot = queryNot + "C";
			} else if (query.charAt(i) == 'A') {
				queryNot = queryNot + "T";
			} else {
				queryNot = queryNot + "A";
			}
		}
		queryComplement = new StringBuilder(queryNot).reverse().toString();

		// checks for substring, capitalizes, and prints
		if ((dbString.contains(query))
				|| ((dbString.contains(queryComplement)))) {
			dbString = dbString.substring(0, 1) + dbString.substring(1).toLowerCase();
			query = query.toLowerCase();
			queryComplement = queryComplement.toLowerCase();

			int index = 0;
			for (int i = 0; i < dbString.length(); i++) {
				if (!(indices.contains(index))) {
					index = dbString.indexOf(query);
					if (index == -1) {
						break;
					}
					indices.add(index);
					dbString = dbString.substring(0, index)
							+ dbString.substring(index, index + 4)
									.toUpperCase()
							+ dbString.substring(index + 4);
				} else {
					index = dbString.indexOf(query, index + 1);
					indices.add(index);
					if (index == -1) {
						break;
					}
					dbString = dbString.substring(0, index)
							+ dbString.substring(index, index + 4)
									.toUpperCase()
							+ dbString.substring(index + 4);
				}
			}
			int index2 = 0;
			for (int i = 0; i < dbString.length(); i++) {
				if (!(indices2.contains(index2))) {
					index2 = dbString.indexOf(queryComplement);
					if (index2 == -1) {
						break;
					}
					indices2.add(index2);
					dbString = dbString.substring(0, index2)
							+ dbString.substring(index2, index2 + 4)
									.toUpperCase()
							+ dbString.substring(index2 + 4);
				} else {
					index2 = dbString.indexOf(queryComplement, index + 1);
					indices2.add(index);
					if (index2 == -1) {
						break;
					}
					dbString = dbString.substring(0, index2)
							+ dbString.substring(index2, index2 + 4)
									.toUpperCase()
							+ dbString.substring(index2 + 4);
				}
			}
			int loopOut = 0;
			while (loopOut != -1) {
				loopOut = monitor.print(numericalOrder, dbString);
			}
		}
		//
		else {
			int loopOut = 0;
			while (loopOut != -1) {
				loopOut = monitor.print2(numericalOrder);
			}
		}
	}
}