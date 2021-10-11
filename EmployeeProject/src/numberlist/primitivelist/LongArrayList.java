package numberlist.primitivelist;

import java.io.Serializable;
import numberlist.IndexException;

/**
 * This class provides a growable array for primitive long values.
 * 
 * @author Ali Ibrahim
 * @version 2.0
 */
class LongArrayList implements Serializable{

    //fields
    private long[] list;
    private int count;
    

    /**
     * Constructor. Initializes the underlying array to 10 elements.
     */
    LongArrayList() {
        list = new long[10];
        count = 0;
    }
    
    /**
     * Inserts the given long value at the given index. Existing elements
     * are moved up as needed to make room for the new value. If value 
     * is out of range, the method will just return
     * 
     * @param index the index where the new value should be stored
     * @param value the value to be stored
     * @throws IndexException
     */
    public void add(int index, long value) throws IndexException{
        if(index < 0 || index > count ){
           throw new IndexException(0, count, index); 
        } else {
            count++;
            if (count == list.length){
               long[] newList = new long[list.length * 2];
               for (int i = 0; i < list.length; i++) {
                    newList[i] = list[i];
               }
               list = newList;
            }
            if (list[index] == 0L){
                list[index] = value;
            }
            else{     
                long tempTwo = value;
                for (int i = index; i < list.length; i++){
                    long temp = list[i];
                    list[i] = tempTwo;
                    tempTwo = temp;   
                }
            
            }
        }
    }

    /**
     * Deletes the value at the given index. If index is out of range
     * return Long.MIN_VALUE.
     * Existing elements
     * are moved down as needed to keep all values contiguous, without
     * any empty spaces in the array.
     * 
     * @param index the index of the element that should be removed
     * @return the value that was removed
     * @throws IndexException
     */
    public long remove(int index) throws IndexException{
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
        if(index < 0 || index >= count){
            throw new IndexException(0, count - 1, index);
        }
        else{
            long temp = list[index];
            for (int i = index + 1; i < count; i++){
                list[i - 1] = list[i];
            }
            count--;
            return temp;
        }
    }

    /**
     * Deletes the first instance of the given value. Existing elements
     * are moved down as needed to keep all values contiguous, without
     * any empty spaces in the array. If the value does not exist, this
     * method returns without doing anything.
     * 
     * @param value the value to remove
     */
    public void remove(long value) {
        int index = findFirstIndex(value);
        if (index >= 0){
	    try {
		remove(index);
	    } catch (IndexException ie) {
		System.out.println(ie.getMessage());
	    }
        }
    }
    
    /**
     * Returns the value at the given index without removing it. If index
     * is out of range return Long.MIN_VALUE.
     * 
     * @param index the index of the element
     * @return the value at that index
     * @throws IndexException
     */
    public long getValue(int index) throws IndexException{
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
        if(index < 0 || index >= count){
           throw new IndexException(0, count - 1, index);
        }
        else{
           return list[index];
        }
    }
    
    /**
     * Returns the index of the first instance of the given value
     * in the array. If the value doesn't exist, -1 is returned.
     * 
     * @param value the value to find in the array
     * @return the index where the value was found, or -1 if not found
     */
    public int findFirstIndex(long value) {
        int index = -1;
        for (int i = 0; i < count && index < 0; i++){
            long number = list[i];
            if (number == value){
                index = i;
            }
        }
        return index; 
    }
    
    /**
     * Provides access to the number of values currently in the array.
     * 
     * @return the number of values in the array 
     */
    public int getCount() {
        return count;
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
        for (int i = 0; i < count; i++) {
            output += list[i] + ", ";
        }
        if (count > 0) {
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
    public long set(int index, long value) throws IndexException {
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
	if (index < 0 || index >= count) {
	    throw new IndexException(0, count - 1, index);
	}
	long rtnValue = list[index];
	list[index] = value;
	return rtnValue;
    }

}