package numberlist.objectlist;

import java.io.Serializable;
import numberlist.IndexException;

/**
 * this class is responsible for providing all the basic methods of the
 * LinkedList.
 *
 * @author Ali Ibrahim
 * @version 2.0
 */
public class NumericLinkedList extends NumericList implements Copiable, Serializable{

    private Node firstNode;
    
    /**
     * creates the numeric linked list
     */
    public NumericLinkedList() {
    }

    /**
     * Inserts the given object value at the given index.Existing elements are
     * moved up as needed to make room for the new value. If value is out of
     * range, the method will just return
     *
     * @param index the index where the new value should be stored
     * @param obj the value to be stored
     * @throws numberlist.IndexException
     */
    @Override
    public void add(int index, Copiable obj) throws IndexException{
	if (index < 0 || index > count) {
	    throw new IndexException(0, count, index);
	}
	Node newNode = new Node(obj);
	if (index == 0) {
	    newNode.setNextNode(firstNode);
	    firstNode = newNode;
	} else {
	    Node currentNode = firstNode;
	    for (int i = 0; i < index - 1; i++) {
		currentNode = currentNode.getNextNode();
	    }
	    newNode.setNextNode(currentNode.getNextNode());
	    currentNode.setNextNode(newNode);
	}
	count++;
    }

    /**
     * Deletes the value at the given index.If index is out of range return
     * null. Existing elements are moved down as needed to keep all values
     * contiguous, without any empty spaces in the LinkedList.
     *
     * @param index the index of the element that should be removed
     * @return the value that was removed
     * @throws numberlist.IndexException
     */
    @Override
    public Copiable remove(int index) throws IndexException{
	Copiable removedNode;
	if (count == 0) {
	    throw new IndexException(-1, -1, index);
	}
	if (index < 0 || index >= count) {
	    throw new IndexException(0, count - 1, index);
	}
	if (index == 0) {
	    removedNode = firstNode.getValue();
	    firstNode = firstNode.getNextNode();
	    count--;
	} else {
	    Node nodeBehindRemoved = firstNode;
	    for (int i = 0; i < index - 1; i++) {
		nodeBehindRemoved = nodeBehindRemoved.getNextNode();
	    }
	    removedNode = nodeBehindRemoved.getNextNode().getValue();
	    if (index == count - 1) {
		nodeBehindRemoved.setNextNode(null);
	    } else {
		nodeBehindRemoved.setNextNode(nodeBehindRemoved.getNextNode().
			getNextNode());
	    }
	    count--;
	}
	return (Copiable)removedNode;
    }

    /**
     * Deletes the first instance of the given value. Existing elements are
     * moved down as needed to keep all values contiguous, without any empty
     * spaces in the LinkedList. If the value does not exist, this method
     * returns without doing anything.
     *
     * @param obj the value to remove
     */
    @Override
    public void remove(Copiable obj) {
	int index = findFirstIndex(obj);
	if (index != -1) {
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
	Node currentNode = firstNode;
	for (int i = 1; i <= index; i++) {
	    currentNode = currentNode.getNextNode();
	}
	return currentNode.getValue();
    }

    /**
     * Returns the index of the first instance of the given value in the
     * LinkedList. If the value doesn't exist, -1 is returned.
     *
     * @param obj the value to find in the LinkedList
     * @return the index where the value was found, or -1 if not found
     */
    @Override
    public int findFirstIndex(Copiable obj) {
	int index = -1;
	Node currentNode = firstNode;
	for (int i = 0; i < count; i++) {
	    if (currentNode.getValue().equals(obj)) {
		return i;
	    }
	    if (i != count - 1) {
		currentNode = currentNode.getNextNode();
	    }
	}
	return index;
    }

    /**
     * Provides a string representation of the LinkedList, displaying all values
     * currently in the list using the format [ value1, value2, ... ].
     *
     * @return the string representation of the array
     */
    @Override
    public String toString() {
	Node currentNode = firstNode;
	String rtnString = "[ ";
	for (int i = 0; i < count; i++) {
	    rtnString += currentNode.getValue();
	    if (i != count - 1) {
		rtnString += ",";
		currentNode = currentNode.getNextNode();
	    }
	    rtnString += " ";
	}
	rtnString += "]";
	return rtnString;
    }

    /**
     * copies the value of each Node into a new Node that is then added to a new
     * LinkedList to which is meant to represent a duplicated list.
     *
     * @return the duplicated NumericLinkedList
     */
    @Override
    public NumericLinkedList copy() {
	NumericLinkedList copyList = new NumericLinkedList();
	for (int i = 0; i < count; i++) {
	    if (i == 0) {
		try {
		    copyList.add(i, firstNode.getValue().copy());
		} catch (IndexException ie) {
		    System.out.println(ie.getMessage());
		}
	    } else {
		try {
		    copyList.add(i, this.getValue(i).copy());
		} catch (IndexException ie) {
		    System.out.println(ie.getMessage());
		}
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
	Node currentNode = firstNode;
	for (int i = 1; i < index; i++) {
	    currentNode = currentNode.getNextNode();
	}
	Copiable value = currentNode.getValue();
	currentNode.setValue(obj);
	return value;
    }
}
