package lab1;

import java.io.IOException;

import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.helper.FastIterator;
import jdbm.htree.HTree;

public class JDBMsample {

	public static void main(String[] args) {
		try {
			
			RecordManager recman;
			HTree hashtable;
			
			// Create a RecordManager name "testRM"
			recman = RecordManagerFactory.createRecordManager("testRM");
			// get the record id of the object named "ht1"
			long recid = recman.getNamedObject("ht1");
			
			if(recid != 0) {
				// load the hash table named "ht1" from the RecordManager
				hashtable = HTree.load(recman, recid);
			} else {
				// create a hash table in the RecordManager
				hashtable = HTree.createInstance(recman);
				// set the name of the hash table to "ht1"
				recman.setNamedObject("ht1", hashtable.getRecid());
			}
			
			// add some triples into the hashtable
			hashtable.put("key1", "context 1");
			hashtable.put("key2", "context 2");
			hashtable.put("key3", "context 3");
			hashtable.put("key4", "context 4");
			
			// get the content of the "key3" from the hash table
			System.out.println(hashtable.get("key3"));
			
			// remove the triple with key = "key2"
			hashtable.remove("key2");
			
			// iterate through all keys
			FastIterator iter = hashtable.keys();
			
			String key;
			while((key=(String)iter.next()) != null) {
				// get and print the content of each key
				System.out.println(key + " : " + hashtable.get(key));
			}
			
			// commit the changes
			recman.commit();
			
			// close the RecordManager
			recman.close();
			
			
		} catch(IOException ex) {
			System.err.println(ex.toString());
		}
	}

}
