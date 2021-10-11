package numberlist.objectlist;

import java.io.Serializable;
import numberlist.IndexException;

/**
 * this class is responsible for holding all of the list ADT's basic methods
 *
 * @author Ali Ibrahim
 * @version 2.0
 */
abstract class NumericList implements Serializable{

    int count;

    /**
     * Inserts the given object value at the given index.Existing elements are
     * moved up as needed to make room for the new value. If value is out of
     * range, the method will just throw an IndexException
     *
     * @param index the index where the new value should be stored
     * @param obj the value to be stored
     * @throws numberlist.IndexException
     */
    abstract void add(int index, Copiable obj) throws IndexException;

    /**
     * Inserts the given Object at the end of the list.
     *
     * @param obj the value to be stored
     * @return the index it was inserted it
     */
    public int add(Copiable obj) {
	try {
	    NumericList.this.add(count, obj);
	} catch (IndexException ie) {
	    System.out.println(ie.getMessage());
	}
	return count - 1;
    }
    
    /**
     * replaces the value at index with a new value.
     *
     * @param obj the new value that replaces the value at index
     * @param index the index that the new value will be set to
     * @return the old value that has been replaced
     * @throws IndexException
     */
    abstract Copiable set(int index, Copiable obj) throws IndexException;
    
    /**
     * removes the value at the given index
     *
     * @param obj the index to which is removed
     * @throws IndexException
     */
    abstract Copiable remove(int index) throws IndexException;

    /**
     * remove the first occurrence of a given value
     *
     * @param obj the value to be removed
     */
    abstract void remove(Copiable obj);

    /**
     * finds the value stored in a given index
     *
     * @param index the chosen index of a desired value
     * @return the value that was stored in index
     * @throws IndexException
     */
    abstract Copiable getValue(int index) throws IndexException;

    /**
     * finds the first index of a given object
     *
     * @param obj the value that is searched for
     * @return the index of the element that is equal to obj
     */
    abstract int findFirstIndex(Copiable obj);

    /**
     * Provides access to the number of values currently in the array.
     *
     * @return the number of values in the array
     */
    public int getCount() {
	return count;
    }
}
