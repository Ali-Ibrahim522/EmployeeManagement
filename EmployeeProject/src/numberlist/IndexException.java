package numberlist;

/**
 * this class acts as the exception occurring when a index is used that is out of
 * the bounds of a list
 * 
 * @author Ali Ibrahim
 * @version 1.0
 */
public class IndexException extends Exception{
    private int min;
    private int max;
    private int value;
    
    /**
     * creates an exception with the bounds to which an index is allowed, and
     * the index that was attempted
     * 
     * @param min the lower bound of the list
     * @param max the higher bound of the list
     * @param value the attempted index
     */
    public IndexException(int min, int max, int value) {
	super("INDEX EXCEPTION: " + min + ", " + max + ", " + value + ", ");
	this.min = min;
	this.max = max;
	this.value = value;
    }

    /**
     * provides access to the lower bound of the list the exception occurred
     * from.
     * 
     * @return the minimum index allowed
     */
    public int getMin() {
	return min;
    }

    /**
     * provides access to the higher bound of the list the exception occurred
     * from.
     * 
     * @return the highest index allowed
     */
    public int getMax() {
	return max;
    }

    /**
     * provides access to the attempted index on the list
     * 
     * @return the attempted index
     */
    public int getValue() {
	return value;
    }
    
}
