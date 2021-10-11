package numberlist.primitivelist;
import java.io.Serializable;
import numberlist.IndexException;

/**
 * This class provides a growable array for primitive long values
 * which inherits from LongArrayList. This class also adds
 * additional methods from its superclass.
 * 
 * @author Ali Ibrahim
 * @version 2.0
 */
public class IntegerArrayList extends LongArrayList implements Serializable{
    
    /**
     * Inserts the given long value at the end of the list. 
     * 
     * @param value the value to be stored
     * @return the index it was inserted it
     */    
    
    public int add(long value) {
	try {
	    add(getCount(), value);
	} catch (IndexException ie) {
	    System.out.println(ie.getMessage());
	}
        return getCount() - 1;
    }
    /**
     * Deletes all instances of the value from the list
     * 
     * @param value to be deleted
     */
    public void removeAll(long value){
        for (int i = this.getCount() - 1; i >= 0; i--) {
	    try {
		if (this.getValue(i) == value) {
		    this.remove(i);
		}
	    } catch (IndexException ie) {
		System.out.println(ie.getMessage());
	    }
	}
    }
    
    /**
     * returns the index of the last element in the list that contains the
       value, or -1 if the value does not exist
     * 
     * @param value to find in the array
     * @return the index where it was found
     */
    public int findLastIndex(long value){
	try {
	    for (int i = this.getCount() - 1; i >= 0; i--) {
		if (this.getValue(i) == value) {
		    return i;
		}
	    }
	} catch (IndexException ie) {
	    System.out.println("The exception should never be thrown in this situation");
	} 
	return -1;
    }
}
