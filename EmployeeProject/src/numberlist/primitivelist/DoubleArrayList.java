
package numberlist.primitivelist;

import java.io.Serializable;
import numberlist.IndexException;

/**
 * This class provides a growable array for primitive double values.
 * 
 * @author Ali Ibrahim
 * @version 2.0
 */
class DoubleArrayList implements Serializable{
    
    LongArrayList list = new LongArrayList();
    
    /**
     * creates an array list to which holds double values. This class uses and
     * instance of LongArrayList to hold the list and when the values are
     * shown they are converted to doubles
     */
    DoubleArrayList(){
       
    }
    
    /**
     * Inserts the given double value at the given index. Existing elements
     * are moved up as needed to make room for the new value. If index
     * is out of range, the method will just return.
     * 
     * @param index the index where the new value should be stored
     * @param value the value to be stored
     * @throws IndexException
     */
    public void add(int index, double value) throws IndexException{
	list.add(index, Double.doubleToRawLongBits(value));
    }
    
    /**
     * Deletes the value at the given index. If index is out of range
     * return Double.MIN_VALUE.
     * Existing elements
     * are moved down as needed to keep all values contiguous, without
     * any empty spaces in the array.
     * 
     * @param index the index of the element that should be removed
     * @return the value that was removed
     * @throws IndexException
     */    
    public double remove(int index) throws IndexException{
	return Double.longBitsToDouble(list.remove(index));
    }
    
    /**
     * Deletes the first instance of the given value. Existing elements
     * are moved down as needed to keep all values contiguous, without
     * any empty spaces in the array. If the value does not exist, this
     * method returns without doing anything.
     * 
     * @param value the value to remove
     */
    public void remove(double value){
       list.remove(Double.doubleToRawLongBits(value));
    }
    
    /**
     * Returns the value at the given index without removing it. If index
     * is out of range return Double.MIN_VALUE.
     * 
     * @param index the index of the element
     * @return the value at that index
     * @throws IndexException
     */
    public double getValue(int index) throws IndexException{
	return Double.longBitsToDouble(list.getValue(index));
    }
    /**
     * Returns the index of the first instance of the given value
     * in the array. If the value doesn't exist, -1 is returned.
     * 
     * @param value the value to find in the array
     * @return the index where the value was found, or -1 if not found
     */
    public int findFirstIndex(double value){
        return list.findFirstIndex(Double.doubleToRawLongBits(value));
    }
    
    /**
     * Provides access to the number of values currently in the array.
     * 
     * @return the number of values in the array 
     */
    public int getCount(){
        return list.getCount();
    }
    /**
     * Provides a string representation of the growable array, displaying
     * all values currently in the array using the format [ value1, 
     * value2, ... ].
     * 
     * @return the string representation of the array
     */
    @Override
    public String toString() {
        String output = "[ ";
        for (int i = 0; i < getCount(); i++) {
	    try {
		output += getValue(i) + ", ";
	    } catch (IndexException ex) {
		System.out.println("The exception should never be thrown in this situation");
	    }
        }
        if (getCount() > 0) {
            output = output.substring(0, output.length() - 2);
        } else {
            output = output.substring(0, output.length() - 1);
        }
        output += " ]";
        return output;
    }

    /**
     * replaces the value at index with a new value.
     *
     * @param value the new value that replaces the value at index
     * @param index the index that the new value will be set to
     * @return the old value that has been replaced
     * @throws IndexException
     */
    public double set(int index, double value) throws IndexException {
	if (list.getCount() == 0) {
	    throw new IndexException(-1, -1, index);
	}
	if (index < 0 || index >= list.getCount()) {
	    throw new IndexException(0, list.getCount() - 1, index);
	}
	long rtnValue = list.getValue(index);
	list.set(index, Double.doubleToRawLongBits(value));
	return Double.longBitsToDouble(rtnValue);
    }

    
  
}
