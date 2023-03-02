package lab1;

import java.io.IOException;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

/*
 * Start your work on the skeleton program InvertedIndex.java. The following output is expected:
 * 
 * First print:
 * cat = doc2 6
 * dog = doc1 33
 * 
 * Second print:
 * cat = doc2 6 doc8 3 doc11 106
 * dog = doc1 33 doc6 73 doc8 83 doc10 5
 * 
 * Third print:
 * cat = doc2 6 doc8 3 doc11 106
 */
public class InvertedIndex {
	
	private static RecordManager recman;
	private static HTree hashtable;
	private static FastIterator iter;
	private static String doc_prefix = "doc";
	
	public static void main(String args[]) {
		
		try {
			
			recman = RecordManagerFactory.createRecordManager("ex1RM");
			long recid = recman.getNamedObject("ht1");
			
			if(recid != 0) {
				// load the hashtable
				hashtable = HTree.load(recman, recid);
			} else {
				// create a new hashtable
				hashtable = HTree.createInstance(recman);
				recman.setNamedObject("ht1", recid);
			}
			
			// Setup Initial Data
			System.out.println("Initial Data:");
			addEntry("dog", 23, 43, hashtable);
			addEntry("dog", 10, 6, hashtable);
			addEntry("cat", 4, 2, hashtable);
			addEntry("pig", 98, 20, hashtable);
			addEntry("pig", 65, 22, hashtable);
			addEntry("pig", 19, 45, hashtable);
			printAll();
			
			// First Print
			System.out.println("First Print:");
			delEntry("dog");
			delEntry("cat");
			delEntry("pig");
			addEntry("cat", 2, 6, hashtable);
			addEntry("dog", 1, 33, hashtable);
			printAll();
			
			// Second Print
			System.out.println("Second Print:");
			addEntry("cat", 8, 3, hashtable);
			addEntry("cat", 11, 106, hashtable);
			addEntry("dog", 6, 73, hashtable);
			addEntry("dog", 8, 83, hashtable);
			addEntry("dog", 10, 5, hashtable);
			printAll();
			
			// Third Print
			System.out.println("Third Print:");
			delEntry("dog");
			printAll();
			
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				recman.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/*
	 * Add a "docX Y" entry for the key "word"
	 * 
	 * For example:
	 * - Before, one the (key, content) paris in the database is:
	 * ("dog", "doc23 43 doc10 6")
	 * = After calling addEntry("dog", 5, 9)
	 * ("dog", "doc23 43 doc10 6 doc5 9")
	 * 
	 */
	public static void addEntry(String word, int x, int y, HTree hashtable) {
		try {
			// get the original value by key
			String orig_val = (String)hashtable.get(word);
			String new_val = "";
			if(orig_val == null) {
				new_val = doc_prefix + x + " " + y;
			} else {
				new_val = orig_val + " " + doc_prefix + x + " " + y;
			}
			hashtable.put(word, new_val);
			recman.commit();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  Delete the word and its list from the database
	 *  
	 *  For example, after calling delEntry("dog", hashtable),
	 *  the key "dog" and its content are deleted from the database
	 */
	public static void delEntry(String word) {
		try {
			hashtable.remove(word);
			recman.commit();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Print all the data in the database
	 * 
	 * For example, a call for printAll(hashtable) may produce the following output:
	 * ------------------------------------------------------------
	 * dog = doc23 43 doc10 6
	 * cat = doc4 2
	 * pig = doc98 20 doc65 22 doc19 45
	 * ------------------------------------------------------------
	 */
	public static void printAll() {
		try {
			iter = hashtable.keys();
			String key = null;
			while((key=(String)iter.next()) != null) {
				System.out.println(key + " = " + hashtable.get(key));
			}
			System.out.println();
		} catch(IOException e ) {
			e.printStackTrace();
		}
	}

}
