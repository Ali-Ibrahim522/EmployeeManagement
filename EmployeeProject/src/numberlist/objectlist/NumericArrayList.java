package numberlist.objectlist;

import java.io.Serializable;
import numberlist.IndexException;

/**
 * This class provides a growable array for primitive object values.
 *
 * @author Ali Ibrahim
 * @version 3.0
 */
public class NumericArrayList extends NumericList implements Copiable, Serializable{

    //fields
    private Copiable[] list;

    /**
     * Constructor. Initializes the underlying array to 10 elements.
     */
    public NumericArrayList() {
	list = new Copiable[10];
	count = 0;
    }

    /**
     * Inserts the given object value at the given index.Existing elements are
     * moved up as needed to make room for the new value. If value is out of
     * range, the method will just return
     *
     * @param index the index where the new value should be stored
     * @param value the value to be stored
     * @throws numberlist.IndexException
     */
    @Override
    public void add(int index, Copiable value) throws IndexException{
	if (index < 0 || index > count) {
	    throw new IndexException(0, count, index);
	} else {
	    count++;
	    if (count == list.length) {
		Copiable[] newList = new Copiable[list.length * 2];
		for (int i = 0; i < list.length; i++) {
		    newList[i] = list[i];
		}
		list = newList;
	    }
	    if (list[index] == null) {
		list[index] = value;
	    } else {
		Copiable tempTwo = value;
		for (int i = index; i < list.length; i++) {
		    Copiable temp = list[i];
		    list[i] = tempTwo;
		    tempTwo = temp;
		}
	    }
	}
    }

    /**
     * Deletes the value at the given index.If index is out of range return
     * null. Existing elements are moved down as needed to keep all values
     * contiguous, without any empty spaces in the array.
     *
     * @param index the index of the element that should be removed
     * @return the value that was removed
     * @throws numberlist.IndexException
     */
    @Override
    public Copiable remove(int index) throws IndexException{
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
	if (index < 0 || index >= count) {
	    throw new IndexException(0, count - 1, index);
	} else {
	    Copiable temp = list[index];
	    for (int i = index + 1; i < count; i++) {
		list[i - 1] = list[i];

	    }
	    count--;
	    return temp;
	}
    }

    /**
     * Deletes the first instance of the given value. Existing elements are
     * moved down as needed to keep all values contiguous, without any empty
     * spaces in the array. If the value does not exist, this method returns
     * without doing anything.
     *
     * @param value the value to remove
     */
    @Override
    public void remove(Copiable value) {
	int index = findFirstIndex(value);
	if (index >= 0) {
	    try {
		remove(index);
	    } catch (IndexException ie) {
		System.out.println(ie.getMessage());
	    }
	}
    }

    /**
     * Returns the value at the given index without removing it.If index is out
     * of range, return null
     *
     * @param index the index of the element
     * @return the value at that index
     * @throws numberlist.IndexException
     */
    @Override
    public Copiable getValue(int index) throws IndexException{
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
	if (index < 0 || index >= count) {
	    throw new IndexException(0, count - 1, index);
	}
	return list[index];
    }

    /**
     * Returns the index of the first instance of the given value in the array.
     * If the value doesn't exist, -1 is returned.
     *
     * @param value the value to find in the array
     * @return the index where the value was found, or -1 if not found
     */
    @Override
    public int findFirstIndex(Copiable value) {
	for (int i = 0; i < count; i++) {
	    if (list[i].equals(value)) {
		return i;
	    }
	}
	return -1;
    }

    /**
     * Provides access to the number of values currently in the array.
     *
     * @return the number of values in the array
     */
    @Override
    public int getCount() {
	return count;
    }

    /**
     * Provides a string representation of the growable array, displaying all
     * values currently in the array using the format [ value1, value2, ... ].
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
     * copies each element of a list and adds them to a new NumericArrayList to
     * which is returned.
     *
     * @return the duplicated NumericArrayList
     */
    @Override
    public NumericArrayList copy() {
	NumericArrayList copyList = new NumericArrayList();
	for (int i = 0; i < count; i++) {
	    try {
		copyList.add(i, this.getValue(i).copy());
	    } catch (IndexException ie) {
		System.out.println(ie.getMessage());
	    }
	}
	return copyList;
    }

    /**
     * replaces the value at index with a new value.
     *
     * @param obj the new value that replaces the value at index
     * @param index the index that the new value will be set to
     * @return the old value that has been replaced
     * @throws IndexException
     */
    @Override
    public Copiable set(int index, Copiable obj) throws IndexException {
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
	if (index < 0 || index >= count) {
	    throw new IndexException(0, count - 1, index);
	}
	Copiable value = list[index];
	list[index] = obj;
	return value;
    }

}
