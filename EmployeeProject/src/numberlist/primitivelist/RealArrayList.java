package numberlist.primitivelist;

import java.io.Serializable;
import numberlist.IndexException;

/**
 * This class provides a growable array for primitive double values
 * which inherits from DoubleArrayList. This class also adds
 * additional methods from its superclass.
 *
 * @author Ali Ibrahim
 * @version 2.0
 */
public class RealArrayList extends DoubleArrayList implements Serializable{

    /**
     * Inserts the given double value at the end of the list. 
     * 
     * @param value the value to be stored
     * @return the index it was inserted it
     */
    public int add(double value) {
	try {
	    add(getCount(), value);
	} catch (IndexException ex) {
	    System.out.println("The exception should never be thrown in this situation");
	}
        return getCount() - 1;
    }
    
    /**
     * Deletes all instances of the value from the list
     * 
     * @param value to be deleted
     */    
    public void removeAll(double value) {
        for (int i = this.getCount() - 1; i >= 0; i--) {
	    try {
		if (this.getValue(i) == value) {
		    this.remove(i);
		}
	    } catch (IndexException ex) {
		System.out.println("The exception should never be thrown in this situation");
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
    public int findLastIndex(double value) {
	try {
	    for (int i = this.getCount() - 1; i >= 0; i--) {
		if (this.getValue(i) == value) {
		    return i;
		}
	    }
	} catch (IndexException ex) {
	    System.out.println("The exception should never be thrown in this situation");
	}
	return -1;
    }
}
